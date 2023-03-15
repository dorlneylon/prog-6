package itmo.lab6.basic.types.builders;

/**
 * Interface for builders
 * <p>
 * Used for creating objects, while parsing XML.
 * To add validation, use annotations.
 *
 * @author kxrxh
 */

public interface Builder {
    Object build();
}
