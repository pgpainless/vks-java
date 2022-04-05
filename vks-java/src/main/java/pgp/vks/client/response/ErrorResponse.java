package pgp.vks.client.response;

import javax.annotation.Nonnull;

public class ErrorResponse {

    private final String error;

    public ErrorResponse(@Nonnull String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
