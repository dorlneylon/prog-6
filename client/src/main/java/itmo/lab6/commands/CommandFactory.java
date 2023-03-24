package itmo.lab6.commands;

import itmo.lab6.basic.auxiliary.Convertible;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseenums.MpaaRating;
import itmo.lab6.basic.utils.files.FileUtils;
import itmo.lab6.basic.utils.files.ScriptExecutor;
import itmo.lab6.basic.utils.parser.Parser;
import itmo.lab6.connection.Connector;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static itmo.lab6.commands.CollectionValidator.isMovieValid;

/**
 * This class is used to create new instances of {@link Command}
 */
public final class CommandFactory {
    /**
     * Returns new command instance {@link Command}
     *
     * @param type type of the command
     * @param args arguments to the command
     * @return command instance (can be null)
     */
    public static Command createCommand(CommandType type, String[] args) {
        return switch (type) {
            case EXIT -> {
                System.out.println("Shutting down...");
                System.exit(0);
                yield null;
            }
            case EXECUTE_SCRIPT -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + CommandType.EXECUTE_SCRIPT);
                    yield new Command(CommandType.SERVICE, "");
                }
                String filePath = args[0];
                if (!FileUtils.isFileExist(filePath)) {
                    System.err.println("Script file does not exist: " + filePath);
                    yield new Command(CommandType.SERVICE, "");
                }
                ArrayList<Command> commands = new ScriptExecutor(new File(filePath)).readScript().getCommandList();
                yield new Command(type, commands);
            }
            case HELP, PRINT_ASCENDING, PRINT_DESCENDING, INFO, SHOW, CLEAR -> new Command(type);
            case HISTORY -> {
                try {
                    yield new Command(type, InetAddress.getLoopbackAddress(), Connector.getPort());
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    yield null;
                }
            }
            case REMOVE_GREATER, REMOVE_KEY -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + type.name());
                    yield new Command(CommandType.SERVICE, "");
                }
                try {
                    yield new Command(type, Long.parseLong(args[0]));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid argument for command " + type.name());
                    yield new Command(CommandType.SERVICE, "");
                }
            }
            case REMOVE_BY_MPAA_RATING -> {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + type.name());
                    yield new Command(CommandType.SERVICE, "");
                }
                try {
                    yield new Command(type, Convertible.convert(args[0], MpaaRating.class));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid argument for command " + type.name());
                    yield new Command(CommandType.SERVICE, "");
                }
            }
            case INSERT, UPDATE, REPLACE_IF_LOWER -> {
                Movie movie = null;
                if (args.length == 1) {
                    movie = parseMovie(type, args);
                } else if (args.length >= 2) {
                    movie = parseMovie(new String[]{args[0]}, Arrays.copyOfRange(args, 1, args.length));
                }
                // TODO: скорее всего, проверка не нужна.
                if (movie != null) yield new Command(type, movie);
                yield new Command(CommandType.SERVICE, "");
            }
            // DEFAULT command
            default -> null;
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

        Movie movie = Parser.readObject(Movie.class);
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
    public static Movie parseMovie(String[] args, String[] movieArgs) {
        Movie movie = Parser.readObject(Movie.class, movieArgs);
        Objects.requireNonNull(movie).setId(Long.parseLong(args[0]));
        return movie;
    }
}
