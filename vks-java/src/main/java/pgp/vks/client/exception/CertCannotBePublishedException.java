// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

import java.net.ConnectException;

/**
 * Exception gets thrown when a public key certificate cannot be published for some reason.
 */
public class CertCannotBePublishedException extends ConnectException {

    public CertCannotBePublishedException(String errorMessage) {
        super(errorMessage);
    }
}
