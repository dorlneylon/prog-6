package src.commands.implemented;

import src.auxiliary.Command;
import src.baseclasses.Movie;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

import java.util.Scanner;

/**
 * Class for the update command. Updates the element with the given key.
 * @see Interpreter#insert(Long, Movie)
 * @see src.moviecollection.MHMap#insert(Object, Object)
 * @see Movie
 */
public class UpdateCommand extends AbstractCommand implements Command {
    private Movie arg;
    private Scanner sc;
    private Reader reader;

    public UpdateCommand(Reader reader, Scanner sc) {
        super(reader.collection);
        this.reader = reader;
        this.sc = sc;
    }

    @Override
    public void execute() {
        Long k = null;
        try {
            k = Long.parseLong(reader.command.split(" ")[1]);
        } catch (NumberFormatException e) {
            System.out.print("Illegal key argument. Key must be a long.");
            return;
        } catch (Exception e) {
            System.out.println("Error occured. Check logger for more info.");
            wr.logErrors(e);
            return;
        }
        if (!reader.collection.containsKey(k)) {
            System.err.print("Key doesn't exist. Try calling the function again.\n");
            return;
        }
        else {
            this.arg = reader.check(sc);
            try {
                this.arg.setId(k);
            } catch (NumberFormatException e) {
                System.out.println("Illegal key argument. Key must be a long.");
                return;
            } catch (Exception e) {
                System.out.println("Error occured. Check logger for more info.");
                wr.logErrors(e);
                return;
            }
        }

        try {
            interpreter.insert(arg.getId(), arg);
        } catch (Exception e) {
            System.err.println("Error occured. Try again.");
        }
    }
}
