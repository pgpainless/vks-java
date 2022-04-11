// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

import java.net.ConnectException;

/**
 * Exception gets thrown when the queried OpenPGP certificate cannot be found on the server.
 */
public class CertNotFoundException extends ConnectException {

    public CertNotFoundException(String message) {
        super(message);
    }
}
