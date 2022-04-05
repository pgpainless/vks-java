package pgp.vks.client.impl.v1;

import pgp.vks.client.Get;
import pgp.vks.client.exception.CertNotFoundException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetImpl implements Get {

    private final V1API api;

    public GetImpl(V1API api) {
        this.api = api;
    }

    @Override
    public InputStream byFingerprint(String fingerprint) throws CertNotFoundException, IOException {
        URL url = api.getByFingerprint(fingerprint);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status == 200) {
            return connection.getInputStream();
        } else if (status == 404) {
            throw new CertNotFoundException();
        } else {
            throw new IllegalStateException("Unhandled status code: " + status);
        }
    }

    @Override
    public InputStream byKeyId(long keyId) throws CertNotFoundException {
        return null;
    }

    @Override
    public InputStream byEmail(String email) throws CertNotFoundException {
        return null;
    }
}
