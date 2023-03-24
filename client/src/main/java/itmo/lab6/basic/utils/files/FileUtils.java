package itmo.lab6.basic.utils.files;

import java.io.File;

public final class FileUtils {
    /**
     * Checks if the given file exists, and it isn't a directory
     *
     * @param path path to the file
     * @return true if the file exists and false otherwise
     */
    public static boolean isFileExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        // Return false value if file is directory.
        return file.isFile();
    }
}
