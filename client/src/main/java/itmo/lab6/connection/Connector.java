package itmo.lab6.connection;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Connector {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buffer;

    private int BUFFER_SIZE = 8192*8192;

    public Connector() throws Exception {
        this.socket = new DatagramSocket();
        this.address = InetAddress.getLocalHost();
        this.port = 12345;
    }

    public Connector(int port) throws Exception {
        this.socket = new DatagramSocket();
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

    public String receive() throws Exception {
        this.buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        this.socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        this.socket.close();
    }
}
