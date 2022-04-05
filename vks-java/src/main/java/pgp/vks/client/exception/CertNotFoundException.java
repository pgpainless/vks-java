// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

import java.net.ConnectException;

public class CertNotFoundException extends ConnectException {

    public CertNotFoundException() {
        super();
    }

    public CertNotFoundException(String message) {
        super(message);
    }
}
