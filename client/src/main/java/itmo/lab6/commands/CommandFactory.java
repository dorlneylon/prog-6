package itmo.lab6.commands;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.utils.files.FileUtils;
import itmo.lab6.basic.utils.files.ScriptExecutor;
import itmo.lab6.basic.utils.parser.Parser;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static itmo.lab6.commands.CollectionValidator.checkIfExists;
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
            case HELP, PRINT_ASCENDING, PRINT_DESCENDING, HISTORY, INFO, SHOW, CLEAR -> new Command(type);
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
            case INSERT, UPDATE, REPLACE_IF_LOWER -> {
                Movie movie;
                if (args.length == 1) {
                    movie = parseMovie(type, args);
                }
                else {
                    movie = parseMovie(type, new String[]{args[0]}, Arrays.copyOfRange(args, 1, args.length));
                }
                // TODO: скорее всего, проверка не нужна.
                if (movie != null) yield new Command(type, movie);
                yield null;
            }
            // DEFAULT command
            default -> null;
        };
    }

    public static Movie parseMovie(CommandType type, String[] args) {
        if (Boolean.FALSE.equals(isMovieValid(type, args))) return null;

        Movie movie = Parser.readObject(Movie.class);
        Objects.requireNonNull(movie).setId(Long.parseLong(args[0]));
        return movie;
    }

    public static Movie parseMovie(CommandType type, String[] args, String[] movieArgs) {
        if (Boolean.FALSE.equals(isMovieValid(type, args))) return null;

        Movie movie = Parser.readObject(Movie.class, movieArgs);
        Objects.requireNonNull(movie).setId(Long.parseLong(args[0]));
        return movie;
    }
}
