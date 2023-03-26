package itmo.lab6.utils.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

    private HashMap<String, String> configMap;

    public Config(String fileName) {
        File config = new File(fileName);
        if (config.exists()) {
            this.configMap = readFile(config);
        }
    }

    public String get(String key) {
        return configMap.get(key);
    }

    private HashMap<String, String> readFile(File config) {
        HashMap<String, String> configMap = new HashMap<>();
        try (Scanner scanner = new Scanner(config)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Skipping comments and lines without "Key=Value"
                if (line.startsWith("#") || !line.contains("=")) {
                    continue;
                }
                String[] split = line.split("=", 2);
                if (split.length < 2) continue;
                configMap.put(split[0], split[1]);
            }
        } catch (FileNotFoundException ignored) {
            // Ignoring this, because file was existence checked
        }
        return configMap;
    }
}
