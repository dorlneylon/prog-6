package itmo.lab6.server;

import itmo.lab6.basic.moviecollection.MovieCollection;
import itmo.lab6.utils.SizedStack;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;

import static itmo.lab6.commands.CommandHandler.handlePacket;
import static itmo.lab6.commands.CommandHandler.setChannel;

public class UdpServer {
    private static final int BUFFER_SIZE = 8192 * 8192;
    public static MovieCollection collection;
    public static HashMap<ClientAddress, SizedStack<String>> commandHistory = new HashMap<>();
    private final int port;
    private DatagramChannel datagramChannel;
    private Selector selector;

    public UdpServer(MovieCollection collection, int port) {
        this.port = port;
        UdpServer.collection = collection;
    }

    public void run() {
        ExitThread exitThread = new ExitThread();
        exitThread.start();
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            ServerLogger.getLogger().info("Starting server on port " + port);
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isReadable()) {
                        DatagramChannel keyChannel = (DatagramChannel) key.channel();
                        setChannel(keyChannel);
                        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                        InetSocketAddress inetSocketAddress = (InetSocketAddress) keyChannel.receive(buffer);
                        ClientAddress clientAddress = new ClientAddress(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                        if (!commandHistory.containsKey(clientAddress))
                            commandHistory.put(clientAddress, new SizedStack<>(7));
                        buffer.flip();
                        byte[] data = new byte[buffer.limit()];
                        buffer.get(data);
                        try {
                            handlePacket(inetSocketAddress, data);
                        } catch (Exception e) {
                            // Думаю, нам стоит убрать этот send.
                            keyChannel.send(ByteBuffer.wrap(e.getMessage().getBytes()), inetSocketAddress);
                            ServerLogger.getLogger().warning(e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Exception: " + e.getMessage());
        } finally {
            try {
                selector.close();
                datagramChannel.close();
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Exception while closing channel or selector: " + e.getMessage());
            }
        }
    }
}