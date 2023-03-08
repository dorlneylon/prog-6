package itmo.lab6.connection;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connector {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buffer;

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

    public void send(String message) throws Exception {
        this.buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length, this.address, port);
        this.socket.send(packet);
    }

    public String receive() throws Exception {
        this.buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        this.socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        this.socket.close();
    }
}
