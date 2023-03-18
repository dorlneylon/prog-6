package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.UdpServer;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

public final class PrintDescendingCommand implements Action {
    @Override
    public Response run() {
        return new Response(UdpServer.collection.printDescending(), ResponseType.INFO);
    }
}
