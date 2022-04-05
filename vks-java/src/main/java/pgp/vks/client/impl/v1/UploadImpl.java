package pgp.vks.client.impl.v1;

import pgp.certificate_store.Certificate;
import pgp.vks.client.Upload;
import pgp.vks.client.response.UploadResponse;

import javax.annotation.Nonnull;

public class UploadImpl implements Upload {
    @Override
    public UploadResponse cert(@Nonnull Certificate certificate) {
        return null;
    }
}
