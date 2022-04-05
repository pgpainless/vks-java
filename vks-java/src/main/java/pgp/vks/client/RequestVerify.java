// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import pgp.vks.client.v1.dto.VerificationResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface RequestVerify {

    default VerificationResponse forEmailAddresses(List<String> emailAddresses, String uploadToken)
            throws IOException {
        return forEmailAddresses(emailAddresses, uploadToken, Arrays.asList("en_US", "en_GB"));
    }

    VerificationResponse forEmailAddresses(List<String> emailAddresses, String uploadToken, List<String> locale)
            throws IOException;
}
