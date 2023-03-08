package itmo.lab6.server;

import itmo.lab6.basic.moviecollection.MovieCollection;
import itmo.lab6.commands.CommandHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpServer {
    private static final int BUFFER_SIZE = 1024;
    public static MovieCollection collection;
    private final int port;
    private InetSocketAddress clientAddress;

    public UdpServer(MovieCollection collection, int port) {
        this.port = port;
        UdpServer.collection = collection;
    }

    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            CommandHandler handler = new CommandHandler(channel);

            channel.configureBlocking(false);
            ServerLogger.getLogger().info("Starting server on port " + port);
            channel.socket().bind(new InetSocketAddress(port));
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true) {
                buffer.clear();
                clientAddress = (InetSocketAddress) channel.receive(buffer);
                if (clientAddress != null) {
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    String message = new String(data);

                    ServerLogger.getLogger().info("Received message from " + clientAddress.getAddress() + ": " + message);
                }
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Exception: " + e.getMessage());
        }
    }
}