// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public class UploadRequest {

    private static final byte[] ARMOR_HEADER = "-----BEGIN PGP PUBLIC KEY BLOCK-----".getBytes(StandardCharsets.UTF_8);

    private final String keytext;

    public UploadRequest(@JsonProperty("keytext") String keytext) {
        this.keytext = keytext;
    }

    public static UploadRequest fromBytes(byte[] keytext) {
        String armoredOrBase64 = new String(base64IfNecessary(keytext));
        return new UploadRequest(armoredOrBase64);
    }

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
