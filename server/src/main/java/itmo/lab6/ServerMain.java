package itmo.lab6;

import itmo.lab6.server.UdpServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        UdpServer server = new UdpServer(5050);
        try {
            server.run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}