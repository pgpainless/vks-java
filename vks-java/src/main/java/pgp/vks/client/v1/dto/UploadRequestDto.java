// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.Streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Request for uploading a certificate to the VKS.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentation</a>
 */
public class UploadRequestDto {

    private static final byte[] ARMOR_HEADER = "-----BEGIN PGP PUBLIC KEY BLOCK-----".getBytes(Charset.forName("UTF8"));

    private final String keytext;

    public UploadRequestDto(@JsonProperty("keytext") String keytext) {
        this.keytext = keytext;
    }

    /**
     * Create an {@link UploadRequestDto} from an ASCII armored or binary OpenPGP certificate which is read from the
     * given <pre>certInStream</pre>.
     *
     * @param certInStream {@link InputStream} containing the ASCII armored or binary OpenPGP certificate
     * @return request DTO
     *
     * @throws IOException in case of an IO error
     */
    public static UploadRequestDto fromInputStream(InputStream certInStream) throws IOException {
        ByteArrayOutputStream certBuf = new ByteArrayOutputStream();
        Streams.pipeAll(certInStream, certBuf);
        certInStream.close();
        return fromBytes(certBuf.toByteArray());
    }

    /**
     * Create an {@link UploadRequestDto} from an ASCII armored or binary OpenPGP certificate which is read from the
     * given <pre>keytext</pre> byte array.
     *
     * @param keytext byte array containing the ASCII armored or binary OpenPGP certificate
     * @return request DTO
     */
    public static UploadRequestDto fromBytes(byte[] keytext) {
        String armoredOrBase64 = new String(base64IfNecessary(keytext));
        return new UploadRequestDto(armoredOrBase64);
    }

    /**
     * Prepare a serialized OpenPGP certificate for upload.
     * If the given <pre>certBytes</pre> contain an ASCII armored OpenPGP certificate, do nothing.
     * Otherwise, consider the bytes to be the binary representation of an OpenPGP certificate and wrap it in Base64 encoding.
     *
     * @param certBytes certificate bytes
     * @return Unmodified ASCII armored, or base64 encoded binary OpenPGP certificate
     */
    private static byte[] base64IfNecessary(byte[] certBytes) {
        if (!Arrays.areEqual(certBytes, 0, ARMOR_HEADER.length, ARMOR_HEADER, 0, ARMOR_HEADER.length)) {
            certBytes = Base64.encode(certBytes);
        }
        return certBytes;
    }

    @JsonProperty("keytext")
    public String getKeyText() {
        return keytext;
    }
}
