package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.basic.auxiliary.Convertible;
import static itmo.lab6.basic.baseenums.CommandEnum.*;
import itmo.lab6.basic.baseenums.CommandEnum;
import itmo.lab6.commands.implemented.*;
import itmo.lab6.server.UdpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler {
    private DatagramChannel channel;
    private InetSocketAddress address;
    private static HashMap<CommandEnum, Command> commands = new HashMap<>();
    private String command;

    public CommandHandler(DatagramChannel channel, InetSocketAddress address) {
        this.channel = channel;
        this.address = address;
        commands.put(SHOW, new ShowCommand(this));
        commands.put(INFO, new InfoCommand(this));
        commands.put(DEFAULT, new DefaultCommand(this));
        commands.put(HELP, new HelpCommand(this));
        commands.put(EXIT, new ExitCommand(this));
        commands.put(INSERT, new InsertCommand(this));
    }

    public static CommandEnum cast(String message) {
        return (Convertible.convert(message.split(" ")[0], CommandEnum.class) != null) ? Convertible.convert(message.split(" ")[0], CommandEnum.class) : DEFAULT;
    }

    public void handlePacket(String message) throws Exception {
        this.command = message;
        commands.get(cast(message)).execute();
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public String getCommand() {
        return command;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
