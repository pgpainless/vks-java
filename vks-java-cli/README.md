<!--
SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>

SPDX-License-Identifier: Apache-2.0
-->

# VKS-Java-CLI

[![javadoc](https://javadoc.io/badge2/org.pgpainless/vks-java-cli/javadoc.svg)](https://javadoc.io/doc/org.pgpainless/vks-java-cli)
[![Maven Central](https://badgen.net/maven/v/maven-central/org.pgpainless/vks-java-cli)](https://search.maven.org/artifact/org.pgpainless/vks-java-cli)

Command Line Frontend for VKS-Java

## Building

To build the CLI app, use `gradle build`. An archive containing an executable can then be found in `vks-java-cli/build/distributions/`.  
Extract it and navigate to the `bin` subdirectory, where you can find `vks-java-cli`/`vks-java-cli.bat` executables.

## Usage Examples

```shell
Usage: vkscli [COMMAND]
Commands:
  help                  Displays help information about the specified command
  get                   Retrieve an OpenPGP certificate from the key server
  upload                Upload an OpenPGP certificate to the key server
  request-verification  Request verification for unverified user-ids
```

By default, the CLI application uses `https://keys.openpgp.org` as key server.  
To use another VKS, use the option `--key-server https://your.key.server` in any command.

To retrieve a key from a Verifying Key Server, use the `get` subcommand:

```shell
$ ./vks-java-cli get -e vanitasvitae@fsfe.org > foo.asc
$ ./vks-java-cli get -f 7F9116FEA90A5983936C7CFAA027DB2F3E1E118A > foo.asc
$ ./vks-java-cli get -i -2535611045697927659 > foo.asc
```

To upload a key, use the `upload` subcommand:

```shell
$ ./vks-java-cli upload -r < foo.asc
```

The option `-r` automatically requests verification mails for unpublished mail addresses.

To manually request verification mails, use the `request-verification` subcommand, passing it the token acquired by the `upload` command:

```shell
$ ./vks-java-cli request-verification -t <upload-token> -a foo@bar.baz -a other@email.address
```
