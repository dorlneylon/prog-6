package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the remove_by_rate command. Removes all elements with the given rating.
 * @see Interpreter#rmByMpaa(String)
 */
public class RemoveByRateCommand extends AbstractCommand implements Command {
    private Reader reader;

    public RemoveByRateCommand(Reader reader) {
        super(reader.collection);
        this.reader = reader;
    }

    @Override
    public void execute() {
        try {
            String rating = reader.command.split(" ")[1];
            interpreter.rmByMpaa(rating);
        } catch (Exception e) {
            System.err.println("Incorrect input format");
        }
    }
}
