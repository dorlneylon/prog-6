package itmo.kxrxh.lab5.utils;

import itmo.kxrxh.lab5.collection.CollectionCore;
import itmo.kxrxh.lab5.utils.xml.Xml;

import java.io.File;
import java.io.IOException;

public final class TempFiles {
    private static final String TempDirectory = System.getProperty("java.io.tmpdir");

    /**
     * Methods for getting all temporary files from temp directory
     *
     * @return array of temporary files
     */
    public static File[] getTempFiles() {
        File dir = new File(TempDirectory);
        return dir.listFiles((d, name) -> name.startsWith("lab5") && name.endsWith(".tmp"));
    }

    /**
     * Deletes all temporary files from temp directory
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteTempFiles() {
        for (File file : getTempFiles()) {
            file.delete();
        }
    }

    /**
     * Creates new temporary file in temp directory.
     */
    public static void createTempFile() {
        File tmpFile;
        try {
            tmpFile = File.createTempFile("lab5", ".tmp");
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temporary file", e);
        }
        new Xml(tmpFile).newWriter().writeCollection(CollectionCore.getCollectionManager().collection());
    }
}
