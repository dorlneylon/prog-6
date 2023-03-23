package itmo.lab6.commands.implemented;
import itmo.lab6.basic.baseenums.MpaaRating;
import itmo.lab6.commands.Action;
import itmo.lab6.server.UdpServer;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

public final class RemoveByMpaaRating implements Action {
    private final MpaaRating mpaaRating;

    public RemoveByMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    @Override
    public Response run() {
        if (UdpServer.collection.removeByRating(mpaaRating)) return new Response("Successfully deleted", ResponseType.SUCCESS);
        return new Response("No such element", ResponseType.SUCCESS);
    }
}
