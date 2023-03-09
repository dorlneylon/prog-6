package itmo.lab6.commands.implemented;

import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class InfoCommand extends AbstractCommand {
    DatagramChannel channel;

    public InfoCommand(DatagramChannel channel) {
        this.channel = channel;
    }

    @Override
    public void execute() throws IOException {
        channel.send(ByteBuffer.wrap(UdpServer.collection.info().getBytes()), UdpServer.clientAddress);
    }
}
