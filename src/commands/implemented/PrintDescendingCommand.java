package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the print descending command. Prints the collection in descending order.
 * @see Interpreter#printDescending()
 * @see src.moviecollection.MovieCol#printDescending()
 */
public class PrintDescendingCommand extends AbstractCommand implements Command {

    public PrintDescendingCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.printDescending();
    }
}
