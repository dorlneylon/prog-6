package itmo.lab6.basic.utils.parser.exceptions;

import java.lang.reflect.Type;

public class ObjectParsingException extends RuntimeException {
    public ObjectParsingException(Type type) {
        super("Unable to parse object of type " + type.getTypeName());
    }
}
