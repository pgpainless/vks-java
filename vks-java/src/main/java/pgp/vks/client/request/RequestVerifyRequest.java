package pgp.vks.client.request;

import javax.annotation.Nonnull;
import java.util.List;

public class RequestVerifyRequest {

    private final String token;
    private final List<String> addresses;
    private final List<String> locale;

    public RequestVerifyRequest(@Nonnull String token, @Nonnull List<String> addresses, @Nonnull List<String> locale) {
        this.token = token;
        this.addresses = addresses;
        this.locale = locale;
    }
}
