package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import static itmo.lab6.server.UdpServer.collection;

public class ClearCommand implements Action {
    @Override
    public Response run() {
        collection.clear();
        return new Response("Collection cleaned successfully", ResponseType.SUCCESS);
    }
}
