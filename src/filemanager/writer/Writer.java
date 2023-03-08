package src.filemanager.writer;

import src.baseclasses.Coordinates;
import src.baseclasses.Movie;
import src.baseclasses.Person;
import src.baseenums.MovieGenre;
import src.baseenums.MpaaRating;
import src.filemanager.FileManager;
import src.moviecollection.MovieCol;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Writer class that writes the data to an XML file.
 * Extends the FileManager class.
 * <p>
 * The name of the XML file is provided in the constructor OR in the parameter of the method {@link Writer#writeData()}.
 * @see FileManager
 * @see Reader
 * @see Movie
 * @see MovieCol
 * @see Coordinates
 * @see MovieGenre
 * @see MpaaRating
 * @see Person
 * etc...
 *
 *
 * @author dorlneylon
 * @version я устал
 * @since 02.02.2023
 */
public class Writer extends FileManager {
    private final MovieCol movies;

    /**
     * Constructor that takes in a MovieCol and calls the super constructor.
     *
     * @param mc the collection of movies to be written to an XML file
     */
    public Writer(MovieCol mc) {
        super("standard.xml");
        movies = mc;
    }

    /**
     * Method to write the data to the XML file with the given filename.
     *
     * @param filename the name of the XML file to write the data to
     * @throws Exception if the field is null or the formatting is incorrect
     * check the header of the class for more information.
     */
    public void writeData(String filename) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Movies>\n");
        int i = 0;

        for (Movie entry : movies.sorted(false)) {
            xml.append("\t<Movie>\n");
            xml.append("\t\t<id>").append(entry.getId()).append("</id>\n");
            xml.append("\t\t<name>").append(entry.getName()).append("</name>\n");
            xml.append("\t\t<coordinates>\n");
            xml.append("\t\t\t<x>").append(entry.getCoordinates().getX()).append("</x>\n");
            xml.append("\t\t\t<y>").append(entry.getCoordinates().getY()).append("</y>\n");
            xml.append("\t\t</coordinates>\n");
            xml.append("\t\t<creationDate>").append(entry.getCreationDate().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)).append("</creationDate>\n");
            xml.append("\t\t<oscarsCount>").append(entry.oscarsCount()).append("</oscarsCount>\n");
            xml.append("\t\t<genre>").append(entry.getGenre()).append("</genre>\n");
            xml.append("\t\t<mpaaRating>").append(entry.getRating()).append("</mpaaRating>\n");
            xml.append("\t\t<director>\n");
            xml.append("\t\t\t<name>").append(entry.getDirector().getName()).append("</name>\n");
            xml.append("\t\t\t<birthday>").append(new SimpleDateFormat("dd.MM.yyyy").format(entry.getDirector().getBirthday())).append("</birthday>\n");
            xml.append("\t\t\t<height>").append(entry.getDirector().getHeight()).append("</height>\n");
            xml.append("\t\t\t<hairColor>").append(entry.getDirector().getHairColor()).append("</hairColor>\n");
            xml.append("\t\t\t<location>\n");
            xml.append("\t\t\t\t<x>").append(entry.getDirector().getLocation().getX()).append("</x>\n");
            xml.append("\t\t\t\t<y>").append(entry.getDirector().getLocation().getY()).append("</y>\n");
            xml.append("\t\t\t\t<z>").append(entry.getDirector().getLocation().getZ()).append("</z>\n");
            xml.append("\t\t\t</location>\n");
            xml.append("\t\t</director>\n");
            xml.append("\t</Movie>\n");
        }
        xml.append("</Movies>");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(xml.toString());
            writer.close();
        } catch (IOException e) {
            logErrors(e);
        }
    }

    public static void logErrors(Exception e) {
        try {
            FileWriter fw = new FileWriter("logger", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("--- Error Log ---");
            e.printStackTrace(pw);
            pw.println("------------------");

            pw.close();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Overridden method from the FileManager class to write the data to the XML file.
     *
     * @throws Exception if there is an error in writing the data
     */
    @Override
    protected void writeData()  {
        writeData(filename);
    }

    /**
     * Overridden method from the FileManager class to read the data from the XML file.
     */
    @Override
    protected void readData() {
        System.out.println("Data is being saved to " + filename);
    }
}
