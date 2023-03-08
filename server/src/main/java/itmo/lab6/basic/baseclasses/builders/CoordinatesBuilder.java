package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Coordinates;

public class CoordinatesBuilder implements Builder {

    private float x;
    private Integer y;

    @Override
    public Coordinates build() {
        return new Coordinates(x, y);
    }
}
