package itmo.lab6.core;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.utils.parser.Parser;
import itmo.lab6.basic.utils.serializer.CommandSerializer;
import itmo.lab6.basic.utils.types.SizedStack;
import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandType;
import itmo.lab6.commands.CommandUtils;
import itmo.lab6.connection.Connector;

import java.util.*;
import java.util.function.Function;


public class ClientCore {
    private final Connector connector;
    private Map<CommandType, Function<String[], Command>> commands = new HashMap<>();

    public ClientCore(int port) {
        try {
            connector = new Connector(port);
            connector.setBufferSize(8192 * 8192);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initCommands() {
        commands.put(CommandType.EXIT, args -> {
            System.out.println("Shutting down...");
            System.exit(0);
            return null;
        });
        commands.put(CommandType.EXECUTE_SCRIPT, args -> {
            // TODO: ЕБЛЯ В ЖОПУ
            return null;
        });
        List.of(CommandType.HELP, CommandType.PRINT_ASCENDING, CommandType.HISTORY, CommandType.PRINT_DESCENDING,
                CommandType.INFO, CommandType.SHOW, CommandType.CLEAR).forEach(commandType -> {
                    commands.put(commandType, args -> new Command(commandType));
            });
        List.of(CommandType.REMOVE_GREATER, CommandType.REMOVE_KEY).forEach(commandType -> {
            commands.put(commandType, args -> {
                if (args.length < 2) { System.out.println("Not enough arguments for command " + commandType.name()); return new Command(CommandType.SERVICE, ""); }
                try { return new Command(commandType, Long.parseLong(args[1])); }
                catch (NumberFormatException e) { System.out.println("Invalid argument for command " + commandType.name()); return new Command(CommandType.SERVICE, ""); }
            });
        });

        // TODO: Сократить код.
        List.of(CommandType.INSERT, CommandType.UPDATE, CommandType.REPLACE_IF_LOWER).forEach(commandType -> {
            commands.put(commandType, args -> {
                if (args.length < 2) { System.out.println("Not enough arguments for command " + commandType.name()); return new Command(CommandType.SERVICE, ""); }
                Long key;
                try {
                    key = Long.parseLong(args[1]);
                    if (!checkIfExists(commandType, key)) {
                        System.out.println("Key " + key + " is not compatible with the command " + commandType.name() + ".");
                        return new Command(CommandType.SERVICE, "");
                    }
                }
                catch (Exception e) {
                    System.out.println("Invalid argument for command " + commandType.name());
                    return new Command(CommandType.SERVICE, "");
                }

                Movie movie = Parser.readObject(Movie.class);
                assert movie != null;
                movie.setId(key);
                return new Command(commandType, movie);
            });
        });
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String[] userInput;
        initCommands();

        while (true) {
            System.out.print("Enter command: ");
            userInput = scanner.nextLine().split(" ");
            if (userInput.length < 1) continue;
            CommandType commandType = CommandUtils.getCommandType(userInput[0]);
            Function<String[], Command> commandFunc = commands.get(commandType);

            if (commandFunc == null) { System.out.println("Invalid command."); continue; }

            try {
                connector.send(CommandSerializer.serialize(commandFunc.apply(userInput)));
                String response = connector.receive();
                if (!response.isEmpty()) System.out.println(response);
            } catch (Exception e) {
                System.err.println("Unable to send/receive request/response to/from the server: " + e.getMessage());
            }
        }
    }


    /**
     *  Метод проверяет:
     *  1. Если команда INSERT, то проверяет, не существует ли ключ в коллекции. Если существует, то возвращает false.
     *  2. Если команда UPDATE или REPLACE_IF_LOWER, то проверяет, существует ли ключ в коллекции. Если не существует, то возвращает false.
     * */
    private Boolean checkIfExists(CommandType command, Long key) throws Exception {
        connector.send(CommandSerializer.serialize(new Command(CommandType.SERVICE, "check_id %d".formatted(key))));
        return (command.equals(CommandType.INSERT) && !Boolean.parseBoolean(connector.receive())) || ((command.equals(CommandType.UPDATE) || command.equals(CommandType.REPLACE_IF_LOWER) ) && Boolean.parseBoolean(connector.receive()));
    }
}
