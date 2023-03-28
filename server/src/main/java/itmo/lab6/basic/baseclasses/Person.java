package itmo.lab6.basic.baseclasses;

import itmo.lab6.basic.auxiliary.Randomness;
import itmo.lab6.basic.baseclasses.builders.annotations.Generated;
import itmo.lab6.basic.baseenums.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * The `Person` class implements `Serializable` interface and is used to store information about a person.
 * It has various instance variables to store the person's name, birthday, height, hair color, and location.
 * The class provides constructors with different parameters and getters and setters for all the instance variables.
 * It also provides an implementation of the `toString()` method for convenient output.
 *
 * @author zxc nylon
 * @version 1.0
 * @since 1999
 */
public class Person implements Serializable {
    @Serial
    @Generated
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * The name of the person.
     */
    private String name;

    /**
     * The birthday of the person.
     */
    private Date birthday;

    /**
     * The height of the person.
     */
    private int height;

    /**
     * The hair color of the person.
     */
    private Color hairColor;

    /**
     * The location of the person.
     */
    private Location location;

    /**
     * Creates a new `Person` object with the specified parameters.
     *
     * @param name      The name of the person.
     * @param birthday  The birthday of the person.
     * @param height    The height of the person.
     * @param hairColor The hair color of the person.
     * @param location  The location of the person.
     */
    public Person(String name, Date birthday, int height, Color hairColor, Location location) {
        if (name == null || name.isEmpty() || birthday == null || height < 0 || hairColor == null || location == null)
            throw new IllegalArgumentException("Can't be null or empty sequences.");
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.hairColor = hairColor;
        this.location = location;
    }

    /**
     * Creates a new `Person` object with the specified parameter.
     *
     * @param name The name of the person.
     */
    public Person(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Can't be null or empty sequences.");
        this.name = name;
        this.birthday = new Date();
        this.height = 1;
        this.hairColor = Randomness.random(Color.class);
        this.location = new Location();

    }

    /**
     * Creates a new `Person` object with standard parameters.
     */
    public Person() {
        this.name = "Quentin Tarantino";
        this.birthday = new Date();
        this.height = 1;
        this.hairColor = Randomness.random(Color.class);
        this.location = new Location();
    }

    /**
     * Returns the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name The name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the birthday of the person.
     *
     * @return The birthday of the person.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the person.
     *
     * @param birthday The birthday of the person.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the height of the person.
     *
     * @return The height of the person.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the person.
     *
     * @param height The height of the person.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the hair color of the person.
     *
     * @return The hair color of the person.
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Sets the hair color of the person.
     *
     * @param hairColor The hair color of the person.
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * Returns the location of the person.
     *
     * @return The location of the person.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the person.
     *
     * @param location The location of the person.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean equals(Person p) {
        return this.name.equals(p.name) && this.height == p.getHeight() && this.hairColor.equals(p.getHairColor()) && this.location.equals(p.getLocation());
    }

    @Override
    public String toString() {
        String birthday = new java.text.SimpleDateFormat("dd.MM.yyyy").format(this.birthday);
        return "Director's name: " + name + ",\n"
                + "Director's location: " + location + ",\n"
                + "Director's height: " + height + ",\n"
                + "Director's hair color: " + hairColor + ",\n"
                + "Director's birth date: " + birthday + ".\n";
    }
}
