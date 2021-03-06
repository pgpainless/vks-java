// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import lombok.SneakyThrows;
import pgp.vks.client.exception.UnsupportedApiException;
import pgp.vks.client.v1.impl.GetImpl;
import pgp.vks.client.v1.impl.RequestVerifyImpl;
import pgp.vks.client.v1.impl.URLMapper;
import pgp.vks.client.v1.impl.UploadImpl;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class VKSImpl implements VKS {

    private final URLMapper api;

    public VKSImpl(String vksServiceDomain) throws MalformedURLException {
        this(new URL(vksServiceDomain));
    }

    public VKSImpl(URL vksService) {
        this.api = new URLMapper(vksService);
    }

    /**
     * Return a {@link VKSImpl} instance targeting the <pre>https://keys.openpgp.org</pre> VKS.
     *
     * @return VKS instance
     */
    @SneakyThrows
    public static VKS keysDotOpenPgpDotOrg() {
        return new VKSImpl("https://keys.openpgp.org");
    }

    @Override
    public Get get(@Nonnull Version version) {
        switch (version) {
            case v1:
                return new GetImpl(api);
            default:
                throw new UnsupportedApiException("Get-API in version " + version + " not supported.");
        }
    }

    @Override
    public Upload upload(@Nonnull Version version) {
        switch (version) {
            case v1:
                return new UploadImpl(api);
            default:
                throw new UnsupportedApiException("Upload-API in version " + version + " not supported.");
        }
    }

    @Override
    public RequestVerify requestVerification(Version version) {
        switch (version) {
            case v1:
                return new RequestVerifyImpl(api);
            default:
                throw new UnsupportedApiException("Request-Verify-API in version " + version + " not supported.");
        }
    }
}
