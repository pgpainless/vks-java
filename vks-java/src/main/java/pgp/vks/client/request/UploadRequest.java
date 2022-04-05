package pgp.vks.client.request;

import javax.annotation.Nonnull;

public class UploadRequest {

    private final String keytext;

    public UploadRequest(@Nonnull String keytext) {
        this.keytext = keytext;
    }
}
