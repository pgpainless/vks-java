// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pgp.vks.client.RequestVerify;
import pgp.vks.client.Status;

import java.util.Map;

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

    @JsonProperty("key_fpr")
    public String getKeyFingerprint() {
        return key_fpr;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("status")
    public Map<String, Status> getStatus() {
        return status;
    }

    public RequestVerify.Response toEntity() {
        return new RequestVerify.Response(getKeyFingerprint(), getStatus(), getToken());
    }
}