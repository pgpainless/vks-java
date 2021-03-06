<!--
SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>

SPDX-License-Identifier: Apache-2.0
-->

# Verifying Key Server - Client API for Java

Client-side API for fetching keys from - and publishing keys to - Verifying OpenPGP Key Servers (VKS).

An example implementation of a Verifying Key Server is [Hagrid](https://gitlab.com/hagrid-keyserver/hagrid), which is running https://keys.openpgp.org.

## Modules

This repository contains the following modules:

* [vks-java](/vks-java): Client-side Java API for communicating with VKS servers
* [vks-java-cli](/vks-java-cli): CLI frontend for `vks-java`
