// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    private final String error;

    public ErrorResponse(@JsonProperty("error") String error) {
        this.error = error;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }
}
