// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class UploadResponse {

    private final String key_fpr;
    private final Map<String, Status> status;
    private final String token;

    public UploadResponse(@JsonProperty("key_fpr") String key_fpr,
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
        return new HashMap<>(status);
    }
}
