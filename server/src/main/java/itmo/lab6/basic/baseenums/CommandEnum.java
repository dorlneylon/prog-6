package itmo.lab6.basic.baseenums;

import java.io.Serializable;

public enum CommandEnum implements Serializable {
    INSERT,
    EXECUTE_SCRIPT,
    CLEAR,
    HISTORY,
    REMOVE_BY_MPAA_RATING,
    PRINT_ASCENDING,
    PRINT_DESCENDING,
    EXIT,
    HELP,
    INFO,
    REMOVE_KEY,
    REMOVE_GREATER,
    REMOVE_IF_LOWER,
    SHOW,
    UPDATE,
    DEFAULT
}
