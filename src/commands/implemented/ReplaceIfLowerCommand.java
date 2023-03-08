package src.commands.implemented;

import src.auxiliary.Command;
import src.baseclasses.Movie;
import src.commands.Interpreter;
import src.commands.Reader;
import src.moviecollection.MovieCol;

import java.util.Scanner;

/**
 * Class for the replace_if_lower command. Replaces the element with the given key if the new element is lower.
 * @see Interpreter#rplcLower(Long, Movie)
 * @see MovieCol#rplcLower(Long, Movie)
 * @see Movie
 */
public class ReplaceIfLowerCommand extends AbstractCommand implements Command {
    private Long key;
    private Movie arg;
    private Scanner sc;
    private Reader reader;

    public ReplaceIfLowerCommand(Reader reader, Scanner sc) {
        super(reader.collection);
        this.reader = reader;
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            this.key = Long.parseLong(reader.command.split(" ")[1]);
        } catch (NumberFormatException e) {
            System.out.println("Illegal key argument. Key must be a long.");
            return;
        } catch (Exception e) {
            System.err.println("Error occured. Try again.");
            return;
        }

        try {
            this.arg = reader.check(sc);
            this.arg.setId(this.key);
        } catch (Exception e) {
            System.err.print("Incorrect input format\n" + blcr + "$ " + whcr);
            return;
        }

        interpreter.rplcLower(key, arg);
    }
}
