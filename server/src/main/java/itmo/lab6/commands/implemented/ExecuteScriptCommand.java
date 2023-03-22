package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.commands.Command;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public final class ExecuteScriptCommand implements Action {

    private final ArrayList<Command> commandQueue;

    public ExecuteScriptCommand(ArrayList<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public Response run() {
        StringBuilder output = new StringBuilder();
        commandQueue.forEach(command -> output.append(command.execute().getMessage()));
        return new Response(output.toString(), ResponseType.SUCCESS);
    }
}
