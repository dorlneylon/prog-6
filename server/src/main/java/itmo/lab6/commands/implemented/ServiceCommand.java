package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import static itmo.lab6.server.UdpServer.collection;

public final class ServiceCommand implements Action {
    private final String command;

    public ServiceCommand(String command) {
        this.command = command;
    }

    @Override
    public Response run() {
        return switch (command.split(" ")[0]) {
            case "check_id" -> new Response(Boolean.toString(collection.isContainsKey(Long.parseLong(command.split(" ")[1]))), ResponseType.SUCCESS);
            default -> new Response("", ResponseType.SUCCESS);
        };
    }
}
