package src.commands.implemented;

import src.auxiliary.Command;
import src.commands.Reader;
import src.filemanager.XMLParser;
import src.moviecollection.MovieCol;

import java.io.File;
import java.util.Scanner;

/**
 * Class for the exit command. Exits the program.
 */
public class ExitCommand extends AbstractCommand implements Command {
    private final Scanner sc;
    private final MovieCol collection;
    private MovieCol logger = new MovieCol();
    private final XMLParser parser;

    public ExitCommand(Reader reader, Scanner sc) {
        super(reader.collection);
        this.collection = reader.collection;
        this.parser = new XMLParser(System.getenv("save_filename"), logger);
        this.sc = sc;
    }

    @Override
    public void execute()  {
        try {
            logger = parser.parseData();
        } catch (Exception e) {}
        if (!logger.equals(collection)) {
            System.out.print("Do you want to save the collection before exiting? (y/n)\n" + blcr + "$ " + whcr);
            String answer = sc.nextLine();
            if (answer.equals("y")) {
                System.out.print("Where to?\n" + blcr + "$ " + whcr);
                String fn = sc.nextLine();
                wr.writeData(fn);
            }
        }
        new File(".log").delete();
        System.exit(0);
    }
}
