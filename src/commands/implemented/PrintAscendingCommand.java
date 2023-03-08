package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the print_ascending command. Prints the collection in ascending order.
 * @see Interpreter#printAscending()
 * @see MovieCol#printAscending()
 */
public class PrintAscendingCommand extends AbstractCommand implements Command {

    public PrintAscendingCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.printAscending();
    }
}
