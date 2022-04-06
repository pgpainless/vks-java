// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pgp.vks.client.Status;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadResponseTest {

    private static final ObjectMapper json = new ObjectMapper();

    @Test
    public void testSerializeDeserialize() throws JsonProcessingException {
        String fingerprint = "9DF2C3FE6F69A3EEDBD5FB8169E8A788A36E7BFD";
        Map<String, Status> statusMap = new HashMap<>();
        statusMap.put("hello@world.mail", Status.pending);
        statusMap.put("hello@mail.world", Status.unpublished);
        String token = "t0k3n5tr1n9";

        UploadResponseDto response = new UploadResponseDto(fingerprint, statusMap, token);

        String val = json.writeValueAsString(response);
        response = json.readValue(val, UploadResponseDto.class);

        assertEquals(fingerprint, response.getKeyFingerprint());
        assertEquals(statusMap, response.getStatus());
        assertEquals(token, response.getToken());
    }
}
