package itmo.kxrxh.lab5.utils.env.dotenv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * FileParser class. Parses .env file.
 *
 * @author kxrxh
 */
public final class FileParser {
    /**
     * Parse map.
     *
     * @param path Path to .env file.
     * @return Map of environment variables. Key - variable name, value - variable value.
     */
    public static Map<String, String> parse(String path) {
        Map<String, String> result = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#") || !line.contains("=")) {
                    continue;
                }
                String[] split = line.split("=", 2);
                result.put(split[0], split[1]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("DotEnv: File not found", e);
        }
        return result;
    }

}
