package itmo.lab6.commands.implemented;

import itmo.lab6.commands.CommandHandler;
import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ShowCommand extends AbstractCommand {
    CommandHandler handler;

    public ShowCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() throws IOException {
        handler.getChannel().send(ByteBuffer.wrap(UdpServer.collection.show().getBytes()), handler.getAddress());
    }
}
