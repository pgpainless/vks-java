// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * URL mapper for the VKS API.
 *
 * @see <a href="https://keys.openpgp.org/about/api">VKS API Documentatioon</a>
 */
public class URLMapper {

    private static final String GET_BY_FINGERPRINT = "/vks/v1/by-fingerprint/";
    private static final String GET_BY_KEYID = "/vks/v1/by-keyid/";
    private static final String GET_BY_EMAIL = "/vks/v1/by-email/";
    private static final String POST_UPLOAD = "/vks/v1/upload";
    private static final String POST_REQUEST_VERIFY = "/vks/v1/request-verify";

    private static final Pattern PATTERN_HEX = Pattern.compile("^[0-9A-F]*$");
    // see Java Regex from https://emailregex.com/
    private static final Pattern PATTERN_EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    private final URL serviceUrl;

    /**
     * Create an {@link URLMapper} for the given key server.
     *
     * @param serviceUrl URL of the key server
     */
    public URLMapper(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public URL getByFingerprint(String fingerprint) {
        String uppercase = fingerprint.toUpperCase();
        int len = uppercase.length();
        if ((len != 32 && len != 40 && len != 64) || !PATTERN_HEX.matcher(uppercase).matches()) {
            throw new IllegalArgumentException("Invalid OpenPGP fingerprint: " + fingerprint);
        }
        return getUrl(GET_BY_FINGERPRINT, uppercase);
    }

    public URL getByKeyid(long keyId) {
        return getUrl(GET_BY_KEYID, Long.toHexString(keyId).toUpperCase());
    }

    public URL getByEmail(String email) {
        if (!PATTERN_EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }

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
