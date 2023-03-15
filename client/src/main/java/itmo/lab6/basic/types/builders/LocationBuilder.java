package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.utils.annotations.*;

public final class LocationBuilder implements Builder {
    @Value
    @NonNull
    private long x;

    @Value
    @NonNull
    private Double y;

    @Value
    @NonNull
    private double z;

    @Override
    public Location build() {
        return new Location(x, y, z);
    }
}