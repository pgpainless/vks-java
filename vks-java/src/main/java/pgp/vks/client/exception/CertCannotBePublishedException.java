// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.exception;

import java.net.ConnectException;

public class CertCannotBePublishedException extends ConnectException {

    public CertCannotBePublishedException(String errorMessage) {
        super(errorMessage);
    }
}
