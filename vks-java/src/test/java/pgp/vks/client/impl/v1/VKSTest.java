package pgp.vks.client.impl.v1;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.Streams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pgp.vks.client.VKS;
import pgp.vks.client.exception.CertNotFoundException;
import pgp.vks.client.impl.VKSImpl;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VKSTest {

    private static VKS vks;

    @BeforeAll
    static void prepare() {
        vks = VKSImpl.keysDotOpenPgpDotOrg();
    }

    @Test
    public void testGetByFingerprint() throws CertNotFoundException, IOException {
        InputStream inputStream = vks.get().byFingerprint("7F9116FEA90A5983936C7CFAA027DB2F3E1E118A");
        Streams.pipeAll(inputStream, System.out);
    }

    @Test
    public void testGetByFingerprint_inexistent() {
        assertThrows(CertNotFoundException.class, () ->
                vks.get().byFingerprint("0000000000000000000000000000000000000000"));
    }
}
