package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.types.builders.annotations.NotNull;

import java.util.Date;

public class DateBuilder implements Builder {
    @NotNull
    private Date date;

    @Override
    public Date build() {
        return date;
    }
}
