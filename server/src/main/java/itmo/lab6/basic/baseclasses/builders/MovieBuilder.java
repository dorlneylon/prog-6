package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Coordinates;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.MovieGenre;
import itmo.lab6.basic.baseenums.MpaaRating;

/**
 * The `Movie` class represents a movie, which includes its name, creation date,
 * number of Oscars it won, genre, MPAA rating, and director.
 *
 * @author dorlneylon
 * @version 99999999.9999999
 * @since 2023-02-02
 */
public class MovieBuilder implements Builder {
    private Long id;

    private String name;

    private Coordinates coordinates;

    private java.time.ZonedDateTime creationDate;

    private long oscarsCount;

    private MovieGenre genre;

    private MpaaRating mpaaRating;

    private Person director;

    @Override
    public Movie build() {
        return new Movie(id, name, coordinates, creationDate, oscarsCount, genre, mpaaRating, director);
    }
}
