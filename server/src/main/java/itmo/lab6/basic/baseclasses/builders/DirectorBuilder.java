package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.Color;

import java.util.Date;

public class DirectorBuilder implements Builder {
    private String name;

    private Date birthday;

    private int height;
    private Color hairColor;
    private Location location;

    @Override
    public Person build() {
        return new Person(name, birthday, height, hairColor, location);
    }
}
