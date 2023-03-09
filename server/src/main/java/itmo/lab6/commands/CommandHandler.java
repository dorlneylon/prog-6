package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.basic.auxiliary.Convertible;
import static itmo.lab6.basic.baseenums.CommandEnum.*;
import itmo.lab6.basic.baseenums.CommandEnum;
import itmo.lab6.commands.implemented.*;
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
        commands.put(INFO, new InfoCommand(channel));
        commands.put(DEFAULT, new DefaultCommand(channel));
    }

    public static CommandEnum cast(String message) {
        return (Convertible.convert(message.split(" ")[0], CommandEnum.class) != null) ? Convertible.convert(message.split(" ")[0], CommandEnum.class) : DEFAULT;
    }

    public static void handlePacket(String message) throws Exception {
        commands.get(cast(message)).execute();
    }
}
