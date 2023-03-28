package itmo.lab6.commands;

import itmo.lab6.basic.utils.serializer.CommandSerializer;
import itmo.lab6.connection.Connector;

/**
 * This class is used to store collection data validators
 */
public final class CollectionValidator {

    private static Connector connector;

    public static void setConnector(Connector connector) {
        CollectionValidator.connector = connector;
    }

    /**
     * Метод проверяет:
     * 1. Если команда INSERT, то проверяет, не существует ли ключ в коллекции. Если существует, то возвращает false.
     * 2. Если команда UPDATE или REPLACE_IF_LOWER, то проверяет, существует ли ключ в коллекции. Если не существует, то возвращает false.
     */
    public static Boolean checkIfExists(CommandType command, Long key) throws Exception {
        connector.send(CommandSerializer.serialize(new Command(CommandType.SERVICE, "check_id %d".formatted(key))));
        Boolean receivedStatus = Boolean.parseBoolean(connector.receive());
        if (command.equals(CommandType.INSERT)) {
            // True if key does not exist
            return receivedStatus;
        } else if (command.equals(CommandType.UPDATE) || command.equals(CommandType.REPLACE_IF_LOWER)) {
            // True if key exists
            return !receivedStatus;
        }
        return false;
    }

    public static Boolean isMovieValid(CommandType type, String[] args) {
        if (args.length < 1) {
            System.err.println("Not enough arguments for command " + type.name());
            return null;
        }
        long key;
        try {
            key = Long.parseLong(args[0]);
            if (checkIfExists(type, key)) {
                System.err.println("Key " + key + " is not compatible with the command " + type.name() + ".");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Invalid argument for command " + type.name());
            return false;
        }
        return true;
    }
}

