package itmo.lab6.basic.utils.strings;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * String utils class
 *
 * @author kxrxh
 */
public final class StringUtils {
    /**
     * Capitalizes first letter of string
     *
     * @param str string to capitalize
     * @return capitalized string
     */
    @Contract("null -> null")
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Converts string from camelCase to snake_case
     *
     * @param input string in camelCase
     * @return string in snake_case
     */
    public static @NotNull String toSnakeCase(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Converts string from snake_case to camelCase
     *
     * @param input string in snake_case
     * @return string in camelCase
     */
    public static @NotNull String toCamelCase(String input) {
        StringBuilder builder = new StringBuilder();
        boolean shouldCapitalizeNext = false;
        for (var c : input.toCharArray()) {
            if (c == '_') {
                shouldCapitalizeNext = true;
            } else if (shouldCapitalizeNext) {
                builder.append(Character.toUpperCase(c));
                shouldCapitalizeNext = false;
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}