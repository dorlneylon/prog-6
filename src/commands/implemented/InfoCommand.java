package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the help command. Prints help for available commands.
 * @see Interpreter#help()
 */
public class InfoCommand extends AbstractCommand implements Command {
    public InfoCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.info();
    }
}
