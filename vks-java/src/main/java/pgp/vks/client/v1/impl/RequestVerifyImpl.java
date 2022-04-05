// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import pgp.vks.client.RequestVerify;
import pgp.vks.client.exception.CertCannotBePublishedException;
import pgp.vks.client.v1.dto.ErrorResponse;
import pgp.vks.client.v1.dto.VerificationResponse;
import pgp.vks.client.v1.dto.VerificationRequest;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class RequestVerifyImpl implements RequestVerify {

    private final URLMapper api;
    private final ObjectMapper json = new ObjectMapper();

    public RequestVerifyImpl(URLMapper api) {
        this.api = api;
    }

    @Override
    public VerificationResponse forEmailAddresses(List<String> emailAddresses, String token, List<String> locale)
            throws IOException {
        VerificationRequest request = new VerificationRequest(token, emailAddresses, locale);

        URL url = api.postRequestVerify();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream out = connection.getOutputStream();
        json.writeValue(out, request);
        out.flush();
        out.close();

        int status = connection.getResponseCode();
        InputStream responseIn;
        if (status >= 400) {
            responseIn = connection.getErrorStream();
            ErrorResponse errorResponse = json.readValue(responseIn, ErrorResponse.class);
            throw new CertCannotBePublishedException(errorResponse.getError() + (status));
        } else {
            responseIn = connection.getInputStream();
            VerificationResponse response = json.readValue(responseIn, VerificationResponse.class);
            return response;
        }
    }
}
