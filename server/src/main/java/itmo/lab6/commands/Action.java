package itmo.lab6.commands;

import itmo.lab6.server.response.Response;

public interface Action {
    Response run();
}
