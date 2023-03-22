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
        return (command.equals(CommandType.INSERT) && !Boolean.parseBoolean(connector.receive())) || ((command.equals(CommandType.UPDATE) || command.equals(CommandType.REPLACE_IF_LOWER)) && Boolean.parseBoolean(connector.receive()));
    }
}

