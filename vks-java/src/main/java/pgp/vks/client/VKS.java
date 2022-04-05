// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import pgp.vks.client.exception.UnsupportedApiException;

import javax.annotation.Nonnull;

public interface VKS {

    /**
     * API Version.
     */
    enum Version {
        v1
    }

    /**
     * Retrieve OpenPGP certificates from a Verifying Key Server via the v1 API.
     *
     * @return Get API
     *
     * @throws UnsupportedApiException if Get-API v1 is not supported by the implementation of this interface
     */
    default Get get() throws UnsupportedApiException {
        return get(Version.v1);
    }

    /**
     * Retrieve OpenPGP certificates from a Verifying Key Server.
     *
     * @param version API version
     * @return Get API
     *
     * @throws UnsupportedApiException if Get-API <pre>version</pre> is not supported by the implementation
     *                                 of this interface
     */
    Get get(@Nonnull Version version) throws UnsupportedApiException;

    /**
     * Upload an OpenPGP certificate to a Verifying Key Server via the v1 API.
     *
     * @return Upload API
     *
     * @throws UnsupportedApiException if Upload-API v1 is not supported by the implementation of this interface
     */
    default Upload upload() throws UnsupportedApiException {
        return upload(Version.v1);
    }

    /**
     * Upload an OpenPGP certificate to a Verifying Key Server.
     *
     * @param version API version
     * @return Upload API
     *
     * @throws UnsupportedApiException if Upload-API <pre>version</pre> is not supported by the implementation
     *                                 of this interface
     */
    Upload upload(@Nonnull Version version) throws UnsupportedApiException;

    /**
     * Verify ownership of OpenPGP User-IDs on a Verifying Key Server via the v1 API.
     *
     * @return Request-Verify API
     *
     * @throws UnsupportedApiException if Request-Verify-API v1 is not supported by the implementation of this interface
     */
    default RequestVerify requestVerification() throws UnsupportedApiException {
        return requestVerification(Version.v1);
    }

    /**
     * Verify ownership of OpenPGP User-IDs on a Verifying Key Server.
     *
     * @param version API version
     * @return Upload API
     *
     * @throws UnsupportedApiException if Request-Verify-API <pre>version</pre> is not supported by the implementation
     *                                 of this interface
     */
    RequestVerify requestVerification(Version version) throws UnsupportedApiException;
}
