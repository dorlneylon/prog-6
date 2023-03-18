package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.types.builders.annotations.NotNull;

public class LocationBuilder implements Builder {

    private long x;
    @NotNull
    private Double y;
    private double z;

    @Override
    public Location build() {
        return new Location(x, y, z);
    }
}
