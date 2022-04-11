// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request for email verification of one or more unpublished/pending user-ids.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentation</a>
 */
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

    /**
     * Return the token which was previously required by uploading a certificate using an {@link UploadRequestDto}.
     *
     * @return access token used to authenticate the request
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * {@link List} of email addresses for which to request email verification.
     *
     * @return email addresses
     */
    @JsonProperty("addresses")
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * {@link List} of preferred locales for the verification mails.
     *
     * @return locale list
     */
    @JsonProperty("locale")
    public List<String> getLocale() {
        return locale;
    }
}
