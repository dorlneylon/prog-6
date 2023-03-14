package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseclasses.builders.annotations.NotEmpty;
import itmo.lab6.basic.baseclasses.builders.annotations.NotNull;
import itmo.lab6.basic.baseclasses.builders.annotations.Value;
import itmo.lab6.basic.baseenums.Color;

import java.util.Date;

public class DirectorBuilder implements Builder {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Date birthday;

    @Value(min = 0)
    private int height;
    @NotNull
    private Color hairColor;
    @NotNull
    private Location location;

    @Override
    public Person build() {
        return new Person(name, birthday, height, hairColor, location);
    }
}
