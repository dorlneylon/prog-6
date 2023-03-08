package itmo.lab6.basic.auxiliary;

/**
 * The `Command` interface is used to implement the command pattern.
 * It has a single method `execute()` that is used to execute the command.
 * @author somebody once told me the world is gonna roll me.
 */
public interface Command {
    void execute() throws Exception;
}
