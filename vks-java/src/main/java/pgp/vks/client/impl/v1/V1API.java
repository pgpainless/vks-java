package pgp.vks.client.impl.v1;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * URL mapper for the VKS API.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentatioon</a>
 */
public class V1API {

    private static final String GET_BY_FINGERPRINT = "/vks/v1/by-fingerprint/";
    private static final String GET_BY_KEYID = "/vks/v1/by-keyid/";
    private static final String GET_BY_EMAIL = "/vks/v1/by-email/";
    private static final String POST_UPLOAD = "/vks/v1/upload";
    private static final String POST_REQUEST_VERIFY = "/vks/v1/request-verify";

    private final URL serviceUrl;

    public V1API(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public URL getByFingerprint(String fingerprint) {
        return getUrl(GET_BY_FINGERPRINT, fingerprint.toUpperCase());
    }

    public URL getByKeyid(long keyId) {
        return getUrl(GET_BY_KEYID, Long.toHexString(keyId).toUpperCase());
    }

    public URL getByEmail(String email) {
        try {
            return getUrl(GET_BY_EMAIL, URLEncoder.encode(email, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // UTF8 is supported anywhere
            throw new AssertionError(e);
        }
    }

    public URL postUpload() {
        return getUrl(POST_UPLOAD, null);
    }

    public URL postRequestVerify() {
        return getUrl(POST_REQUEST_VERIFY, null);
    }

    @SneakyThrows
    private URL getUrl(String path, String param) {
        return new URL(serviceUrl.getProtocol(), serviceUrl.getHost(), serviceUrl.getPort(), serviceUrl.getPath() + path + (param != null ? param : ""), null);
    }
}
