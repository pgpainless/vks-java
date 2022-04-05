// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import pgp.vks.client.exception.CertNotFoundException;

import java.io.IOException;
import java.io.InputStream;

public interface Get {

    /**
     * Retrieve a certificate by its fingerprint or the fingerprint of one of its subkeys.
     *
     * @param fingerprint fingerprint consisting of 32, 40 or 64 uppercase hex characters (without leading "0x" prefix).
     * @return InputStream containing the ASCII armored certificate
     *
     * @throws CertNotFoundException in case the certificate cannot be found
     * @throws IOException in case of an IO error
     */
    InputStream byFingerprint(String fingerprint) throws CertNotFoundException, IOException;

    /**
     * Retrieve a certificate by its primary key id or the key id of one if its subkeys.
     *
     * @param keyId key id
     * @return InputStream containing the ASCII armored certificate
     *
     * @throws CertNotFoundException in case the certificate cannot be found
     * @throws IOException in case of an IO error
     */
    InputStream byKeyId(long keyId) throws CertNotFoundException, IOException;

    /**
     * Retrieve a certificate by email address.
     * Only exact matches are accepted.
     * Note: On some servers, lookup by email address requires opt-in by the email addresses owner.
     *
     * @param email email address
     * @return InputStream containing the ASCII armored certificate
     *
     * @throws CertNotFoundException in case the certificate cannot be found
     * @throws IOException in case of an IO error
     */
    InputStream byEmail(String email) throws CertNotFoundException, IOException;

}
