package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the show command. Prints the collection elements.
 * @see Interpreter#show()
 * @see src.baseclasses.Movie#toString()
 */
public class ShowCommand extends AbstractCommand implements Command {
    public ShowCommand(Reader reader) {
        super(reader.collection);
    }

    @Override
    public void execute() {
        interpreter.show();
    }
}
