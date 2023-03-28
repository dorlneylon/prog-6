package itmo.lab6.server;

import itmo.lab6.basic.moviecollection.MovieCollection;
import itmo.lab6.utils.SizedStack;

import java.io.ByteArrayOutputStream;
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
    private static final int BUFFER_SIZE = 1025;
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
                        ByteBuffer buffer = ByteBuffer.allocate(1025);
                        // Receiving first packet
                        InetSocketAddress inetSocketAddress = (InetSocketAddress) keyChannel.receive(buffer);
                        ClientAddress clientAddress = new ClientAddress(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                        if (!commandHistory.containsKey(clientAddress))
                            commandHistory.put(clientAddress, new SizedStack<>(7));
                        buffer.flip();
                        byte[] data = new byte[buffer.limit()];
                        buffer.get(data);
                        buffer.clear();
                        boolean hasNext = (data[data.length - 1] & 0xFF) == 1; // last byte is a flag
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bos.write(data, 0, data.length - 1);
                        while (hasNext) {
                            keyChannel.receive(buffer);
                            buffer.flip();
                            data = new byte[buffer.limit()];
                            buffer.get(data);
                            buffer.clear();
                            hasNext = (data[data.length - 1] & 0xFF) == 1; // last byte is a flag
                            bos.write(data, 0, data.length - 1);
                        }
                        try {
                            handlePacket(inetSocketAddress, bos.toByteArray());
                        } catch (Exception e) {
                            keyChannel.send(ByteBuffer.wrap("ERROR: Something went wrong...".getBytes()), inetSocketAddress);
                            ServerLogger.getLogger().warning(e.toString());
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