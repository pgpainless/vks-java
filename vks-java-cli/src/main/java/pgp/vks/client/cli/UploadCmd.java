// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.cli;

import pgp.vks.client.RequestVerify;
import pgp.vks.client.Status;
import pgp.vks.client.Upload;
import pgp.vks.client.VKS;
import pgp.vks.client.exception.CertCannotBePublishedException;
import picocli.CommandLine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@CommandLine.Command(
        name = "upload",
        resourceBundle = "msg_upload")
public class UploadCmd implements Runnable {

    @CommandLine.Mixin
    VKSCLI.KeyServerMixin keyServerMixin;

    @CommandLine.Option(names = {"-r", "--request-verification"})
    boolean requestVerification;

    private final ResourceBundle msg;

    public UploadCmd() {
        msg = ResourceBundle.getBundle("msg_upload", Locale.getDefault());
    }

    public void run() {
        VKS vks;
        try {
            vks = keyServerMixin.parent.getApi();
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }

        Upload upload = vks.upload();
        try {
            Upload.Response response = upload.cert(System.in);

            // Unpublished mail addresses
            List<String> unpublished = new ArrayList<>();
            int maxMailLen = 0;
            for (String address : response.getStatus().keySet()) {
                Status status = response.getStatus().get(address);
                if (address.length() > maxMailLen) {
                    maxMailLen = address.length();
                }
                if (status != Status.published && status != Status.revoked) {
                    unpublished.add(address);
                }
            }

            String msgUpload = String.format(msg.getString("output.uploaded_key"),
                    response.getKeyFingerprint(), response.getToken());
            System.out.println(msgUpload);

            String msgStatus = msg.getString("output.status");
            if (!requestVerification || unpublished.isEmpty()) {
                System.out.println(msgStatus);
                for (String address : response.getStatus().keySet()) {
                    Status status = response.getStatus().get(address);
                    System.out.format("%-" + maxMailLen + "s %s\n", address, status);
                }
                return;
            }

            RequestVerify.Response verifyResponse = vks.requestVerification().forEmailAddresses(unpublished.toArray(new String[0]))
                    .execute(response.getToken());
            System.out.println(msgStatus);
            for (String address : verifyResponse.getStatus().keySet()) {
                Status status = response.getStatus().get(address);
                System.out.format("%-" + maxMailLen + "s %s\n", address, status);
            }
        } catch (CertCannotBePublishedException e) {
            throw new AssertionError(e.getMessage());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
