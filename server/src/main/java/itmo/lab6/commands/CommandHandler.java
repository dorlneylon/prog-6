package itmo.lab6.commands;

import itmo.lab6.ServerMain;
import itmo.lab6.server.ClientAddress;
import itmo.lab6.xml.Xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static itmo.lab6.server.UdpServer.collection;
import static itmo.lab6.server.UdpServer.commandHistory;

public class CommandHandler {
    private static DatagramChannel channel;

    public static void setChannel(DatagramChannel channel) {
        CommandHandler.channel = channel;
    }

    public static void handlePacket(InetSocketAddress sender, byte[] bytes) throws Exception {
        ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Command command = (Command) objectInputStream2.readObject();
        // TODO: избавиться от if'а
        // if (command.getCommandType() == CommandType.HISTORY) command.setArguments(sender);
        channel.send(ByteBuffer.wrap(command.execute().getMessage().getBytes()), sender);
        if (!command.getCommandType().equals(CommandType.SERVICE))
            commandHistory.get(new ClientAddress(sender.getAddress(), sender.getPort())).push(command.getCommandType().toString());
        new Xml(new File(ServerMain.collectionFileName), true).newWriter().writeCollection(collection);
    }
}
