package itmo.lab6.commands.implemented;

import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ShowCommand extends AbstractCommand {
    DatagramChannel channel;

    public ShowCommand(DatagramChannel channel) {
        this.channel = channel;
    }

    @Override
    public void execute() throws IOException {
        channel.send(ByteBuffer.wrap(UdpServer.collection.show().getBytes()), UdpServer.clientAddress);
    }
}
