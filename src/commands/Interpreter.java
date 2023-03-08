package src.commands;

import src.auxiliary.Command;
import src.auxiliary.Convertible;
import src.baseclasses.Movie;
import src.baseenums.MpaaRating;
import src.moviecollection.MovieCol;

import java.util.HashMap;

/**
 * Interpreter class is a command-line interface to interact with the movie collection.
 * It provides various methods to perform operations on the movie collection,
 * such as adding, updating, removing movies, printing the collection, etc.
 * It also keeps track of the executed commands using the {@link History} class.
 *
 * Tl;DR: Reader -> Interpreter -> MovieCol.
 *
 * @author dorlneylon
 */
public class Interpreter {
	/**
	 * A constant to represent the white color code in ANSI escape codes.
	 */
	public static final String whcr = "\u001B[0m";

	/**
	 * The ANSI color for blue.
	 */
	public static final String blcr = "\u001B[34m";

	/**
	 * A constant to represent the purple color code in ANSI escape codes.
	 */
	public static final String prcr = "\u001B[35m";

	private final MovieCol collection;

	/**
	 * Constructs an Interpreter object with the specified movie collection.
	 * @param collection The movie collection.
	 */
	public Interpreter(MovieCol collection) {
		this.collection = collection;
	}

	/**
	 * Clears all movies from the collection.
	 */
	public void clear() {
		collection.clear();
		History.updCounter("clear");
	}

	/**
	 * Saves the movie collection to a file.
	 * @param filename The name of the file to save the collection to.
	 * @throws Exception If an error occurs while saving the collection.
	 */
	public void save(String filename)  {
		History.updCounter("save");
	}

	/**
	*  Shows the movie collection.
	 */
	public void show() {
		collection.show();
		History.updCounter("show");
	}

	/**
	 * Shows the 7 last executed commands.
	 */
	public void history() {
		History.updCounter("history");
		History.latest();
	}

	/**
	 * Inserts a movie into the collection with the given key.
	 * @param key The key for the movie.
	 * @param value The movie's data.
	 */
	public void insert(String key, String value) throws IllegalArgumentException {
		if (!key.equals("null")) collection.insert(Long.parseLong(key)-1, new Movie(value));
		else collection.insert(new Movie(value));
		History.updCounter("insert");
	}

	/**
	 * Inserts a movie into the collection.
	 * @param movie The movie to insert.
	 */
	public void insert(Movie movie) {
		collection.insert(movie);
		History.updCounter("insert");
	}

	/**
	 * Updates a movie in the collection.
	 * @param id The id of the movie to update.
	 * @param movie The updated movie.
	 */
	public void insert(Long id, Movie movie) {
		collection.update(id, movie);
		History.updCounter("update");
	}

	/**
	 * Assigns a new movie collection to the Interpreter.
	 * @param collection The new movie collection.
	 */
	public void setMovieCollection(MovieCol collection) {
		this.collection.assign(collection);
	}

	/**
	 * Removes a movie from the collection based on its key.
	 * @param key The key of the movie to remove.
	 */
	public void removeKey(String key) {
		if (collection.rmByKey(Long.valueOf(key))) System.out.println("Element has been succesfully removed");
		else System.out.println("Element not in collection");
		History.updCounter("remove_key");
	}

	/**
	 * Removes movies with greater key than the given.
	 * @param key the provided key.
	 */
	public void removeGreater(String key) {
		collection.rmGreater(Long.valueOf(key));
		History.updCounter("remove_greater");
	}

	/**
	 * Swaps the values of two movies in the collection if the amount of Oscars of the first is greater than of the second's.
	 * @param key the id of the element that is being compared with the value.
	 * @param value the value that might take the id's place.
	 */
	public void rplcLower(Long key, Movie value) {
		collection.rplcLower(key, value);
		History.updCounter("replace_if_lower");
	}

	/**
	 * Updates the counter of the specified command.
	 */
	public void executeScript() {
		History.updCounter("execute_script");
	}

	/**
	 * Prints helper messages.
	 */
	public void help() {
		System.out.println(
				prcr + "help:" + whcr + " output help for available commands\n" +
				prcr + "info:" + whcr + " output to the standard output stream information about the collection (type, initialization date, number of elements, etc.)\n" +
				prcr + "show:" + whcr + " output to the standard output stream all elements of the collection in the string representation\n" +
				prcr + "insert " + blcr + "<id> {element}:" + whcr + " add a new element with the specified key\n" +
				prcr + "update " + blcr + "<id> {element}:" + whcr + " update the value of a collection element whose id is equal to the specified\n" +
				prcr + "remove_key" + blcr +" <id>:" + whcr + " delete an element from the collection by its key\n" +
				prcr + "clear:" + whcr + " clear the collection\n" +
				prcr + "save " + blcr + "<filename>:" + whcr + " save the collection to a file. USE RELATIVE PATHS.\n" +
				prcr + "execute_script" + blcr + " <file_name>:" + whcr + " read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode. USE RELATIVE PATHS.\n" +
				prcr + "exit:" + whcr + " terminate the program (without saving to a file)\n" +
				prcr + "remove_greater" + blcr + " <Oscars>:" + whcr + " remove from the collection all elements exceeding the specified\n" +
				prcr + "history:" + whcr + " output the last 7 commands (without their arguments)\n" +
				prcr + "replace_if_lower" + blcr + " <id> {element}:" + whcr + " replace the value by key if the new value is less than the old one.\n" +
				prcr + "remove_all_by_mpaa_rating" + blcr + " <mpaaRating>:" + whcr + " remove from the collection all elements whose mpaaRating field value is equivalent to the specified one\n" +
				prcr + "print_ascending:" + whcr + " print the elements of the collection in ascending order\n" +
				prcr + "print_descending:" + whcr + " print the elements of the collection in descending order"
		);
		History.updCounter("help");
	}

	/**
	 * Prints the collection in ascending order.
	 */
	public void printAscending() {
		collection.printAscending();
		History.updCounter("print_ascending");
	}

	/**
	 * Prints the collection in descending order.
	 */
	public void printDescending() {
		collection.printDescending();
		History.updCounter("print_descending");
	}

	/**
	 * Prints the info about the collection. Apparently it's just a placeholder.
	 */
	public void info() {
		collection.info();
		History.updCounter("info");
	}

	/**
	 * Removes all the elements from the collection with the specified Mpaa.
	 * @param mpaa The Mpaa rating to remove.
	 */
	public void rmByMpaa(String mpaa) {
		MpaaRating rate = Convertible.convert(mpaa, MpaaRating.class);
		collection.rmByRate(rate);
		History.updCounter("remove_all_by_mpaa_rating");
	}
}
