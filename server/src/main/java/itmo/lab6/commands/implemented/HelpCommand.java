package itmo.lab6.commands.implemented;

import itmo.lab6.commands.Action;
import itmo.lab6.commands.CommandType;
import itmo.lab6.server.response.MessagePainter;
import itmo.lab6.server.response.Response;
import itmo.lab6.server.response.ResponseType;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class HelpCommand implements Action {
    @Override
    public Response run() {
        return new Response(MessagePainter.ColoredInfoMessage(Arrays.stream(CommandType.values()).
                map(CommandType::getDescription).
                filter(description -> !description.isEmpty()).
                collect(Collectors.joining("\n"))), ResponseType.INFO);
    }
}
