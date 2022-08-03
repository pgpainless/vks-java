// SPDX-FileCopyrightText: 2022 Paul Schaub <vanitasvitae@fsfe.org>
//
// SPDX-License-Identifier: Apache-2.0

package pgp.vks.client.cli;

import pgp.vks.client.VKS;
import pgp.vks.client.VKSImpl;
import picocli.CommandLine;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

@CommandLine.Command(
        name = "vks",
        resourceBundle = "msg_vks",
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
        CommandLine cmd = new CommandLine(VKSCLI.class);
        cmd.setExitCodeExceptionMapper(new CommandLine.IExitCodeExceptionMapper() {
                    @Override
                    public int getExitCode(Throwable exception) {
                        return 1;
                    }
                });
        cmd.getSubcommands().get("help").setResourceBundle(ResourceBundle.getBundle("msg_help", Locale.getDefault()));
        return cmd.setCommandName("vkscli")
                .execute(args);
    }

    public VKS getApi() throws MalformedURLException {
        return new VKSImpl(keyServer);
    }

    public static class KeyServerMixin {

        @CommandLine.ParentCommand
        VKSCLI parent;

        @CommandLine.Option(names = "--key-server",
                paramLabel = "KEYSERVER")
        public void setKeyServer(String keyServer) {
            parent.keyServer = keyServer;
        }
    }
}
