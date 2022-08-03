// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.cli;

import pgp.vks.client.Get;
import pgp.vks.client.VKS;
import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

@CommandLine.Command(
        name = "get",
        resourceBundle = "msg_get")
public class GetCmd implements Runnable {

    @CommandLine.Mixin
    VKSCLI.KeyServerMixin keyServerMixin;

    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive by;

    static class Exclusive {
        @CommandLine.Option(names = {"-f", "--by-fingerprint"})
        String fingerprint;

        @CommandLine.Option(names = {"-i", "--by-keyid"})
        Long keyId;

        @CommandLine.Option(names = {"-e", "--by-email"})
        String email;
    }

    private final ResourceBundle msg;

    public GetCmd() {
        msg = ResourceBundle.getBundle("msg_get", Locale.getDefault());
    }

    public void run() {
        VKS vks;
        try {
            vks = keyServerMixin.parent.getApi();
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }

        Get get = vks.get();
        InputStream inputStream = null;
        try {
            if (by.fingerprint != null) {
                inputStream = get.byFingerprint(by.fingerprint);
            } else if (by.keyId != null) {
                inputStream = get.byKeyId(by.keyId);
            } else if (by.email != null) {
                inputStream = get.byEmail(by.email);
            } else {
                throw new IllegalArgumentException(msg.getString("error.missing_by_option"));
            }

            int read;
            byte[] buf = new byte[4096];
            while ((read = inputStream.read(buf)) != -1) {
                System.out.write(buf, 0, read);
            }

        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            }
        }
    }
}
