// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.cli;

import pgp.vks.client.RequestVerify;
import pgp.vks.client.VKS;
import picocli.CommandLine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@CommandLine.Command(
        name = "request-verification",
        resourceBundle = "msg_request_verification")
public class RequestVerificationCmd implements Runnable {

    @CommandLine.Mixin
    VKSCLI.KeyServerMixin keyServerMixin;

    @CommandLine.Option(names = {"-t", "--token"},
            required = true, arity = "1", paramLabel = "TOKEN")
    String token;

    @CommandLine.Option(names = {"-l", "--locale"})
    List<String> locale = Arrays.asList("en_US", "en_GB");

    @CommandLine.Option(names = {"-e", "--email"}, required = true, arity = "1..*")
    String[] addresses = new String[0];

    private final ResourceBundle msg;

    public RequestVerificationCmd() {
        msg = ResourceBundle.getBundle("msg_request_verification", Locale.getDefault());
    }

    @Override
    public void run() {
        VKS vks;
        try {
            vks = keyServerMixin.parent.getApi();
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }

        RequestVerify requestVerify = vks.requestVerification();
        try {
            RequestVerify.Response response = requestVerify
                    .forEmailAddresses(addresses)
                    .execute(token, locale);
            System.out.printf(msg.getString("output.mails_sent"), response.getKeyFingerprint());
            System.out.printf(msg.getString("output.token"), response.getToken());
            System.out.println(msg.getString("output.status"));
            for (String address : response.getStatus().keySet()) {
                System.out.println("\t" + address + "\t" + response.getStatus().get(address));
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
