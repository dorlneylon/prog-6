package itmo.lab6.commands.implemented;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.commands.CommandHandler;

import java.nio.channels.DatagramChannel;

public class ExitCommand implements Command {
    CommandHandler handler;

    public ExitCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() throws Exception {
        handler.getChannel().close();
    }
}
