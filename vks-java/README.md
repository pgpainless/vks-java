<!--
SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>

SPDX-License-Identifier: Apache-2.0
-->

# VKS-Java

[![javadoc](https://javadoc.io/badge2/org.pgpainless/vks-java/javadoc.svg)](https://javadoc.io/doc/org.pgpainless/vks-java)
[![Maven Central](https://badgen.net/maven/v/maven-central/org.pgpainless/vks-java)](https://search.maven.org/artifact/org.pgpainless/vks-java)

Client Side API for Communicating with Verifying Key Servers.

```java
VKS vks = new VKSImpl("https://keys.openpgp.org/");

// Key Discovery via Email, key-id or fingerprint
InputStream bobsKey = vks.get().byEmail("bob@pgpainless.org");

// Upload Key to the VKS
InputStream myKey = ...
Upload.Response uploadResponse = vks.upload().cert(myKey);

// Request email verification of user-ids
RequestVerify.Response verifyResponse = vks.requestVerification()
        .forEmailAddress("bob@pgpainless.org")
        .execute(uploadResponse.getToken());
```
