// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import pgp.vks.client.RequestVerify;
import pgp.vks.client.exception.CertCannotBePublishedException;
import pgp.vks.client.v1.dto.ErrorResponseDto;
import pgp.vks.client.v1.dto.VerificationRequestDto;
import pgp.vks.client.v1.dto.VerificationResponseDto;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RequestVerifyImpl implements RequestVerify {

    private final URLMapper api;
    private final ObjectMapper json = new ObjectMapper();

    public RequestVerifyImpl(URLMapper api) {
        this.api = api;
    }

    @Override
    public ForEmailAddresses forEmailAddresses(String... emailAddresses) {
        if (emailAddresses == null || emailAddresses.length == 0) {
            throw new IllegalArgumentException("At least one email address is required.");
        }
        List<String> emailList = Arrays.asList(emailAddresses);

        return new ForEmailAddressesImpl(emailList);
    }

    private class ForEmailAddressesImpl implements ForEmailAddresses {

        private final List<String> emailAddresses;

        ForEmailAddressesImpl(@Nonnull List<String> emailList) {
            this.emailAddresses = emailList;
        }

        @Override
        public Response execute(String token, List<String> locale) throws IOException {
            VerificationRequestDto request = new VerificationRequestDto(token, emailAddresses, locale);

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
                ErrorResponseDto errorResponse = json.readValue(responseIn, ErrorResponseDto.class);
                throw new CertCannotBePublishedException(errorResponse.getError() + " (" + status + ")");
            } else {
                responseIn = connection.getInputStream();
                VerificationResponseDto response = json.readValue(responseIn, VerificationResponseDto.class);
                return response.toEntity();
            }
        }
    }
}
