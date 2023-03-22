package itmo.lab6.core;

import itmo.lab6.basic.utils.serializer.CommandSerializer;
import itmo.lab6.commands.*;
import itmo.lab6.connection.Connector;

import java.util.Arrays;
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
            if (command == null) {
                System.out.println("Invalid or unknown command.");
                continue;
            }
            try {
                connector.send(CommandSerializer.serialize(command));
                String response = connector.receive();
                if (!response.isEmpty()) System.out.println(response);
            } catch (Exception e) {
                System.err.println("Unable to send/receive request/response to/from the server: " + e.getMessage());
            }
        }
    }
}
