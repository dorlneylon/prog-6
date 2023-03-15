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
    private final Object[] arguments;

    public Command(CommandType commandType, Object... arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * В этом методе вызывается конструктор у нужной нам команды. Затем она исполняется.
     */
    public final Response execute() {
        try {
            Class<?> executableClass = commandType.getExecutableClass();
            Constructor<?> constructor;
            Object instance;
            if (arguments == null || arguments.length == 0) {
                constructor = executableClass.getDeclaredConstructor();
                instance = constructor.newInstance();
            } else {
                Class<?> clazz = arguments[0].getClass();
                constructor = executableClass.getDeclaredConstructor(clazz);
                instance = constructor.newInstance(arguments);
            }
            return ((Action) instance).run();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            ServerLogger.getLogger().warning("Unable to execute command: " + e);
            return new Response("Unable to execute command: " + e, ResponseType.ERROR);
        }
    }

//    public final Response execute() {
//        Response response;
//        try {
//            return commandType.getExecutableClass().getDeclaredConstructor().newInstance(arguments).run();
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
//                 NoSuchMethodException e) {
//            ServerLogger.getLogger().warning("Unable to execute command: " + e);
//            return new Response("Unable to execute command: " + e, ResponseType.ERROR);
//        }
//    }
}
