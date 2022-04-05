// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import pgp.vks.client.Get;
import pgp.vks.client.exception.CertNotFoundException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;

public class GetImpl implements Get {

    private final URLMapper api;

    public GetImpl(URLMapper api) {
        this.api = api;
    }

    @Override
    public InputStream byFingerprint(String fingerprint) throws IOException {
        URL url = api.getByFingerprint(fingerprint);
        return fetchFromUrl(url);
    }

    @Override
    public InputStream byKeyId(long keyId) throws IOException {
        URL url = api.getByKeyid(keyId);
        return fetchFromUrl(url);
    }

    @Override
    public InputStream byEmail(String email) throws IOException {
        URL url = api.getByEmail(email);
        return fetchFromUrl(url);
    }

    private InputStream fetchFromUrl(URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status == 200) {
            return connection.getInputStream();
        } else if (status == 404) {
            throw new CertNotFoundException("Certificate not found. Status Code: 404");
        } else {
            throw new ConnectException("Cannot retrieve certificate. Status Code: " + status);
        }
    }
}
