package itmo.lab6.commands;

import itmo.lab6.server.ServerLogger;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public final class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private final CommandType commandType;
    private Object[] arguments;

    public Command(CommandType commandType, Object... arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    /**
     * В этом методе вызывается конструктор у нужной нам команды. Затем она исполняется.
     */
    public Response execute() {
        try {
            Class<?> executableClass = commandType.getExecutableClass();
            Constructor<?> constructor;
            Object instance;
            if (arguments == null || arguments.length == 0) {
                constructor = executableClass.getDeclaredConstructor();
                instance = constructor.newInstance();
            } else {
                // Reading all arguments and getting constructor with this type(s).
                Class<?>[] argumentsTypes = new Class[arguments.length];
                for (int i = 0; i < arguments.length; i++) {
                    argumentsTypes[i] = arguments[i].getClass();
                }
                constructor = executableClass.getDeclaredConstructor(argumentsTypes);
                instance = constructor.newInstance(arguments);
            }
            return ((Action) instance).run();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            ServerLogger.getLogger().warning("Unable to execute command: " + e);
            return new Response("Unable to execute command: " + e, ResponseType.ERROR);
        }
    }
}
