package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Coordinates;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.MovieGenre;
import itmo.lab6.basic.baseenums.MpaaRating;
import itmo.lab6.basic.utils.annotations.*;

/**
 * Builder for Product. Used for creating Product objects, while parsing XML.
 *
 * @see Builder
 */
public final class MovieBuilder implements Builder {
    @Value
    @Unique
    @Generated
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Coordinates coordinates;

    @NonNull
    @Generated
    private java.time.ZonedDateTime creationDate;

    @Value
    @NonNull
    private long oscarsCount;

    @NonNull
    private MovieGenre genre;

    @NonNull
    private MpaaRating mpaaRating;

    @NonNull
    private Person director;

    @Override
    public Movie build() {
        return new Movie(id, name, coordinates, oscarsCount, genre, mpaaRating, director);
    }
}