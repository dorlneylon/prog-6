package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Coordinates;
import itmo.lab6.basic.utils.annotations.*;

import java.util.Date;

/**
 * Builder for Product. Used for creating Product objects, while parsing XML.
 *
 * @see Builder
 */
public final class CoordinatesBuilder implements Builder {
    @Value
    @NonNull
    private float x;

    @Value
    @NonNull
    private Integer y;

    @Override
    public Coordinates build() {
        return new Coordinates(x, y);
    }
}