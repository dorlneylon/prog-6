package src.commands.implemented;

import src.auxiliary.Command;
import src.baseclasses.Movie;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

import java.util.Scanner;

/**
 * Class for the insert command. Inserts a new element into the collection.
 * @see Interpreter#insert(Movie)
 */
public class InsertCommand extends AbstractCommand implements Command {
    private Movie arg;
    private Scanner sc;
    private Reader reader;

    public InsertCommand(Reader reader, Scanner sc) {
        super(reader.collection);
        this.reader = reader;
        this.sc = sc;
    }

    @Override
    public void execute() {
        Long k = null;
        try {
            k = Long.parseLong(reader.command.split(" ")[1]);
            if (reader.collection.containsKey(k)) {
                System.err.print("Key already exists. Try calling the function again.\n");
                return;
            }
            else {
                this.arg = reader.check(sc);
                try {
                    this.arg.setId(k);
                } catch (NumberFormatException e) {
                    System.err.print("Illegal key argument.\n");
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.err.print("Illegal key argument.\n");
            return;
        } catch (Exception e) {
            return;
        }

        try {
            if (arg == null) throw new NullPointerException();
			interpreter.insert(arg);
            System.out.println("Done!");
		} catch (NullPointerException e) {
			System.out.println("Empty fields or illegal arguments detected.");
        } catch (Exception e) {
            System.err.println("Error occured.");
        }
    }
}
