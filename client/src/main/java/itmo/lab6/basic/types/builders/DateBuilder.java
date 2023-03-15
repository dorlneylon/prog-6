package itmo.lab6.basic.types.builders;

import itmo.lab6.basic.utils.annotations.NonNull;

import java.util.Date;

public class DateBuilder implements Builder {
    @NonNull
    private Date date;

    @Override
    public Date build() {
        return date;
    }
}
