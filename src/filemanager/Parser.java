package src.filemanager;

import src.baseclasses.Coordinates;
import src.baseclasses.Location;
import src.baseenums.Color;
import src.baseenums.MovieGenre;
import src.baseenums.MpaaRating;
import src.moviecollection.MovieCol;

/**
 * The Parser class is an abstract class that extends the FileManager class and implements the parseData method.
 * This class contains instance variables used to store information about movies.
 *
 * @param <T> the generic type of object returned by the parseData method
 *
 * @author dorlneylon
 * @version 1.0?
 * @since 03.02.2023
 */
public abstract class Parser<T> extends FileManager {

    /** The id of the movie. */
    protected static Long id;

    /** The name of the movie. */
    protected static String name;

    /** The coordinates of the movie. */
    protected static Coordinates coordinates;

    /** The creation date of the movie. */
    protected static java.time.ZonedDateTime creationDate;

    /** The number of Oscars received by the movie. */
    protected static long oscarsCount;

    /** The genre of the movie. */
    protected static MovieGenre genre;

    /** The MPAA rating of the movie. */
    protected static MpaaRating mpaaRating;

    /** The name of the director. */
    protected static String dname;

    /** The date of birth of the director. */
    protected static java.util.Date ddate;

    /** The height of the director. */
    protected static int dheight;

    /** The eye color of the director. */
    protected static Color dcolor;

    /** The location of the director. */
    protected static Location dlocation;

    /** The collection of movies. */
    protected MovieCol movies;

    /**
     * Constructs a Parser object with the specified filename and collection of movies.
     *
     * @param filename the name of the file to be processed
     * @param movies the collection of movies
     */
    public Parser(String filename, MovieCol movies) {
        super(filename);
        this.movies = movies;
    }

    /**
     * Parses the data in the specified file and returns a generic object.
     *
     * @return a generic object containing the information about the movies
     * @throws Exception if there is an error reading the file or processing the data
     */
    protected abstract T parseData() ;
}