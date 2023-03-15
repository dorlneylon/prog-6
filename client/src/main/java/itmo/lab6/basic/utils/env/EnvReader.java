package itmo.kxrxh.lab5.utils.env;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * EnvReader class. Reads environment variables. You should it for linux environment variables.
 *
 * @author kxrxh
 */
public final class EnvReader {
    /**
     * Map of environment variables. Key is variable name, value is variable value.
     */
    public final static Map<String, String> env = System.getenv();

    /**
     * Getter for environment variable. Returns null if variable is not found.
     *
     * @param key environment variable name
     * @return environment variable value
     */
    public static @Nullable String get(String key) {
        return env.get(key);
    }
}
