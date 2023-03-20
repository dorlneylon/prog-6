package itmo.lab6.core;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.utils.parser.Parser;
import itmo.lab6.basic.utils.serializer.CommandSerializer;
import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandType;
import itmo.lab6.commands.CommandUtils;
import itmo.lab6.connection.Connector;

import java.io.IOException;
import java.util.Scanner;

public class ClientCore {
    private final Connector connector;

    public ClientCore(int port) {
        try {
            connector = new Connector(port);
            connector.setBufferSize(8192 * 8192);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String[] userInput;
        while (true) {
            System.out.print("Enter command: ");
            userInput = scanner.nextLine().split(" ");
            if (userInput.length < 1) continue;
            CommandType commandType = CommandUtils.getCommandType(userInput[0]);
            Command command = switch (commandType) {
                case EXIT -> {
                    System.out.println("Shutting down...");
                    System.exit(0);
                    yield null;
                }
                case EXECUTE_SCRIPT -> {
                    // TODO: ЕБЛЯ В ЖОПУ
                    yield null;
                }
                case INSERT -> {
                    Movie movie = Parser.readObject(Movie.class);
                    assert movie != null;
                    movie.setId(Long.parseLong(userInput[1]));
                    yield new Command(commandType, movie);
                }
                default -> new Command(commandType);
            };
            // TODO: Эта проверка скорее всего будет не нужна
            if (command == null) {
                continue;
            }
            try {
                connector.send(CommandSerializer.serialize(command));
            } catch (Exception e) {
                System.err.println("Unable to send request to the server: " + e);
                continue;
            }
            try {
                String response = connector.receive();
                System.out.println(response);
            } catch (IOException e) {
                System.err.println("Unable to receive response from the server: " + e);
            }
        }
    }
}
