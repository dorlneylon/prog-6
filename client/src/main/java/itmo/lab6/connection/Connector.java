package itmo.lab6.connection;

import java.io.IOException;
import java.net.*;

public class Connector {
    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;
    private final int socketTimeout = 8000;
    private byte[] buffer;

    private int BUFFER_SIZE;

    public Connector() throws Exception {
        this(8080);
    }

    public Connector(int port) throws Exception {
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(socketTimeout);
        this.address = InetAddress.getLocalHost();
        this.port = port;
    }

    public void setBufferSize(int size) throws SocketException {
        this.socket.setReceiveBufferSize(size);
        this.socket.setSendBufferSize(size);
        BUFFER_SIZE = size;
    }

    public void send(String message) throws Exception {
        this.send(message.getBytes());
    }

    public void send(byte[] bytes) throws Exception {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, this.address, port);
        this.socket.send(packet);
    }

    public String receive() throws IOException {
        this.buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        try {
            this.socket.receive(packet);
        } catch (SocketTimeoutException e) {
            return "Waiting time for reply from server exceeded... The server is not available.";
        }
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        this.socket.close();
    }
}
