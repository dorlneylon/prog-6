package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.respones.Response;
import itmo.lab6.server.respones.ResponseType;

public class ExecuteScriptCommand implements Action {
    @Override
    public Response run() {

        return new Response("Executing ", ResponseType.SUCCESS);
    }
}
