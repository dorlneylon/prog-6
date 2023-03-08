package itmo.lab6.basic.baseclasses.builders;

import itmo.lab6.basic.baseclasses.Location;

public class LocationBuilder implements Builder {

    private long x;
    private Double y;
    private double z;
    @Override
    public Location build() {
        return new Location(x, y, z);
    }
}
