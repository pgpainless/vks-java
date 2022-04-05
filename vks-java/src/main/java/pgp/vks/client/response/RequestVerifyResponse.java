package pgp.vks.client.response;

import javax.annotation.Nonnull;
import java.util.Map;

public class RequestVerifyResponse {

    private final String key_fpr;
    private final Map<String, Status> status;
    private final String token;

    public RequestVerifyResponse(@Nonnull String key_fpr, @Nonnull Map<String, Status> status, @Nonnull String token) {
        this.key_fpr = key_fpr;
        this.status = status;
        this.token = token;
    }
}
