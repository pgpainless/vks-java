// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Request email verification for email addresses of user-ids on a certificate.
 */
public interface RequestVerify {

    /**
     * Request email verification for the given email address.
     *
     * @param emailAddress email address
     * @return builder
     */
    default ForEmailAddresses forEmailAddress(String emailAddress) {
        return forEmailAddresses(emailAddress);
    }

    /**
     * Request email verification for one or more email addresses.
     *
     * @param emailAddresses email addresses
     * @return builder
     */
    ForEmailAddresses forEmailAddresses(String... emailAddresses);

    interface ForEmailAddresses {

        /**
         * Execute the verification request using the given <pre>token</pre>, retrieved earlier by uploading the
         * certificate to the server.
         *
         * @param token access token to authorize the request
         * @return servers successful response
         *
         * @throws IOException in case of an error
         */
        default Response execute(String token) throws IOException {
            return execute(token, Arrays.asList("en_US", "en_GB"));
        }

        /**
         * Execute the verification request using the given <pre>token</pre>, retrieved earlier by uploading the
         * certificate to the server.
         *
         * @param token access token to authorize the request
         * @param locale list of preferred locales for the verification emails
         * @return servers successful response
         *
         * @throws IOException in case of an error
         */
        Response execute(String token, List<String> locale) throws IOException;

    }

    class Response {

        private final String keyFingerprint;
        private final Map<String, Status> status;
        private final String token;

        public Response(String keyFingerprint,
                        Map<String, Status> status,
                        String token) {
            this.keyFingerprint = keyFingerprint;
            this.status = status;
            this.token = token;
        }

        /**
         * Return the uppercase OpenPGP fingerprint of the certificate.
         *
         * @return fingerprint
         */
        public String getKeyFingerprint() {
            return keyFingerprint;
        }

        /**
         * Return a map of {@link Status States} of email addresses of user-ids on the certificate after requesting
         * verification.
         * @return status map
         */
        public Map<String, Status> getStatus() {
            return status;
        }

        /**
         * Return an access token to be used to make further requests to the API.
         *
         * @return token
         */
        public String getToken() {
            return token;
        }
    }
}
