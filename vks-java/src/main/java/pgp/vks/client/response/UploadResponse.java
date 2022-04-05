package pgp.vks.client.response;

import java.util.Map;

public class UploadResponse {

    private final String key_fpr;
    private final Map<String, Status> status;
    private final String token;

    public UploadResponse(String key_fpr, Map<String, Status> status, String token) {
        this.key_fpr = key_fpr;
        this.status = status;
        this.token = token;
    }

}
