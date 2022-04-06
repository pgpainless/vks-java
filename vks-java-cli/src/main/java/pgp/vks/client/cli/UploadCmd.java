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

@CommandLine.Command(name = "upload", description = "Upload an OpenPGP certificate to the key server")
public class UploadCmd implements Runnable {

    @CommandLine.Mixin
    VKSCLI.KeyServerMixin keyServerMixin;

    @CommandLine.Option(names = {"-r", "--request-verification"},
            description = "Request verification mails for unpublished email addresses")
    boolean requestVerification;

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

            System.out.println("Uploaded key " + response.getKeyFingerprint());
            System.out.println("Token: " + response.getToken());

            if (!requestVerification || unpublished.isEmpty()) {
                System.out.println("Status:");
                for (String address : response.getStatus().keySet()) {
                    Status status = response.getStatus().get(address);
                    System.out.format("%-" + maxMailLen + "s %s\n", address, status);
                }
                return;
            }

            RequestVerify.Response verifyResponse = vks.requestVerification().forEmailAddresses(unpublished.toArray(new String[0]))
                    .execute(response.getToken());
            System.out.println("Status:");
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
