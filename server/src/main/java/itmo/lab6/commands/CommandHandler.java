package itmo.lab6.commands;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static itmo.lab6.server.UdpServer.commandHistory;

public class CommandHandler {
    private static DatagramChannel channel;

    public static void setChannel(DatagramChannel channel) {
        CommandHandler.channel = channel;
    }

    // Это будет на клиенте
    //    public static CommandEnum cast(String message) {
    //        return (Convertible.convert(message.split(" ")[0], CommandEnum.class) != null) ? Convertible.convert(message.split(" ")[0], CommandEnum.class) : DEFAULT;
    //    }
    public static void handlePacket(InetSocketAddress sender, byte[] bytes) throws Exception {
        ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Command command = (Command) objectInputStream2.readObject();
        // TODO: избавиться от if'а
        if (command.getCommandType() == CommandType.HISTORY) command.setArguments(sender);
        channel.send(ByteBuffer.wrap(command.execute().getMessage().getBytes()), sender);

        commandHistory.get(sender).push(command.getCommandType().toString());

        // todo: сохранение после каждого действия.
    }
}
