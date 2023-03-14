package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.respones.Response;
import itmo.lab6.server.respones.ResponseType;

public class ClearCommand implements Action {
    @Override
    public Response run() {

        return new Response("Collection cleaned successfully", ResponseType.SUCCESS);
    }
}
