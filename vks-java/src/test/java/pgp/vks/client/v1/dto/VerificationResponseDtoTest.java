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

public class VerificationResponseDtoTest {

    private static final ObjectMapper json = new ObjectMapper();

    @Test
    public void testSerializationDeserialization() throws JsonProcessingException {
        String fingerprint = "1EBF5F15850C540B3142F1584BDD496D4C6C5F25";
        Map<String, Status> status = new HashMap<>();
        status.put("XXX@riseup.net", Status.published);
        status.put("l.p@riseup.net", Status.unpublished);
        status.put("laura@riseup.net", Status.pending);
        status.put("poitras@gmail.com", Status.revoked);
        String token = "incrediblylongandoverlytideoustotypetokenstring";

        VerificationResponseDto dto = new VerificationResponseDto(fingerprint, status, token);
        assertEquals(fingerprint, dto.getKeyFingerprint());
        assertEquals(status, dto.getStatus());
        assertEquals(token, dto.getToken());

        String val = json.writeValueAsString(dto);
        dto = json.readValue(val, VerificationResponseDto.class);
        assertEquals(fingerprint, dto.getKeyFingerprint());
        assertEquals(status, dto.getStatus());
        assertEquals(token, dto.getToken());
    }
}
