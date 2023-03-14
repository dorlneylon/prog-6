package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Coordinates;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseclasses.builders.annotations.Generated;
import itmo.lab6.basic.baseclasses.builders.annotations.NotEmpty;
import itmo.lab6.basic.baseclasses.builders.annotations.NotNull;
import itmo.lab6.basic.baseclasses.builders.annotations.Value;
import itmo.lab6.basic.baseenums.MovieGenre;
import itmo.lab6.basic.baseenums.MpaaRating;

public class MovieBuilder implements Builder {
    @NotNull
    @Value(min = 0)
    private Long id;
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Coordinates coordinates;
    @NotNull
    @Generated
    private java.time.ZonedDateTime creationDate;

    @Value(min = 0)
    private long oscarsCount;

    @NotNull
    private MovieGenre genre;
    @NotNull
    private MpaaRating mpaaRating;
    @NotNull
    private Person director;

    @Override
    public Movie build() {
        return new Movie(id, name, coordinates, creationDate, oscarsCount, genre, mpaaRating, director);
    }
}
