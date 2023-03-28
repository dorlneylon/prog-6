package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Convertible;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseenums.MpaaRating;
import itmo.lab6.basic.utils.files.FileUtils;
import itmo.lab6.basic.utils.files.ScriptExecutor;
import itmo.lab6.basic.utils.parser.ArgumentParser;
import itmo.lab6.basic.utils.parser.UserInputParser;
import itmo.lab6.basic.utils.parser.exceptions.ObjectParsingException;
import itmo.lab6.basic.utils.terminal.Colors;
import itmo.lab6.connection.Connector;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import static itmo.lab6.commands.CollectionValidator.isMovieValid;

/**
 * This class is used to create new instances of {@link Command}
 */
public final class CommandFactory {
    private static HashSet<CommandType> commandsHistory = new HashSet<>();

    /**
     * Returns new command instance {@link Command}
     *
     * @param type type of the command
     * @param args arguments to the command
     * @return command instance (can be null)
     */
    public static Command createCommand(CommandType type, String[] args) {
        if (!type.equals(CommandType.DEFAULT)) commandsHistory.add(type);
        return switch (type) {
            case EXIT -> {
                System.out.println("Shutting down...");
                System.exit(0);
                yield null;
            }
            case EXECUTE_SCRIPT -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + CommandType.EXECUTE_SCRIPT);
                    yield null;
                }
                String filePath = args[0];
                if (!FileUtils.isFileExist(filePath)) {
                    System.err.println("Script file does not exist: " + filePath);
                    yield null;
                }
                ArrayList<Command> commands = new ScriptExecutor(new File(filePath)).readScript().getCommandList();
                yield new Command(type, commands);
            }
            case HELP, PRINT_ASCENDING, PRINT_DESCENDING, INFO, SHOW, CLEAR -> new Command(type);
            case HISTORY -> {
                System.out.println(commandsHistory.stream().map(CommandType::name).reduce((s1, s2) -> s1 + "\n" + s2).orElse("No commands were executed."));
                yield null;
            }
//                try {
//                    yield new Command(type, InetAddress.getLoopbackAddress(), Connector.getPort());
//                } catch (Exception e) {
//                    System.err.println("Error: " + e.getMessage());
//                    yield null;
//                }
            case REMOVE_GREATER, REMOVE_KEY -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + type.name());
                    yield null;
                }
                try {
                    yield new Command(type, Long.parseLong(args[0]));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid argument for command " + type.name());
                    yield null;
                }
            }
            case REMOVE_ALL_BY_MPAA_RATING -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + type.name());
                    yield null;
                }
                try {
                    MpaaRating rating = Convertible.convert(args[0], MpaaRating.class);
                    if (rating == null) {
                        System.err.println("Invalid MPAA rating. List of available MPAA ratings: " + Arrays.toString(MpaaRating.class.getEnumConstants()));
                        yield null;
                    }
                    yield new Command(type, rating);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid argument for command " + type.name());
                    yield null;
                }
            }
            case INSERT, UPDATE, REPLACE_IF_LOWER -> {
                Movie movie = null;
                if (args.length == 1) {
                    movie = parseMovie(type, args);
                } else if (args.length >= 2) {
                    movie = parseMovie(type, new String[]{args[0]}, Arrays.copyOfRange(args, 1, args.length));
                }
                if (movie != null) yield new Command(type, movie);
                yield null;
            }
            // DEFAULT command
            default -> {
                System.err.println("Unknown command.");
                yield null;
            }
        };
    }

    /**
     * Parses movie from console
     *
     * @param type command type
     * @param args command arguments
     * @return read movie from console
     */
    public static Movie parseMovie(CommandType type, String[] args) {
        if (Boolean.FALSE.equals(isMovieValid(type, args))) return null;

        Movie movie = new UserInputParser().readObject(Movie.class);
        Objects.requireNonNull(movie).setId(Long.parseLong(args[0]));
        return movie;
    }

    /**
     * Parses movie from file
     *
     * @param args      command args
     * @param movieArgs movie args
     * @return read movie from file
     */
    public static Movie parseMovie(CommandType type, String[] args, String[] movieArgs) {
        // if (Boolean.FALSE.equals(isMovieValid(type, args))) return null;
        Movie movie = null;
        try {
            movie = new ArgumentParser(movieArgs).readObject(Movie.class);
            Objects.requireNonNull(movie).setId(Long.parseLong(args[0]));
        } catch (ObjectParsingException e) {
            System.err.println("Error parsing: " + e.getMessage());
        }
        return movie;
    }
}
