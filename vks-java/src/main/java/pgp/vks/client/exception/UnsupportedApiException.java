// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

/**
 * Exception gets thrown when an unsupported API {@link pgp.vks.client.VKS.Version} is used.
 * E.g. user wants to use some command using v2 API, but implementation only supports v1.
 */
public class UnsupportedApiException extends RuntimeException {

    public UnsupportedApiException(String message) {
        super(message);
    }
}
