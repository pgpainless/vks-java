// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.cli;

import pgp.vks.client.VKS;
import pgp.vks.client.VKSImpl;
import picocli.CommandLine;

import java.net.MalformedURLException;

@CommandLine.Command(
        name = "vks",
        description = "Interact with Verifying Key Servers",
        subcommands = {
                CommandLine.HelpCommand.class,
                GetCmd.class,
                UploadCmd.class,
                RequestVerificationCmd.class
        }
)
public class VKSCLI {

    String keyServer = "https://keys.openpgp.org";

    public static void main(String[] args) {
        int exitCode = execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    public static int execute(String[] args) {
        return new CommandLine(VKSCLI.class)
                .setExitCodeExceptionMapper(new CommandLine.IExitCodeExceptionMapper() {
                    @Override
                    public int getExitCode(Throwable exception) {
                        return 1;
                    }
                })
                .setCommandName("vkscli")
                .execute(args);
    }

    public VKS getApi() throws MalformedURLException {
        return new VKSImpl(keyServer);
    }

    public static class KeyServerMixin {

        @CommandLine.ParentCommand
        VKSCLI parent;

        @CommandLine.Option(names = "--key-server",
                description = "Address of the Verifying Key Server.\nDefaults to 'https://keys.openpgp.org'",
                paramLabel = "KEYSERVER")
        public void setKeyServer(String keyServer) {
            parent.keyServer = keyServer;
        }
    }
}
