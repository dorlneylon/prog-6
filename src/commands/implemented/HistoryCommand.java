package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the help command. Prints help for available commands.
 * @see Interpreter#help()
 */
public class HistoryCommand extends AbstractCommand implements Command {
    public HistoryCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.history();
    }
}
