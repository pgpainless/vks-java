// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseDtoTest {

    private static final ObjectMapper json = new ObjectMapper();

    @Test
    public void testSerializeDeserialize() throws JsonProcessingException {
        String errorMessage = "Certificate cannot be parsed.";
        ErrorResponseDto dto = new ErrorResponseDto(errorMessage);
        assertEquals(errorMessage, dto.getError());

        String val = json.writeValueAsString(dto);
        dto = json.readValue(val, ErrorResponseDto.class);

        assertEquals(errorMessage, dto.getError());
    }
}
