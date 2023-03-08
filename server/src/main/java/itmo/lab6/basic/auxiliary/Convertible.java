package itmo.lab6.basic.auxiliary;

/**
 * The `Convertible` class is used to convert strings to enums.
 * It has a single method `convert()` that is used to convert the string to the enum.
 *
 * @author nylon (dorlneylon).
 */
public class Convertible {
    public static <T extends Enum<T>> T convert(String str, Class<T> enumClass) {
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.toString().equalsIgnoreCase(str)) {
                return constant;
            }
        }
        return null;
    }
}
