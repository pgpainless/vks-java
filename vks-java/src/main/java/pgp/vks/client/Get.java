package pgp.vks.client;

import pgp.vks.client.exception.CertNotFoundException;

import java.io.IOException;
import java.io.InputStream;

public interface Get {

    InputStream byFingerprint(String fingerprint) throws CertNotFoundException, IOException;

    InputStream byKeyId(long keyId) throws CertNotFoundException;

    InputStream byEmail(String email) throws CertNotFoundException;

}
