package itmo.lab6.commands.implemented;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.commands.Action;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static itmo.lab6.server.UdpServer.collection;

public class ShowCommand implements Action {

    @Override
    public Response run() {
        return new Response(Arrays.stream(collection.values()).map(Movie::info).collect(Collectors.joining("\n")), ResponseType.INFO);
    }
}
