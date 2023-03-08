package itmo.kxrxh.lab5.utils.env.dotenv;

import java.util.Map;

/**
 * DotEnv class. Loads environment variables from .env file.
 *
 * @author kxrxh
 */
public final class DotEnv {
    /**
     * Path to .env file.
     */
    private final String path;
    /**
     * Map of environment variables. Key - variable name, value - variable value.
     */
    private Map<String, String> env_map;

    /**
     * Instantiates a new Dot env.
     *
     * @param path Path to .env file.
     */
    public DotEnv(String path) {
        this.path = path;
    }

    /**
     * Loads environment variables from .env file. Puts them into env_map.
     */
    public DotEnv load() {
        env_map = FileParser.parse(path);
        return this;
    }

    /**
     * Returns value of environment variable.
     *
     * @param key Key of environment variable.
     * @return Value of environment variable.
     */
    public String get(String key) {
        return env_map.get(key);
    }
}
