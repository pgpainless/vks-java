package pgp.vks.client.exception;

public class CertNotFoundException extends Exception {

    public CertNotFoundException(Throwable cause) {
        super(cause);
    }

    public CertNotFoundException() {

    }
}
