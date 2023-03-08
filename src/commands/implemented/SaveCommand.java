package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

import static src.filemanager.writer.Writer.logErrors;

/**
 * Class for the save command. Saves the collection to a file.
 * @see Interpreter#save(String)
 */
public class SaveCommand extends AbstractCommand implements Command {
    private String filename;
    private Reader reader;

    public SaveCommand(Reader reader) {
        super(reader.collection);
        try { this.filename = reader.command.split(" ")[1]; }
        catch (Exception e) { this.reader = reader; this.filename = System.getenv("save_filename"); }
    }

    @Override
    public void execute() {
        if (reader.command.equals("save") || reader.command.equals("save ")) {
            this.filename = System.getenv("save_filename");
        } else {
            this.filename = reader.command.split(" ")[1];
        }

        try {
            interpreter.save(filename);
            wr.writeData(filename);
        } catch (Exception e) {
            System.out.println("Error occured. Check logger for more info.");
            logErrors(e);
        }
    }
}
