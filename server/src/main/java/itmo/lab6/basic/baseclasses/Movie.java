package itmo.lab6.basic.baseclasses;

import itmo.lab6.basic.baseclasses.builders.annotations.Generated;
import itmo.lab6.basic.baseenums.MovieGenre;
import itmo.lab6.basic.baseenums.MpaaRating;

import java.io.Serial;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * The `Movie` class represents a movie, which includes its name, creation date,
 * number of Oscars it won, genre, MPAA rating, and director.
 *
 * @author dorlneylon
 * @version 99999999.9999999
 * @since 2023-02-02
 */
public class Movie implements Comparable<Movie>, Serializable {
    @Serial
    @Generated
    private static final long serialVersionUID = 6529685098267757690L;

    @Generated
    private static Long nextId = 1L;

    /**
     * The unique ID of the movie.
     * Generates automatically, can't be null, greater than zero.
     */
    private Long id;

    /**
     * The name of the movie. Can't be null and an empty sequence.
     */
    private String name;

    /**
     * The coordinates of the movie. Can't be null
     *
     * @see Coordinates
     */
    private Coordinates coordinates;

    /**
     * The creation date of the movie. Generates automatically, can't be null.
     */
    private java.time.ZonedDateTime creationDate;

    /**
     * The number of Oscars won by the movie. Not negative.
     */
    private long oscarsCount;

    /**
     * The genre of the movie.
     *
     * @see MovieGenre
     */
    private MovieGenre genre;

    /**
     * The MPAA rating of the movie.
     *
     * @see MpaaRating
     */
    private MpaaRating mpaaRating;

    /**
     * The director of the movie. The field can't be null.
     *
     * @see Person
     */
    private Person director;

    /**
     * Constructor to create a movie with the given parameters.
     * Automatically sets the id and creation date for the movie.
     *
     * @param name        the name of the movie
     * @param coordinates the coordinates of the movie
     * @param oscarsCount the number of oscars won by the movie
     * @param genre       the genre of the movie
     * @param mpaaRating  the MPAA rating of the movie
     * @param director    the director of the movie
     * @see Person
     * @see Coordinates
     * @see MovieGenre
     * @see MpaaRating
     */
    public Movie(String name, Coordinates coordinates, Long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director) {
        if (name == null || name.isEmpty() || coordinates == null || oscarsCount == null || oscarsCount < 0 || genre == null || mpaaRating == null || director == null)
            throw new IllegalArgumentException("The fields can't be null or empty sequences.");
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.creationDate = java.time.ZonedDateTime.now();
    }

    /**
     * Constructs a `Movie` instance with the specified ID, name, coordinates, creation date,
     * number of Oscars, genre, MPAA rating, and director.
     *
     * @param id           the unique ID of the movie
     * @param name         the name of the movie
     * @param coordinates  the coordinates of the movie
     * @param creationDate the creation date of the movie
     * @param oscarsCount  the number of Oscars won by the movie
     * @param genre        the genre of the movie
     * @param mpaaRating   the MPAA rating of the movie
     * @param director     the director of the movie
     * @see Movie#Movie(String name, Coordinates coordinates, Long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director)
     */
    public Movie(Long id, String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, Long oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director) {
        if (name == null || name.isEmpty() || coordinates == null || oscarsCount == null || oscarsCount < 0 || genre == null || mpaaRating == null || director == null)
            throw new IllegalArgumentException("The fields can't be null or empty sequences.");
        nextId++;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }


    /**
     * @return the amount of Oscars won by the movie
     * @see Movie#oscarsCount
     */
    public long oscarsCount() {
        return oscarsCount;
    }

    /**
     * @return the unique ID of the movie
     * @see Movie#id
     * @see Movie#nextId
     */
    public Long getId() {
        return id;
    }

    /**
     * Used to set the ID of the movie.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name of the movie
     * @see Movie#name
     */
    public String getName() {
        return name;
    }

    /**
     * Used to set the name of the movie.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the coordinates of the movie
     * @see Movie#coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Used to set the coordinates of the movie.
     *
     * @see Coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the creation date of the movie
     * @see Movie#creationDate
     */
    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Used to set the creation date of the movie.
     */
    public void setCreationDate(java.time.ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the genre of the movie
     * @see Movie#genre
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * Used to set the genre of the movie.
     *
     * @see Movie#genre
     * @see MovieGenre
     */
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    /**
     * @return the director of the movie
     * @see Movie#director
     * @see Person
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Used to set the director of the movie.
     *
     * @see Movie#director
     * @see Person
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * @return the amount of Oscars won by the movie.
     * @see Movie#oscarsCount()
     * @see MpaaRating
     * @deprecated use {@link Movie#getOscarsInt()} or {@link Movie#oscarsCount()} instead
     */
    @Deprecated
    public long getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Used to set the amount of Oscars won by the movie.
     *
     * @param oscarsCount
     * @see Movie#oscarsCount
     */
    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    /**
     * @return the amount of Oscars won by the movie as an integer.
     * @see Movie#oscarsCount()
     * @see MpaaRating
     */
    public int getOscarsInt() {
        return (int) oscarsCount;
    }

    /**
     * @return MpaaRating of the movie
     * @see MpaaRating
     */
    public MpaaRating getRating() {
        return mpaaRating;
    }

    /**
     * Used to set the MPAA rating of the movie.
     *
     * @see Movie#mpaaRating
     * @see MpaaRating
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

//	/**
//	 * Used to compare ids.
//	 */
//	public int compareTo(Movie movie) {
//		return this.id.compareTo(movie.id);
//	}

    public Object[] getFields() {
        return new Object[]{id, name, coordinates, creationDate, oscarsCount, genre, mpaaRating, director};
    }

    /**
     * Used to compare movies.
     */
    @Override
    public int compareTo(Movie movie) {
        return Long.compare(oscarsCount(), movie.oscarsCount());
    }

    /**
     * Used see if two movies are set on the same position.
     */
    public boolean equals(Movie movie) {
        return coordinates.equals(movie.getCoordinates()) && name.equals(movie.getName()) && oscarsCount == movie.oscarsCount() && genre == movie.getGenre() && mpaaRating == movie.getRating() && director.equals(movie.getDirector());
    }

    @Override
    public String toString() {
        return id + ".\n"
                + "Film's title: " + name + ",\n"
                + "Film's coords: " + coordinates + ",\n"
                + "Creation Date: " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ",\n"
                + "Number of Oscars: " + oscarsCount + ",\n"
                + "Genre: " + genre + ",\n"
                + "Mpaa rating: " + mpaaRating + ",\n"
                + director.toString();
    }
}
