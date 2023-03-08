package itmo.lab6.server;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.moviecollection.MovieCol;
import itmo.lab6.commands.CommandHandler;
import itmo.lab6.server.utils.logger.LogLevel;
import itmo.lab6.server.utils.logger.ServerLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UdpServer {
    private final int port;
    public static MovieCol collection;
    private boolean isRunning = true;
    private final int BUFFER_SIZE = 1024;

    private static InetSocketAddress clientAddress;

    public UdpServer(MovieCol collection, int port) {
        this.port = port;
        this.collection = collection;
    }

    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            CommandHandler handler = new CommandHandler(channel);

            channel.configureBlocking(false);
            ServerLogger.log("Starting server on port " + port, LogLevel.INFO);
            channel.socket().bind(new InetSocketAddress(port));
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (isRunning) {
                buffer.clear();
                clientAddress = (InetSocketAddress) channel.receive(buffer);
                if (clientAddress != null) {
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    String message = new String(data);

                    ServerLogger.log("Received message from " + clientAddress.getAddress() + ": " + message, LogLevel.INFO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}