// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.impl.v1;

import org.bouncycastle.util.io.Streams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pgp.vks.client.VKS;
import pgp.vks.client.exception.CertNotFoundException;
import pgp.vks.client.v1.dto.VerificationResponse;
import pgp.vks.client.v1.impl.VKSImpl;
import pgp.vks.client.v1.dto.UploadResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VKSTest {

    private static VKS vks;

    @BeforeAll
    static void prepare() {
        vks = VKSImpl.keysDotOpenPgpDotOrg();
    }

    @Test
    public void testGetByFingerprint() throws IOException {
        InputStream inputStream = vks.get().byFingerprint("7F9116FEA90A5983936C7CFAA027DB2F3E1E118A");
        Streams.pipeAll(inputStream, System.out);
    }

    @Test
    public void testGetByFingerprint_inexistent() {
        assertThrows(CertNotFoundException.class, () ->
                vks.get().byFingerprint("0000000000000000000000000000000000000000"));
    }

    @Test
    public void testUploadArmored() throws IOException {
        String keyArmored = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
                "Comment: 5741 7147 D0C8 B548 220A  36A6 0BAA B05A 0877 68D3\n" +
                "Comment: <test123asdasd@byom.de>\n" +
                "\n" +
                "xjMEYky2cxYJKwYBBAHaRw8BAQdA+l48gCNI3qq+I5KFOWzJUEqd1ojQ9dj8vPxX\n" +
                "FaQFwrPCwBEEHxYKAIMFgmJMtnMFiQWkj70DCwkHCRALqrBaCHdo00cUAAAAAAAe\n" +
                "ACBzYWx0QG5vdGF0aW9ucy5zZXF1b2lhLXBncC5vcmecnaLE0kMC8KdcEyXcBbGa\n" +
                "YjiagLt29IAfEb5gkvRdlwMVCggCmwECHgEWIQRXQXFH0Mi1SCIKNqYLqrBaCHdo\n" +
                "0wAAIi8A/1HZNJgTKluUnWx9LArNy7/zeJfqjv/OpM+2UTnzx39YAP4jDLV4MnQR\n" +
                "yqV8cwMIUs3ywVLUXC1mhb4Mos+fCJv8Ac0XPHRlc3QxMjNhc2Rhc2RAYnlvbS5k\n" +
                "ZT7CwBQEExYKAIYFgmJMtnMFiQWkj70DCwkHCRALqrBaCHdo00cUAAAAAAAeACBz\n" +
                "YWx0QG5vdGF0aW9ucy5zZXF1b2lhLXBncC5vcmfb06zBQi+jpdGinb/RdunCKtsB\n" +
                "1p2+/BK+OhAg7QeoegMVCggCmQECmwECHgEWIQRXQXFH0Mi1SCIKNqYLqrBaCHdo\n" +
                "0wAAW7QBAPuGPj2a0cS5wPscwmSTJ0VCVRJiHO1I7G6zQbnCqPpSAQCpIIzwJYTD\n" +
                "GyJlFAsbeKDWZ06ocZRJr+EjkDYxBUwuDs4zBGJMtnMWCSsGAQQB2kcPAQEHQGGe\n" +
                "QPC6fMblCh3f9DJVuF7sHwI1ZO7Zl7rRKXBe/97CwsDFBBgWCgE3BYJiTLZzBYkF\n" +
                "pI+9CRALqrBaCHdo00cUAAAAAAAeACBzYWx0QG5vdGF0aW9ucy5zZXF1b2lhLXBn\n" +
                "cC5vcmceK3ulesGHFmSp7R9EnYuEIDdNOYOjQ244caRio3h/oAKbAr6gBBkWCgBv\n" +
                "BYJiTLZzCRAWfOxMKts/5kcUAAAAAAAeACBzYWx0QG5vdGF0aW9ucy5zZXF1b2lh\n" +
                "LXBncC5vcmeLTTIx8Ux5jI/8nwf0NXo3GawjHc2S48C4TwrGV5jnshYhBJrwKXCp\n" +
                "drOao3EMdBZ87Ewq2z/mAADIhgEAn7mG6YXo6lYf8/RmlpGk+a6uz8dzOrc8baoh\n" +
                "1mdc5wUBAK5LRDHEukqkjJ9QKFzbh6D8GaZqduODrx+FkAtjsPEBFiEEV0FxR9DI\n" +
                "tUgiCjamC6qwWgh3aNMAAPqeAQCCoobPkeCjTxjWt/nyHkTegH9Hi/eXUNuXngE7\n" +
                "rNI3SAD/W0xZnBMYQ7TMOpGhfEsMn/TlQiRrfSV9KDFnDMAvbgfOOARiTLZzEgor\n" +
                "BgEEAZdVAQUBAQdAMUyxaYf0/mpWFxaqU11Wn8xMrn9ZYYFRe1iCQ9SChGIDAQgJ\n" +
                "wsAGBBgWCgB4BYJiTLZzBYkFpI+9CRALqrBaCHdo00cUAAAAAAAeACBzYWx0QG5v\n" +
                "dGF0aW9ucy5zZXF1b2lhLXBncC5vcmev5SUm4G0yJrDk8SsYyi3tZfi2stONhniC\n" +
                "I+XQWIg10gKbDBYhBFdBcUfQyLVIIgo2pguqsFoId2jTAAAgMgD+PH0e2GkKe+gw\n" +
                "Bv60rUPUuwD2ubrASm5EkT+wPfY+ZyoBAOjp0Z2Vbrx0NkW7nd+HENb4v91eNUJA\n" +
                "YA3TLiYiZbEM\n" +
                "=QRwY\n" +
                "-----END PGP PUBLIC KEY BLOCK-----\n";
        String keyFingerprint = "57417147D0C8B548220A36A60BAAB05A087768D3";

        UploadResponse uploadResponse = vks.upload().cert(new ByteArrayInputStream(keyArmored.getBytes(StandardCharsets.UTF_8)));
        assertEquals(keyFingerprint, uploadResponse.getKeyFingerprint());

        VerificationResponse verifyResponse = vks.requestVerification().forEmailAddresses(
                Collections.singletonList("test123asdasd@byom.de"),
                uploadResponse.getToken(),
                Collections.singletonList("de_DE"));

        assertEquals(keyFingerprint, verifyResponse.getKeyFingerprint());
    }
}
