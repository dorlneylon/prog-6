package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.Color;
import itmo.lab6.basic.types.builders.annotations.NotNull;
import itmo.lab6.basic.types.builders.annotations.Value;

import java.util.Date;

/**
 * Builder for Product. Used for creating Product objects, while parsing XML.
 *
 * @see Builder
 */
public final class PersonBuilder implements Builder {

    @NotNull
    private String name;

    /**
     * The birthday of the person.
     */
    @NotNull
    private Date birthday;

    /**
     * The height of the person.
     */
    @Value(min = 0)
    @NotNull
    private int height;

    /**
     * The hair color of the person.
     */
    @NotNull
    private Color hairColor;

    /**
     * The location of the person.
     */
    @NotNull
    private Location location;

    @Override
    public Person build() {
        return new Person(name, birthday, height, hairColor, location);
    }
}