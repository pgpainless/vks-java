// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.io.Streams;
import pgp.vks.client.Upload;
import pgp.vks.client.exception.CertCannotBePublishedException;
import pgp.vks.client.v1.dto.UploadRequest;
import pgp.vks.client.v1.dto.ErrorResponse;
import pgp.vks.client.v1.dto.UploadResponse;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class UploadImpl implements Upload {

    private final URLMapper api;
    private final ObjectMapper json = new ObjectMapper();

    public UploadImpl(URLMapper api) {
        this.api = api;
    }

    @Override
    public UploadResponse cert(@Nonnull InputStream certInStream) throws IOException {
        ByteArrayOutputStream certBuf = new ByteArrayOutputStream();
        Streams.pipeAll(certInStream, certBuf);
        certInStream.close();

        UploadRequest request = UploadRequest.fromBytes(certBuf.toByteArray());

        URL url = api.postUpload();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStream out = connection.getOutputStream();
        byte[] requestBody = json.writeValueAsBytes(request);
        out.write(requestBody);
        out.flush();
        out.close();

        int status = connection.getResponseCode();
        System.out.println(status);
        InputStream responseIn;
        if (status >= 400) {
            responseIn = connection.getErrorStream();
            ErrorResponse errorResponse = json.readValue(responseIn, ErrorResponse.class);
            throw new CertCannotBePublishedException(errorResponse.getError() + (status));
        } else {
            responseIn = connection.getInputStream();
            UploadResponse response = json.readValue(responseIn, UploadResponse.class);
            return response;
        }
    }
}
