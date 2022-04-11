// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pgp.vks.client.RequestVerify;
import pgp.vks.client.Status;

import java.util.Map;

/**
 * VKS servers response to a successful verification request.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentation</a>
 */
public class VerificationResponseDto {

    private final String key_fpr;
    private final Map<String, Status> status;
    private final String token;

    public VerificationResponseDto(@JsonProperty("key_fpr") String key_fpr,
                                   @JsonProperty("status") Map<String, Status> status,
                                   @JsonProperty("token") String token) {
        this.key_fpr = key_fpr;
        this.status = status;
        this.token = token;
    }

    /**
     * Uppercase OpenPGP fingerprint of the certificate.
     *
     * @return fingerprint
     */
    @JsonProperty("key_fpr")
    public String getKeyFingerprint() {
        return key_fpr;
    }

    /**
     * Access token which can be used to authenticate further verification requests.
     *
     * @return token
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * Map of {@link Status States} of email addresses of user-ids on the certificate.
     *
     * @return email address status map
     */
    @JsonProperty("status")
    public Map<String, Status> getStatus() {
        return status;
    }

    /**
     * Convert this DTO to an entity.
     *
     * @return entity
     */
    public RequestVerify.Response toEntity() {
        return new RequestVerify.Response(getKeyFingerprint(), getStatus(), getToken());
    }
}
