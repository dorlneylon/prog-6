package src.commands;

import src.auxiliary.Command;
import src.auxiliary.Convertible;
import src.baseclasses.Coordinates;
import src.baseclasses.Location;
import src.baseclasses.Movie;
import src.baseclasses.Person;
import src.baseenums.Color;
import src.baseenums.MovieGenre;
import src.baseenums.MpaaRating;
import src.commands.implemented.*;
import src.filemanager.XMLParser;
import src.moviecollection.MovieCol;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for the reader. Reads the commands from the console and executes them.
 *
 * @see src.commands.implemented.AbstractCommand
 * @see Interpreter
 * @see MovieCol
 * @see XMLParser
 */
public class Reader {
    protected final String filename;
    protected String scrFile;
    public final MovieCol collection;
    public String command;
    public final Interpreter interpreter;
    private final XMLParser parser;
    public HashSet<String> Executing = new HashSet<>();
    public HashMap<String, Command> commands = new HashMap<>();

    /**
     * Reset the ANSI color.
     */
    public static final String whcr = "\u001B[0m";

    /**
     * The ANSI color for blue.
     */
    public static final String blcr = "\u001B[34m";


    /**
     * Constructor for the Reader class. Initializes the {@link Interpreter} and the {@link XMLParser}.
     * Takes the filename with the XML to be parsed at the start of the program from the environment variable "save_filename".
     *
     * @param moviecollection
     */
    public Reader(MovieCol moviecollection) {
        this.collection = moviecollection;
        this.interpreter = new Interpreter(moviecollection);
        this.filename = System.getenv("save_filename");
        parser = new XMLParser(this.filename, moviecollection);
    }

    /**
     * Method for reading the commands from the console.
     * Reads and transforms user input. Delegates the commands execution to the {@link Interpreter}.
     * Uses {@link Command} interface to execute the commands ("Command" pattern they say...)
     *
     * @see AbstractCommand (and other classes that implement it)
     * @see src.commands.Reader#selector(Scanner)
     */
    public void selector(Scanner sc) {
        command = sc.nextLine();
        Command cmd = commands.getOrDefault(command.split(" ")[0], null);
        if (cmd != null) {
            cmd.execute();
            ((AbstractCommand) cmd).updateLogs();
        } else {
            System.out.println(
                    "Unknown command. Type " + blcr + "'help'" + whcr + " to see the list of available commands."
            );
        }
    }

