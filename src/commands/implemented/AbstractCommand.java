package src.commands.implemented;

import src.commands.Interpreter;
import src.filemanager.writer.Writer;
import src.moviecollection.MovieCol;

/** Abstract class for all commands. Serves the command pattern idea. */
public abstract class AbstractCommand {
    public static final String whcr = "\u001B[0m";
    public static final String blcr = "\u001B[34m";
    public static final String prcr = "\u001B[35m";
    protected final Interpreter interpreter;
    protected final Writer wr;

    /**
     * Constructor for AbstractCommand.
     * @param collection - movie collection
     * @see MovieCol
     * @see Writer
     * */
    AbstractCommand(MovieCol collection) {
        interpreter = new Interpreter(collection);
        wr = new Writer(collection);
    }

    public void updateLogs() {
        wr.writeData(".log");
    }
}
