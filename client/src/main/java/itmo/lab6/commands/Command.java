package itmo.lab6.commands;

import java.io.Serial;
import java.io.Serializable;


public final class Command implements Serializable {
    /**
     * Class version for serializing simplified command class
     */
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * Type of the command
     */
    private final CommandType commandType;
    /**
     * Arguments for the command
     */
    private final Object[] arguments;

    public Command(CommandType commandType, Object... arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }
}
