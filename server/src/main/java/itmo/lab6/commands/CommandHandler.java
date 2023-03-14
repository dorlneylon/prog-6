package itmo.lab6.commands;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandHandler {
    private static DatagramChannel channel;

    public CommandHandler(DatagramChannel channel) {
        CommandHandler.channel = channel;
    }
    // Это будет на клиенте
    //    public static CommandEnum cast(String message) {
    //        return (Convertible.convert(message.split(" ")[0], CommandEnum.class) != null) ? Convertible.convert(message.split(" ")[0], CommandEnum.class) : DEFAULT;
    //    }

    public static void handlePacket(InetSocketAddress sender, byte[] bytes) throws Exception {
        ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Command command = (Command) objectInputStream2.readObject();
        channel.send(ByteBuffer.wrap(command.execute().getMessage().getBytes()), sender);
    }
}
