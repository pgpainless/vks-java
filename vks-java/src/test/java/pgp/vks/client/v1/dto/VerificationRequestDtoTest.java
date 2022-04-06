// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.v1.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificationRequestDtoTest {

    private static final ObjectMapper json = new ObjectMapper();

    @Test
    public void testSerializationDeserialization() throws JsonProcessingException {
        String token = "thisisalongtokenstring";
        List<String> addresses = Arrays.asList("alice@pgpainless.org", "vanitasvitae@fsfe.org", "Ed_Snowden@lavabit.com");
        List<String> locale = Arrays.asList("de_DE", "de_CH");

        VerificationRequestDto dto = new VerificationRequestDto(token, addresses, locale);
        assertEquals(token, dto.getToken());
        assertEquals(addresses, dto.getAddresses());
        assertEquals(locale, dto.getLocale());

        String val = json.writeValueAsString(dto);

        dto = json.readValue(val, VerificationRequestDto.class);
        assertEquals(token, dto.getToken());
        assertEquals(addresses, dto.getAddresses());
        assertEquals(locale, dto.getLocale());
    }

}
