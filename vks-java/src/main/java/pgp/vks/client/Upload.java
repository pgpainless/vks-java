package pgp.vks.client;

import pgp.certificate_store.Certificate;
import pgp.vks.client.response.UploadResponse;

import javax.annotation.Nonnull;

public interface Upload {

    UploadResponse cert(@Nonnull Certificate certificate);
}
