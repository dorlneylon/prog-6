package itmo.lab6.commands;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

public class CommandHandler {
    private static DatagramChannel channel;

    public CommandHandler(DatagramChannel channel) {
        CommandHandler.channel = channel;
    }

    public static void handlePacket(String message) throws IOException {
        switch (message) {
            case "show":
                //channel.socket().send(new DatagramPacket(UdpServer.collection.show().getBytes(), UdpServer.collection.show().getBytes().length, UdpServer.clientAddress));
                break;
            case "info":
                break;
            case "help":
                break;
            case "clear":
                break;
            case "exit":
                break;
            case "insert":
                break;
            case "update":
                break;
            case "remove_key":
                break;
            case "remove_if_lower":
                break;
            case "remove_greater":
                break;
            case "execute_script":
                break;
            case "history":
                break;
            case "remove_by_mpaa_rating":
                break;
            case "print_ascending":
                break;
            case "print_descending_":
                break;
        }
    }
}
