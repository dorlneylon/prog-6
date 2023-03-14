package itmo.lab6.commands;

import java.io.Serial;
import java.io.Serializable;


public final class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private final CommandType commandType;
    private final Object[] arguments;

    public Command(CommandType commandType, Object... arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }
}
