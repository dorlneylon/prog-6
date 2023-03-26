package itmo.lab6.connection;

import java.io.IOException;
import java.net.*;

/**
 * Connector is used to connect to a remote server
 */
public class Connector {
    /**
     * Socket for UDP connection
     */
    private static DatagramSocket socket;
    private final InetAddress address;
    private final int port;
    private final static int socketTimeout = 8000;

    private int BUFFER_SIZE;


    /**
     * Connector constructor with specified server port
     *
     * @param port server port
     * @throws Exception Socket exception
     */
    public Connector(InetAddress address, int port) throws Exception {
        socket = new DatagramSocket();
        socket.setSoTimeout(socketTimeout);
        this.address = address;
        this.port = port;
    }

    /**
     * Return port of client
     *
     * @return localhost port
     */
    public static int getPort() {
        return socket.getLocalPort();
    }

    public void setBufferSize(int size) throws SocketException {
        socket.setReceiveBufferSize(size);
        socket.setSendBufferSize(size);
        BUFFER_SIZE = size;
    }

    /**
     * Sends string to remote server
     *
     * @param message string message
     * @throws Exception sending exception
     */
    public void send(String message) throws Exception {
        this.send(message.getBytes());
    }

    /**
     * Sends bytes to remote server
     *
     * @param bytes bytes to send
     * @throws Exception sending exceptions
     */
    public void send(byte[] bytes) throws Exception {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, this.address, port);
        socket.send(packet);
    }

    /**
     * Receives bytes from remote server and transforms them into string
     *
     * @return string message
     * @throws IOException Receiving exception
     */
    public String receive() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
        } catch (SocketTimeoutException e) {
            return "Waiting time for reply from server exceeded... The server is not available.";
        }
        return new String(packet.getData(), 0, packet.getLength());
    }
}
