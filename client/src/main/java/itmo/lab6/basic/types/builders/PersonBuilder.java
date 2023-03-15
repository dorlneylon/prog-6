package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.Color;
import itmo.lab6.basic.utils.annotations.*;

import java.util.Date;

/**
 * Builder for Product. Used for creating Product objects, while parsing XML.
 *
 * @see Builder
 */
public final class PersonBuilder implements Builder {

    @NonNull
    private String name;

    /**
     * The birthday of the person.
     */
    @NonNull
    private Date birthday;

    /**
     * The height of the person.
     */
    @Value
    @NonNull
    private int height;

    /**
     * The hair color of the person.
     */
    @NonNull
    private Color hairColor;

    /**
     * The location of the person.
     */
    @NonNull
    private Location location;

    @Override
    public Person build() {
        return new Person(name, birthday, height, hairColor, location);
    }
}