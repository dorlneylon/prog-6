package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the clear command. Clears the collection.
 * @see Interpreter#clear()
 */
public class ClearCommand extends AbstractCommand implements Command {
    public ClearCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.clear();
    }
}
