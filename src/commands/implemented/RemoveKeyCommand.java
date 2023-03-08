package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the remove_key command. Removes an element from the collection by its key.
 * @see Interpreter#removeKey(String)
 */
public class RemoveKeyCommand extends AbstractCommand implements Command {
    private String arg;
    public Reader reader;

    public RemoveKeyCommand(Reader reader) {
        super(reader.collection);
        this.reader = reader;
    }

    @Override
    public void execute() {
        try {
            this.arg = reader.command.split(" ")[1];
        } catch (Exception e) {
            System.err.println("Error occured. Try again.");
            return;
        }
        interpreter.removeKey(arg);
        System.out.print("");
    }
}
