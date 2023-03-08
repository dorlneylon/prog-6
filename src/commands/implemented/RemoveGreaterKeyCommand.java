package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

/**
 * Class for the remove_greater_key command. Removes all elements with a key greater than the given one.
 * @see Interpreter#removeGreater(String)
 */
public class RemoveGreaterKeyCommand extends AbstractCommand implements Command {
    private String arg;
    public Reader reader;

    public RemoveGreaterKeyCommand(Reader reader) {
        super(reader.collection);
        this.reader = reader;
    }

    @Override
    public void execute() {
        try {
            this.arg = reader.command.split(" ")[1];
        } catch (Exception e) {
            System.err.print("Incorrect input.\n" + blcr + "$ " + whcr);
            return;
        }

        try {
            interpreter.removeGreater(arg);
        } catch (Exception e) {
            System.err.print("Incorrect input format\n" + blcr + "$ " + whcr);
        }
    }
}
