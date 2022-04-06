// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import pgp.vks.client.Upload;
import pgp.vks.client.exception.CertCannotBePublishedException;
import pgp.vks.client.v1.dto.ErrorResponseDto;
import pgp.vks.client.v1.dto.UploadRequestDto;
import pgp.vks.client.v1.dto.UploadResponseDto;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
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
    public Response cert(@Nonnull InputStream certInStream) throws IOException {
        UploadRequestDto request = UploadRequestDto.fromInputStream(certInStream);

        URL url = api.postUpload();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStream out = connection.getOutputStream();
        json.writeValue(out, request);
        out.flush();
        out.close();

        int status = connection.getResponseCode();
        InputStream responseIn;
        if (status >= 400) {
            responseIn = connection.getErrorStream();
            ErrorResponseDto errorResponse = json.readValue(responseIn, ErrorResponseDto.class);
            throw new CertCannotBePublishedException(errorResponse.getError() + (status));
        } else {
            responseIn = connection.getInputStream();
            UploadResponseDto dto = json.readValue(responseIn, UploadResponseDto.class);
            return dto.toEntity();
        }
    }
}
