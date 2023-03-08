package src.filemanager;

/**
 * Abstract class for managing file operations.
 *
 * @author dorlneylon
 */
public abstract class FileManager {

    /** The name of the file to be managed. */
    protected String filename;

    /**
     * Constructs a new `FileManager` object with the specified filename.
     *
     * @param filename the name of the file to be managed
     */
    public FileManager(String filename) {
        this.filename = filename;
    }

    /**
     * Returns the name of the file being managed.
     *
     * @return the name of the file
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the name of the file to be managed.
     *
     * @param filename the new name of the file
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Reads data from the file.
     *
     * @throws Exception if an error occurs while reading the data
     */
    protected abstract void readData() ;

    /**
     * Writes data to the file.
     *
     * @throws Exception if an error occurs while writing the data
     */
    protected abstract void writeData() ;

}
