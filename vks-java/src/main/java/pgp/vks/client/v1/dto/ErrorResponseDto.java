// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response that gets returned by the server if a POST request fails.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentation</a>
 */
public class ErrorResponseDto {

    private final String error;

    public ErrorResponseDto(@JsonProperty("error") String error) {
        this.error = error;
    }

    /**
     * Return the error message.
     *
     * @return error message
     */
    @JsonProperty("error")
    public String getError() {
        return error;
    }
}
