package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.UdpServer;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

public final class RemoveGreaterCommand implements Action {

    private final Long key;

    public RemoveGreaterCommand(Long key) {
        this.key = key;
    }

    @Override
    public Response run() {
        if (UdpServer.collection.removeGreater(key)) {
            return new Response("Greater elements have been successfully deleted", ResponseType.SUCCESS);
        }
        return new Response("There are no items in the collection larger than a given", ResponseType.ERROR);
    }
}
