// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client;

import pgp.vks.client.v1.dto.UploadResponse;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

public interface Upload {

    UploadResponse cert(@Nonnull InputStream certInStream) throws IOException;

}
