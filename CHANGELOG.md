<!--
SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>

SPDX-License-Identifier: Apache-2.0
-->

# Changelog

## 0.1.4-SNAPSHOT
- Bump `bc-util` to `1.82`
- Bump `logback` to `1.5.13`
- Bump `lombok` to `1.18.42`
- Upgrade build system
  - Bump gradle to `8.8`
  - Raise minimum JVM API level to 11
  - Bump `checkstyle` to `10.26.0`
  - 

## 0.1.3
- Bump `bc-util` to `1.75`
- Bump `jackson-databind` to `2.15.2`
- Add support for resource bundles for i18n

## 0.1.2
- Bump `slf4j` to `1.7.36`
- Bump `logback` to `1.2.11`
- Bump `lombok` to `1.18.24`
- CLI: Set `keys.openpgp.org` as default key server
- Add name and description to parent command

## 0.1.1
- Add `vks-java-cli`: CLI frontend for `vks-java`
- Bump Bouncy Castle dependencies to `1.71`

## 0.1.0
- Initial release
  - `vks-java`: Client side API to communicate with Verifying Key Servers
