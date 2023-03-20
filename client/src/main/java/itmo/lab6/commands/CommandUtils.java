package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Convertible;

public class CommandUtils {
    /**
     * Returns command type by user input
     *
     * @param message user input
     * @return CommandType by message
     */
    public static CommandType getCommandType(String message) {
        CommandType commandTypeEnum = Convertible.convert(message.split(" ")[0], CommandType.class);
        return (commandTypeEnum != null) ? commandTypeEnum : CommandType.DEFAULT;
    }
}
