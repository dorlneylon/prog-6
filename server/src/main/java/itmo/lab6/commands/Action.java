package itmo.lab6.commands;

import itmo.lab6.server.respones.Response;

public interface Action {
    Response run();
}
