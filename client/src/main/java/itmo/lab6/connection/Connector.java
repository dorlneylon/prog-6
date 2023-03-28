package itmo.lab6.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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
        final int chunkSize = 1024; // 1Kb chunk size + number of chunks
        int numChunks = (int) Math.ceil((double) bytes.length / chunkSize);
        for (int i = 0; i < numChunks; i++) {
            int offset = i * chunkSize;
            int length = Math.min(bytes.length - offset, chunkSize);
            byte[] chunk = new byte[length + 1];
            if (i != 0 && i % 20 == 0) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            chunk[length] = (numChunks == 1 || i + 1 == numChunks) ? (byte) 0 : (byte) 1; // has next flag
            System.arraycopy(bytes, offset, chunk, 0, length);
            DatagramPacket datagramPacket = new DatagramPacket(chunk, length + 1, this.address, port);
            socket.send(datagramPacket);
        }
    }

    /**
     * Receives bytes from remote server and transforms them into string
     *
     * @return string message
     * @throws IOException Receiving exception
     */
    public String receive() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1025];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        boolean hasNext = (packet.getData()[packet.getLength() - 1] & 0xFF) == 1; // last byte is a flag
        bos.write(packet.getData(), 0, packet.getLength() - 1);
        while (hasNext) {
            socket.receive(packet);
            hasNext = (packet.getData()[packet.getLength() - 1] & 0xFF) == 1;
            bos.write(packet.getData(), 0, packet.getLength() - 1);
        }
        return bos.toString();
    }
}
