package pgp.vks.client;

import javax.annotation.Nonnull;

public interface VKS {

    enum Version {
        v1
    }

    default Get get() {
        return get(Version.v1);
    }

    Get get(@Nonnull Version version);

    default Upload upload() {
        return upload(Version.v1);
    }

    Upload upload(@Nonnull Version version);

    default RequestVerify requestVerify() {
        return requestVerify(Version.v1);
    }

    RequestVerify requestVerify(Version version);
}
