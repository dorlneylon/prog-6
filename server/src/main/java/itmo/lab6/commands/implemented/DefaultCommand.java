package itmo.lab6.commands.implemented;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DefaultCommand extends AbstractCommand {
    private DatagramChannel channel;
    public DefaultCommand(DatagramChannel channel) {
        this.channel = channel;
    }

    @Override
    public void execute() throws IOException {
        channel.send(ByteBuffer.wrap("пошел нахуй".getBytes()), UdpServer.clientAddress);
    }
}
