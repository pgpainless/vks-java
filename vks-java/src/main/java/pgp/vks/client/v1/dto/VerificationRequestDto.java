// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VerificationRequestDto {

    private final String token;
    private final List<String> addresses;
    private final List<String> locale;

    public VerificationRequestDto(@JsonProperty("token") String token,
                                  @JsonProperty("addresses") List<String> addresses,
                                  @JsonProperty("locale") List<String> locale) {
        this.token = token;
        this.addresses = addresses;
        this.locale = locale;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("addresses")
    public List<String> getAddresses() {
        return addresses;
    }

    @JsonProperty("locale")
    public List<String> getLocale() {
        return locale;
    }
}
