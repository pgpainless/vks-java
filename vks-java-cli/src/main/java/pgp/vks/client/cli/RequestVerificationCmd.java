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

@CommandLine.Command(name = "request-verification", description = "Request verification for unverified user-ids")
public class RequestVerificationCmd implements Runnable {

    @CommandLine.Mixin
    VKSCLI.KeyServerMixin keyServerMixin;

    @CommandLine.Option(names = {"-t", "--token"}, description = "Access token. Can be retrieved by uploading the certificate.",
            required = true, arity = "1", paramLabel = "TOKEN")
    String token;

    @CommandLine.Option(names = {"-l", "--locale"}, description = "Locale for the verification mail")
    List<String> locale = Arrays.asList("en_US", "en_GB");

    @CommandLine.Option(names = {"-e", "--email"}, description = "Email addresses to request a verification mail for", required = true, arity = "1..*")
    String[] addresses = new String[0];


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

            System.out.println("Verification E-Mails for key " + response.getKeyFingerprint() + " have been sent.");
            System.out.println("Token: " + response.getToken());
            System.out.println("Status:");
            for (String address : response.getStatus().keySet()) {
                System.out.println("\t" + address + "\t" + response.getStatus().get(address));
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
