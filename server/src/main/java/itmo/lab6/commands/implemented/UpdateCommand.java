package itmo.lab6.commands.implemented;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.commands.Action;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import static itmo.lab6.server.UdpServer.collection;

public class UpdateCommand implements Action {
    private final Movie movie;

    public UpdateCommand(Movie movie) {
        this.movie = movie;
    }

    @Override
    public Response run() {
        if (!collection.isKeyPresented(movie.getId())) return new Response("Collection does not contain such a key", ResponseType.SUCCESS);
        collection.update(movie);

        return new Response("Update was completed successfully", ResponseType.SUCCESS);
    }
}
