package pgp.vks.client.impl.v1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V1APITest {

    private static V1API api;

    @BeforeAll
    static void prepare() throws MalformedURLException {
        api = new V1API(new URL("https://keys.openpgp.org"));
    }

    @Test
    void testGetByFingerprint() {
        String fingerprint = "7F9116FEA90A5983936C7CFAA027DB2F3E1E118A";
        URL url = api.getByFingerprint(fingerprint);

        assertEquals("https://keys.openpgp.org/vks/v1/by-fingerprint/7F9116FEA90A5983936C7CFAA027DB2F3E1E118A", url.toString());
    }

    @Test
    public void testGetByKeyId() {
        long keyId = -6906310507597262454L;
        URL url = api.getByKeyid(keyId);

        assertEquals("https://keys.openpgp.org/vks/v1/by-keyid/A027DB2F3E1E118A", url.toString());
    }

    @Test
    public void testGetByEmail() {
        String email = "vanitasvitae@fsfe.org";
        URL url = api.getByEmail(email);

        assertEquals("https://keys.openpgp.org/vks/v1/by-email/vanitasvitae%40fsfe.org", url.toString());
    }

    @Test
    public void testPostUpload() {
        URL url = api.postUpload();

        assertEquals("https://keys.openpgp.org/vks/v1/upload", url.toString());
    }

    @Test
    public void testPostRequestVerify() {
        URL url = api.postRequestVerify();

        assertEquals("https://keys.openpgp.org/vks/v1/request-verify", url.toString());
    }
}
