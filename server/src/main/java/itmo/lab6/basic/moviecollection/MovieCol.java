package itmo.lab6.basic.moviecollection;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.MpaaRating;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The MovieCol class is a collection based on HashMap that extends the MHMap class.
 * This class contains instance variables used to store information about movies.
 *
 * @author dorlneylon
 * @version 1.0?
 * @since 03.02.2023
 * @see MHMap
 * @see Movie
 */
public class MovieCol extends MHMap<Long, Movie> {
	/**
	 * Instantiates a new MovieCol object.
	 */
	public MovieCol() {}

	/**
	 * prints the collection elements and its contents to the console
	 * @see Movie
	 * @see Person
	 */
	public String show() {
		return Arrays.stream(this.values()).map(Movie::toString).collect(Collectors.joining("\n"));
	}

	/**
	 * removes all elements with higher oscarsCount than given key from the collection.
	 *
	 * @param key the key to compare with.
	 */
	public void rmGreater(Long key) {
		for (Movie movie : this.values())
			if (key.compareTo(movie.oscarsCount()) < 0)
				this.rmByVal(movie);
	}

	public boolean equals(MovieCol map) {
		Movie[] a = this.values();
		Movie[] b = map.values();
		if (a.length != b.length) return false;
		for (int i = 0; i < a.length; i++) if (!a[i].equals(b[i])) return false;
		return true;
	}

	/**
	 * sorts the elements of the collection by the number of Oscars received and prints them in a new order.
	 * @see Movie
	 */
	@Override
	public void printAscending() {
		Movie[] vals = sorted(false);	
		for (Movie movie : vals)
			System.out.println(movie.toString());
	}

	/**
	 * sorts the elements of the collection by the number of Oscars received in a reversed order and prints them.
	 * @see Movie
	 */
	@Override
	public void printDescending() {
		Movie[] vals = sorted(true);
		for (Movie movie : vals)
			System.out.println(movie.toString());
	}

	/**
	 * get the id of the element in the collection
	 * @param movie the element to get the id of
	 * @return the id of the element or -1 if the element is not in the collection
	 * @see Movie
     */
	@Override
	public Long getKey(Movie movie) {
		return movie.getId();
	}

	/**
	 * get the elements of the collection
	 * @return Movie[]
	 * @see Movie
	 */
	@Override
	public Movie[] values() {
		return this.getMap().values().toArray(new Movie[this.size()]);
	}

	/**
	 * swap the elements if the provided elements has less Oscars than the element in the collection
	 * @param key the key of the element in the collection
	 * @param movie the element to compare with
	 * @return true if the elements are swapped, false otherwise
	 */
	public boolean rplcLower(Long key, Movie movie) {
		if (movie.oscarsCount() < this.get(key).oscarsCount()) { this.update(key, movie); return true; }
		return false;
	}

	/**
	 * check if the collection contains such an element.
	 * @param movie the element to check
	 * @return true if the element is in the collection, false otherwise
	 * @see Movie
	 */
	@Override
	public boolean contains(Movie movie) {
		return this.getMap().containsValue(movie);
	}

	/**
	 * check if the collection contains such a key.
	 * @param key the key to check
	 * @return true if the key is in the collection, false otherwise
	 */
	public boolean containsKey(Long key) {
		return this.getMap().containsKey(key);
	}

	@Override
	public Movie[] sorted(boolean reverse) {
		Movie[] vals = this.values();
        Arrays.sort(vals, (reverse) ? (o1, o2) -> o2.compareTo(o1) : (o1, o2) -> o1.compareTo(o2));
		return vals;	
	} 

	/**
	 * remove the elements with the specified rating from the collection.
	 * @param rating the rating to check
	 * @return true if the operation was successful, false otherwise
	 * @see MpaaRating
	 */
	public boolean rmByRate(MpaaRating rating) {
		Arrays.stream(this.values()).filter(movie -> movie.getRating() == rating).forEach(this::rmByVal);
		return true;
	}

}
