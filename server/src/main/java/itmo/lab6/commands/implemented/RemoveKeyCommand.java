package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.UdpServer;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

public final class RemoveKeyCommand implements Action {
    private final Long key;

    public RemoveKeyCommand(Long key) {
        this.key = key;
    }

    @Override
    public Response run() {
        if (UdpServer.collection.removeByKey(key)) {
            return new Response("Movie with key %d deleted successfully".formatted(key), ResponseType.SUCCESS);
        }
        return new Response("It is not possible to delete a Movie with key=%d, because there is no Movie with this key.".formatted(key), ResponseType.ERROR);
    }
}
