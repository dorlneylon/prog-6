package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Class for the execute_script command. Executes a script from a file.
 * @see Interpreter#executeScript()
 * @see src.commands.Reader#readCommand(String)
 */
public class ExecuteScriptCommand extends AbstractCommand implements Command {
    private String filename;
    private final Reader reader;

    public ExecuteScriptCommand(Reader reader) {
        super(reader.collection);
        this.reader = reader;
    }

    @Override
    public void execute() {
        try {
            this.filename = reader.command.split(" ")[1];
        } catch (Exception e) {
            System.err.println("Incorrect input format");
            return;
        }

        String a = FileSystems.getDefault().getPath((this.filename != null) ? this.filename : "").normalize().toAbsolutePath().toString();
        if (reader.Executing.contains(a)) {
            System.err.print("You can't execute the same script recursively!\n" + blcr + "$ " + whcr);
            return;
        }
        reader.setScrFile(this.filename);

        try {
            reader.Executing.add(Paths.get(filename).normalize().toAbsolutePath().toString());
            interpreter.executeScript();
            reader.readCommand(filename);
            reader.Executing.remove(Paths.get(filename).normalize().toAbsolutePath().toString());
        } catch (Exception e) {
            System.err.println("Incorrect input format");
        }
    }
}
