package itmo.lab6.server;

import itmo.lab6.server.utils.logger.LogLevel;
import itmo.lab6.server.utils.logger.ServerLogger;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class UdpServer {

    DatagramSocket socket;
    DatagramPacket packet;

    private final int port;
    private final InetAddress host;
    private boolean isRunning = true;

    public UdpServer(int port) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        this.port = port;
        try {
            host = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            ServerLogger.log("Unable to get host address", LogLevel.ERROR);
            throw new RuntimeException(e);
        }
    }

    public void run() throws SocketException {
        ServerLogger.log("Starting server on port " + port, LogLevel.INFO);
        socket = new DatagramSocket(port, host);
        ServerLogger.log("Server is listening on port " + port, LogLevel.INFO);
        while (isRunning) {
            ServerLogger.log("Waiting for a UDP packet...", LogLevel.INFO);
            packet = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                ServerLogger.log("Failed to receive UDP packet", LogLevel.ERROR);
                continue;
            }
            displayPacketDetails(packet);
        }
    }

    private static void displayPacketDetails(DatagramPacket packet) {
        byte[] msgBuffer = packet.getData();
        int length = packet.getLength();
        int offset = packet.getOffset();

        int remotePort = packet.getPort();
        InetAddress remoteAddr = packet.getAddress();
        String msg = new String(msgBuffer, offset, length);

        System.out.println("Received a  packet:[IP Address=" + remoteAddr
                + ", port=" + remotePort + ", message=" + msg + "]");
    }
}
