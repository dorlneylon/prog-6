package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.response.Color;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.net.InetSocketAddress;

import static itmo.lab6.server.UdpServer.commandHistory;

public final class HistoryCommand implements Action {

    private final InetSocketAddress address;

    public HistoryCommand(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public Response run() {
        return new Response(Color.PURPLE + "Command history:\n" + Color.RESET + String.join("\n", commandHistory.get(address)), ResponseType.SUCCESS);
    }
}
