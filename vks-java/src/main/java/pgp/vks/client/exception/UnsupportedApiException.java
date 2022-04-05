// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

public class UnsupportedApiException extends RuntimeException {

    public UnsupportedApiException(String message) {
        super(message);
    }
}
