package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.ClientAddress;
import itmo.lab6.server.response.Color;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.net.Inet4Address;
import java.net.InetAddress;

import static itmo.lab6.server.UdpServer.commandHistory;

public final class HistoryCommand implements Action {

    private final InetAddress address;
    private final Integer port;

    public HistoryCommand(Inet4Address address, Integer port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public Response run() {
        return new Response(Color.PURPLE + "Command history:\n"
                + Color.RESET + String.join("\n", commandHistory.get(new ClientAddress(address, port))),
                ResponseType.SUCCESS);
    }
}
