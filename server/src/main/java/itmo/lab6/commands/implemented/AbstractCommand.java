package itmo.lab6.commands.implemented;

import itmo.lab6.basic.auxiliary.Command;

import java.io.IOException;

public abstract class AbstractCommand implements Command {

    public AbstractCommand() {}

    public abstract void execute() throws IOException;
}
