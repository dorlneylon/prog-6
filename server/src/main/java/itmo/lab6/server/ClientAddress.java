package itmo.lab6.server;

import java.net.InetAddress;

public record ClientAddress(InetAddress address, int port) {
}