    /**
     * At the start of the program parses the XML and fills the collection.
     * After that it starts reading commands from the interactive input.
     *
     * @throws Exception if the file is not found or the input format is incorrect.
     */
    public void startReading() {
        Scanner sc = new Scanner(System.in);
        try {
            readXML(sc);
            initCommands(sc);
            readCommand(sc);
        } catch (NoSuchElementException e) {
            System.err.print("Ctrl-D detected, exiting the program...\n");
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads commands from the console input.
     *
     * @throws Exception if the input format is incorrect.
     */
    public void readCommand(Scanner sc) {
        while (true) {
            System.out.print(blcr + "$ " + whcr);
            selector(sc);
        }
    }

    /**
     * Reads commands from the file.
     *
     * @param s - the name of the file.
     * @throws Exception if the file is not found or the input format is incorrect.
     */
    public void readCommand(String s) {
        HashMap<String, Command> oldCommands = new HashMap<>(commands);
        try {
            Scanner sc = new Scanner(new File(s));
            initCommands(sc);
            while (sc.hasNextLine()) {
                selector(sc);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.err.println("Incorrect input format");
        }
        commands = oldCommands;
    }

    /**
     * Reads the XML file and fills the collection with its contents.
     *
     * @see XMLParser
     */
    private void readXML(Scanner sc) {
        if (new File(".log").exists()) {
            try {
                System.out.print("Do you want to restore the collection from the last session? (y/n)\n" + blcr + "$ " + whcr);
                String s = sc.nextLine();
                if (s.equals("y")) {
                    MovieCol m = parser.parseData(".log");
                    interpreter.setMovieCollection(m);
                } else {
                    try {
                        MovieCol m = parser.parseData();
                        if (m.isEmpty()) System.out.print("File is empty or doesn't exist.\n" + blcr + "$ " + whcr);
                        interpreter.setMovieCollection(m);
                    } catch (NullPointerException e) {
                        System.err.print("File not found or not enough rights!\n" + blcr + "$ " + whcr);
                    } catch (Exception e) {
                        System.err.print("XML error occured.\n" + blcr + "$ " + whcr);
                    }
                }
            } catch (Exception e) {
                System.err.println("XML error occured.");
            }
        } else {
            try {
                MovieCol m = parser.parseData();
                if (m.isEmpty()) System.out.print("File is empty or doesn't exist.\n" + blcr + "$ " + whcr);
                interpreter.setMovieCollection(m);
            } catch (NullPointerException e) {
                System.err.println("File not found or not enough rights!");
            } catch (Exception e) {
                System.err.println("XML error occured.");
            }
        }
    }

    /**
     * Takes user input description of the {@link Movie} and checks it for correctness.
     *
     * @param sc - the scanner to read the input.
     * @return the {@link Movie} object.
     * @throws Exception if the input format is incorrect.
     */
    public Movie check(Scanner sc) {
        String name = checkName(sc);

        Coordinates coordinates = checkCoordinates(sc);

        long oscarsCount = checkOscarsCount(sc);

        MovieGenre genre = checkGenre(sc);

        MpaaRating mpaaRating = checkMpaaRating(sc);

        Person director = checkDirector(sc);

        try {
            return new Movie(name, coordinates, oscarsCount, genre, mpaaRating, director);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String checkName(Scanner sc) {
        while (true) {
            System.out.print("Enter name\n" + blcr + "$ " + whcr);
            String name = sc.nextLine();

            if (!name.equals("")) return name;

            System.err.print("Incorrect input format\n");
        }
    }

    private Coordinates checkCoordinates(Scanner sc) {
        while (true) {
            System.out.print("Enter coordinates (float x, int y, separated by space and dots, provided in one line)\n" + blcr + "$ " + whcr);
            String[] cs = sc.nextLine().split(" ");

            try {
                float x = Float.parseFloat(cs[0]);
                int y = Integer.parseInt(cs[1]);
                return new Coordinates(x, y);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.print("Incorrect input format\n");
            }
        }
    }

    private long checkOscarsCount(Scanner sc) {
        while (true) {
            System.out.print("Enter oscarsCount\n" + blcr + "$ " + whcr);

            try {
                long oscarsCount = sc.nextLong();
                sc.nextLine(); // consume the rest of the line
                return oscarsCount;
            } catch (InputMismatchException e) {
                System.err.print("Incorrect input format\n");
                sc.nextLine(); // consume the rest of the line
            }
        }
    }

    private MovieGenre checkGenre(Scanner sc) {
        while (true) {
            System.out.print("Enter genre (ACTION, COMEDY, TRAGEDY, THRILLER)\n" + blcr + "$ " + whcr);
            String ge = sc.nextLine();
            MovieGenre genre = Convertible.convert(ge, MovieGenre.class);

            if (genre != null) return genre;

            System.err.print("Incorrect input format\n");
        }
    }

    private MpaaRating checkMpaaRating(Scanner sc) {
        while (true) {
            System.out.print("Enter mpaaRating (PG, PG_13, R, NC_17)\n" + blcr + "$ " + whcr);
            MpaaRating mpaaRating = Convertible.convert(sc.nextLine(), MpaaRating.class);

            if (mpaaRating != null) return mpaaRating;

            System.err.print("Incorrect input format\n");
        }
    }

    private Person checkDirector(Scanner sc) {
        System.out.print("Enter director's name\n" + blcr + "$ " + whcr);
        String name = sc.nextLine();

        if (name.equals("")) {
            System.err.print("Director's name cannot be empty.\n" + blcr + "$ " + whcr);
            return checkDirector(sc);
        }

        java.util.Date bday = checkBirthday(sc);

        int height = checkIntInput(sc, "Enter director's height\n" + blcr + "$ " + whcr, "Director's height must be a positive integer.");
        if (height <= 0) {
            System.err.print("Director's height must be a positive integer.\n" + blcr + "$ " + whcr);
            return checkDirector(sc);
        }

        Location location = checkLocation(sc);

        Color hairColor = checkHairColor(sc);

        try {
            return new Person(name, bday, height, hairColor, location);
        } catch (IllegalArgumentException e) {
            return checkDirector(sc);
        }
    }

    private java.util.Date checkBirthday(Scanner sc) {
        System.out.print("Enter director's birthday (dd.MM.yyyy)\n" + blcr + "$ " + whcr);

        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(sc.nextLine());
        } catch (ParseException e) {
            System.err.print("Incorrect date format. Please enter the date in the format dd.MM.yyyy.\n");
            return checkBirthday(sc);
        }
    }

    private int checkIntInput(Scanner sc, String message, String errorMessage) {
        System.out.print(message);

        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.err.println(errorMessage);
            sc.nextLine();
            return checkIntInput(sc, message, errorMessage);
        }
    }

    private Location checkLocation(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter director's location (long double double - separated by spaces and dots, provided in one line)\n" + blcr + "$ " + whcr);
        String[] loc = sc.nextLine().split(" ");
        try {
            return new Location(Long.parseLong(loc[0]), Double.parseDouble(loc[1]), Float.parseFloat(loc[2]));
        } catch (NumberFormatException e) {
            System.err.print("Incorrect location format. Please enter the location in the format long double double.\n" + blcr + "$ " + whcr);
            return checkLocation(sc);
        }
    }

    private Color checkHairColor(Scanner sc) {
        System.out.print("Enter director's haircolor (GREEN, RED, BLACK, BLUE, YELLOW)\n" + blcr + "$ " + whcr);
        String hairColorStr = sc.nextLine();
        Color hairColor = Convertible.convert(hairColorStr, Color.class);

        if (hairColor == null) {
            System.err.print("Invalid haircolor. Please enter a valid haircolor.\n");
            return checkHairColor(sc);
        }

        return hairColor;
    }

    private void initCommands(Scanner sc) {
        commands.put("exit", new ExitCommand(this, sc));
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(this));
        commands.put("show", new ShowCommand(this));
        commands.put("clear", new ClearCommand(this));
        commands.put("save", new SaveCommand(this));
        commands.put("history", new HistoryCommand(this));
        commands.put("print_ascending", new PrintAscendingCommand(this));
        commands.put("print_descending", new PrintDescendingCommand(this));
        commands.put("insert", new InsertCommand(this, sc));
        commands.put("update", new UpdateCommand(this, sc));
        commands.put("remove_key", new RemoveKeyCommand(this));
        commands.put("remove_greater", new RemoveGreaterKeyCommand(this));
        commands.put("replace_if_lower", new ReplaceIfLowerCommand(this, sc));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("remove_all_by_mpaa_rating", new RemoveByRateCommand(this));
    }

    public void setScrFile(String scrFile) {
        this.scrFile = scrFile;
    }
}
