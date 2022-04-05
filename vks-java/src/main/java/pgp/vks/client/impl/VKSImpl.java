package pgp.vks.client.impl;

import lombok.SneakyThrows;
import pgp.vks.client.Get;
import pgp.vks.client.RequestVerify;
import pgp.vks.client.Upload;
import pgp.vks.client.VKS;
import pgp.vks.client.impl.v1.GetImpl;
import pgp.vks.client.impl.v1.RequestVerifyImpl;
import pgp.vks.client.impl.v1.UploadImpl;
import pgp.vks.client.impl.v1.V1API;

import javax.annotation.Nonnull;
import java.net.URL;

public class VKSImpl implements VKS {

    private final V1API api;

    public VKSImpl(URL vksService) {
        this.api = new V1API(vksService);
    }

    @SneakyThrows
    public static VKS keysDotOpenPgpDotOrg() {
        return new VKSImpl(new URL("https://keys.openpgp.org"));
    }

    @Override
    public Get get(@Nonnull Version version) {
        switch (version) {
            case v1:
                return new GetImpl(api);
            default:
                throw new IllegalArgumentException("Invalid version: " + version);
        }
    }

    @Override
    public Upload upload(@Nonnull Version version) {
        switch (version) {
            case v1:
                return new UploadImpl();
            default:
                throw new IllegalArgumentException("Invalid version: " + version);
        }
    }

    @Override
    public RequestVerify requestVerify(Version version) {
        switch (version) {
            case v1:
                return new RequestVerifyImpl();
            default:
                throw new IllegalArgumentException("Invalid version: " + version);
        }
    }
}
