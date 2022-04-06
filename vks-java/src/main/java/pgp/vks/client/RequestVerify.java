// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface RequestVerify {

    default ForEmailAddresses forEmailAddress(String emailAddress) {
        return forEmailAddresses(emailAddress);
    }

    ForEmailAddresses forEmailAddresses(String... emailAddresses);

    interface ForEmailAddresses {

        default Response execute(String token) throws IOException {
            return execute(token, Arrays.asList("en_US", "en_GB"));
        }

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

        public String getKeyFingerprint() {
            return keyFingerprint;
        }

        public Map<String, Status> getStatus() {
            return status;
        }

        public String getToken() {
            return token;
        }
    }
}
