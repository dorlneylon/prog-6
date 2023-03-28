package itmo.lab6.core;

import itmo.lab6.basic.utils.serializer.CommandSerializer;
import itmo.lab6.basic.utils.types.SubArrayIterator;
import itmo.lab6.commands.*;
import itmo.lab6.connection.Connector;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ClientCore {
    private final Connector connector;

    public ClientCore(InetAddress address, int port) {
        try {
            connector = new Connector(address, port);
            connector.setBufferSize(8192*8129);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CollectionValidator.setConnector(connector);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String[] userInput;
        while (true) {
            System.out.print("Enter command: ");
            userInput = scanner.nextLine().split(" ");
            if (userInput.length < 1) continue;
            String[] args = Arrays.copyOfRange(userInput, 1, userInput.length);
            CommandType commandType = CommandUtils.getCommandType(userInput[0]);
            Command command = CommandFactory.createCommand(commandType, args);
            if (command == null) continue;
            try {
                connector.send(CommandSerializer.serialize(command));
                String response = connector.receive();

                if (List.of(CommandType.SHOW, CommandType.PRINT_ASCENDING, CommandType.PRINT_DESCENDING).contains(commandType)) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    String[] movies = response.split("\\.\n");
                    SubArrayIterator<String> iterator = new SubArrayIterator<>(movies, 20);
                    while (iterator.hasNext()) {
                        for (String movie : List.of(iterator.next())) {
                            System.out.println(movie + (iterator.hasNext() ? "." : ""));
                        }
                        if (iterator.hasNext()) {
                            System.out.println("Press enter to continue, Press q to stop");
                            String ans = scanner.nextLine();
                            if (ans.equals("q")) break;
                        }
                    }
                    continue;
                }

                if (!response.isEmpty()) System.out.println(response);
            } catch (Exception e) {
                System.err.println("Unable to send/receive request/response to/from the server: " + e.getMessage());
            }
        }
    }
}
