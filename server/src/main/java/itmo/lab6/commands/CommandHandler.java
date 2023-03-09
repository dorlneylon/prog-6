package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.basic.auxiliary.Convertible;
import static itmo.lab6.basic.baseenums.CommandEnum.*;
import itmo.lab6.basic.baseenums.CommandEnum;
import itmo.lab6.commands.implemented.ShowCommand;
import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler {
    private static DatagramChannel channel;
    private static HashMap<CommandEnum, Command> commands = new HashMap<>();

    public CommandHandler(DatagramChannel channel) {

        this.channel = channel;
        commands.put(SHOW, new ShowCommand(channel));
    }

    public static CommandEnum cast(String message) {
        return (Convertible.convert(message.split(" ")[0], CommandEnum.class) != null) ? Convertible.convert(message.split(" ")[0], CommandEnum.class) : DEFAULT;
    }

    public static void handlePacket(String message) throws Exception {
        commands.get(cast(message)).execute();
//        switch (Objects.requireNonNull(Convertible.convert(message.split(" ")[0], CommandEnum.class))) {
//            case SHOW:
//                // send the collection to the client
//                channel.send(ByteBuffer.wrap(UdpServer.collection.show().getBytes()), UdpServer.clientAddress);
//                //channel.socket().send(new DatagramPacket(UdpServer.collection.show().getBytes(), UdpServer.collection.show().getBytes().length, UdpServer.clientAddress));
//                break;
//            case INFO:
//                if (!UdpServer.collection.isEmpty()) channel.send(ByteBuffer.wrap(UdpServer.collection.info().getBytes()), UdpServer.clientAddress);
//                else channel.send(ByteBuffer.wrap("Collection is empty".getBytes()), UdpServer.clientAddress);
//                break;
//            case HELP:
//                break;
//            case CLEAR:
//                break;
//            case EXIT:
//                break;
//            case INSERT:
//                break;
//            case UPDATE:
//                break;
//            case REMOVE_KEY:
//                break;
//            case REMOVE_IF_LOWER:
//                break;
//            case REMOVE_GREATER:
//                break;
//            case EXECUTE_SCRIPT:
//                break;
//            case HISTORY:
//                break;
//            case REMOVE_BY_MPAA_RATING:
//                break;
//            case PRINT_ASCENDING:
//                break;
//            case PRINT_DESCENDING:
//                break;
//            default:
//                break;
//        }
    }
}
