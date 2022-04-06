// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public interface Upload {

    /**
     * Upload a certificate to the key server.
     * The <pre>certInStream</pre> can either contain the binary, or the ASCII armored representation of a transferable
     * public key (certificate).
     *
     * @param certInStream inputStream containing the certificate
     * @return server response
     * @throws IOException in case of an IO error
     */
    Response cert(@Nonnull InputStream certInStream) throws IOException;

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
         * Return the fingerprint of the uploaded certificate.
         *
         * @return fingerprint
         */
        public String getKeyFingerprint() {
            return keyFingerprint;
        }

        /**
         * Return an access token which, for a limited time, can be used to request verifications (see {@link RequestVerify}).
         *
         * @return access token
         */
        public String getToken() {
            return token;
        }

        /**
         * Return a map containing the {@link Status} of each of the keys addresses.
         *
         * @return status map
         */
        public Map<String, Status> getStatus() {
            return new HashMap<>(status);
        }
    }
}

