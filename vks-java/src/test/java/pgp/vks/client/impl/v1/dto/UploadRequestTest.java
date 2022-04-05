// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.impl.v1.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import pgp.vks.client.v1.dto.UploadRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadRequestTest {

    private static final String TEST_CERT_ARMORED = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
            "Comment: 9DF2 C3FE 6F69 A3EE DBD5  FB81 69E8 A788 A36E 7BFD\n" +
            "Comment: Test\n" +
            "\n" +
            "xjMEYkyuqxYJKwYBBAHaRw8BAQdAKayCvDEF4ZBcUJ9NPZlrG2jYOrNHDfYqOHpq\n" +
            "D+3AkK7CwBEEHxYKAIMFgmJMrqsFiQWkj70DCwkHCRBp6KeIo257/UcUAAAAAAAe\n" +
            "ACBzYWx0QG5vdGF0aW9ucy5zZXF1b2lhLXBncC5vcmeCqtr0bdgGRbTWiCD+lFXm\n" +
            "cyAF5bE3vAgLRHfSSjXcDgMVCggCmwECHgEWIQSd8sP+b2mj7tvV+4Fp6KeIo257\n" +
            "/QAA5pYA/1LKPHcYBTFAIQEJo7cN6ZUJVupGCPI5g2F2qBJbk5OTAQCmiYScX22U\n" +
            "VuoOq/QIgMXhlVMb/oFs7ttWiSbTWHQMAM0EVGVzdMLAFAQTFgoAhgWCYkyuqwWJ\n" +
            "BaSPvQMLCQcJEGnop4ijbnv9RxQAAAAAAB4AIHNhbHRAbm90YXRpb25zLnNlcXVv\n" +
            "aWEtcGdwLm9yZ/H8CTxR91Y1HOYAw910GaXZt7sOeypX5drWVoeA0VBHAxUKCAKZ\n" +
            "AQKbAQIeARYhBJ3yw/5vaaPu29X7gWnop4ijbnv9AACN8QD8C747OJoLZpjTSiGc\n" +
            "N3GlJbaWmdGGaFOMJktKIKgXdd8A/0gAKE1gn12Jo0rl9sHpRMqKPNG1QGNHJ7X/\n" +
            "H7PZE/kLzjMEYkyuqxYJKwYBBAHaRw8BAQdAJy+BAzLwsFL9T+SwbyQxZhatOZZ7\n" +
            "/xXlYJWKUu2M+UDCwMUEGBYKATcFgmJMrqsFiQWkj70JEGnop4ijbnv9RxQAAAAA\n" +
            "AB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdwLm9yZ9rmaNAa/me4nEhPdKRG\n" +
            "MUSXjv1R1ZGnlAoEtR/2NdU7ApsCvqAEGRYKAG8FgmJMrqsJEPYiaYJrDVrZRxQA\n" +
            "AAAAAB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdwLm9yZws3xh5RCajUNu0M\n" +
            "re0wS0Xh+N6IxLYoGrlGjxrRBw6hFiEEAj9WeADbcBGWuGqt9iJpgmsNWtkAAFhf\n" +
            "AQD7Ouv336RMsPgE3JlKx9zGPZcU9HVib70Q2fXxSVR7RQD+O9PEF7MqsIZXiTFG\n" +
            "vkwLe97LoCBBw6LhO8YlDiIZAwwWIQSd8sP+b2mj7tvV+4Fp6KeIo257/QAAdekA\n" +
            "/jp1Nc5kihBPK/vsQSJkHtUeeYPZQz9wxeFgKjEVQiBVAP9lwdJzgC4FNdz0rpp6\n" +
            "xCG38GI2o97S1cr+ot3lkaZpBc44BGJMrqsSCisGAQQBl1UBBQEBB0D8iZDZOC0M\n" +
            "MkZHO7UPpqhCaW642O00LkrToWaKbaUoRAMBCAnCwAYEGBYKAHgFgmJMrqsFiQWk\n" +
            "j70JEGnop4ijbnv9RxQAAAAAAB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdw\n" +
            "Lm9yZ8Jbc6jIIwGN+Pb7UYyTUCzjBvypv9S6ewlfyYYIvbIpApsMFiEEnfLD/m9p\n" +
            "o+7b1fuBaeiniKNue/0AAGQlAQCULqkfbB5T/62qn6o7U4BrmmC90gGksSdpyiur\n" +
            "c9OuIQEAgCeZNcTY8ocGWjsW+6h4A4a1UcmXpK9aqkHd1MNXxQ8=\n" +
            "=stBT\n" +
            "-----END PGP PUBLIC KEY BLOCK-----\n";
    private static final String TEST_CERT_BASE64 = "xjMEYkyuqxYJKwYBBAHaRw8BAQdAKayCvDEF4ZBcUJ9NPZlrG2jYOrNHDfYqOHpqD+3AkK7CwBEE" +
            "HxYKAIMFgmJMrqsFiQWkj70DCwkHCRBp6KeIo257/UcUAAAAAAAeACBzYWx0QG5vdGF0aW9ucy5z" +
            "ZXF1b2lhLXBncC5vcmeCqtr0bdgGRbTWiCD+lFXmcyAF5bE3vAgLRHfSSjXcDgMVCggCmwECHgEW" +
            "IQSd8sP+b2mj7tvV+4Fp6KeIo257/QAA5pYA/1LKPHcYBTFAIQEJo7cN6ZUJVupGCPI5g2F2qBJb" +
            "k5OTAQCmiYScX22UVuoOq/QIgMXhlVMb/oFs7ttWiSbTWHQMAM0EVGVzdMLAFAQTFgoAhgWCYkyu" +
            "qwWJBaSPvQMLCQcJEGnop4ijbnv9RxQAAAAAAB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdw" +
            "Lm9yZ/H8CTxR91Y1HOYAw910GaXZt7sOeypX5drWVoeA0VBHAxUKCAKZAQKbAQIeARYhBJ3yw/5v" +
            "aaPu29X7gWnop4ijbnv9AACN8QD8C747OJoLZpjTSiGcN3GlJbaWmdGGaFOMJktKIKgXdd8A/0gA" +
            "KE1gn12Jo0rl9sHpRMqKPNG1QGNHJ7X/H7PZE/kLzjMEYkyuqxYJKwYBBAHaRw8BAQdAJy+BAzLw" +
            "sFL9T+SwbyQxZhatOZZ7/xXlYJWKUu2M+UDCwMUEGBYKATcFgmJMrqsFiQWkj70JEGnop4ijbnv9" +
            "RxQAAAAAAB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdwLm9yZ9rmaNAa/me4nEhPdKRGMUSX" +
            "jv1R1ZGnlAoEtR/2NdU7ApsCvqAEGRYKAG8FgmJMrqsJEPYiaYJrDVrZRxQAAAAAAB4AIHNhbHRA" +
            "bm90YXRpb25zLnNlcXVvaWEtcGdwLm9yZws3xh5RCajUNu0Mre0wS0Xh+N6IxLYoGrlGjxrRBw6h" +
            "FiEEAj9WeADbcBGWuGqt9iJpgmsNWtkAAFhfAQD7Ouv336RMsPgE3JlKx9zGPZcU9HVib70Q2fXx" +
            "SVR7RQD+O9PEF7MqsIZXiTFGvkwLe97LoCBBw6LhO8YlDiIZAwwWIQSd8sP+b2mj7tvV+4Fp6KeI" +
            "o257/QAAdekA/jp1Nc5kihBPK/vsQSJkHtUeeYPZQz9wxeFgKjEVQiBVAP9lwdJzgC4FNdz0rpp6" +
            "xCG38GI2o97S1cr+ot3lkaZpBc44BGJMrqsSCisGAQQBl1UBBQEBB0D8iZDZOC0MMkZHO7UPpqhC" +
            "aW642O00LkrToWaKbaUoRAMBCAnCwAYEGBYKAHgFgmJMrqsFiQWkj70JEGnop4ijbnv9RxQAAAAA" +
            "AB4AIHNhbHRAbm90YXRpb25zLnNlcXVvaWEtcGdwLm9yZ8Jbc6jIIwGN+Pb7UYyTUCzjBvypv9S6" +
            "ewlfyYYIvbIpApsMFiEEnfLD/m9po+7b1fuBaeiniKNue/0AAGQlAQCULqkfbB5T/62qn6o7U4Br" +
            "mmC90gGksSdpyiurc9OuIQEAgCeZNcTY8ocGWjsW+6h4A4a1UcmXpK9aqkHd1MNXxQ8=";

    private static final ObjectMapper json = new ObjectMapper();

    @Test
    public void testSerializeDeserializeArmoredCert() throws JsonProcessingException {
        UploadRequest request = new UploadRequest(TEST_CERT_ARMORED);

        String val = json.writeValueAsString(request);
        request = json.readValue(val, UploadRequest.class);

        assertEquals(TEST_CERT_ARMORED, request.getKeyText());
    }

    @Test
    public void testSerializeDeserializeBase64() throws JsonProcessingException {
        byte[] rawCert = Base64.decode(TEST_CERT_BASE64);
        UploadRequest request = UploadRequest.fromBytes(rawCert);

        String val = json.writeValueAsString(request);
        request = json.readValue(val, UploadRequest.class);

        assertEquals(TEST_CERT_BASE64, request.getKeyText());
    }
}
