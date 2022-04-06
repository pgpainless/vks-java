// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

public enum Status {

    /**
     * User-ID is not published.
     */
    unpublished,

    /**
     * User-ID is published.
     */
    published,

    /**
     * User-ID is revoked.
     */
    revoked,

    /**
     * A verification mail for the User-ID was requested, but the verification is still pending.
     */
    pending
}
