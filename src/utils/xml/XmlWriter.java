package itmo.kxrxh.lab5.utils.xml;


import itmo.kxrxh.lab5.collection.ProductCollector;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static itmo.kxrxh.lab5.utils.strings.StringUtils.toSnakeCase;

/**
 * Xml writer class. Used for writing to xml file
 *
 * @author kxrxh
 * @see Xml
 * @see XmlAction
 */
public class XmlWriter extends XmlAction {
    private final BufferedOutputStream bufferedOutput;
    private int indentLevel = 0;

    protected XmlWriter(Xml xml) {
        super(xml);
        try {
            this.bufferedOutput = new BufferedOutputStream(new FileOutputStream(xml.getXmlFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
    }

    /**
     * Write string to file with new line ('\n')
     *
     * @param line String to write
     */
    protected void writeLine(String line) throws RuntimeException {
        try {
            bufferedOutput.write(line.getBytes());
            bufferedOutput.write("\n".getBytes());
            bufferedOutput.flush();

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }
    }

    /**
     * Write collection to file in xml format
     *
     * @param collection Collection to write
     */
    public void writeCollection(ProductCollector collection) {
        writeLine(indentString() + "<" + collection.getClass().getSimpleName() + ">");
        indentLevel++;
        collection.forEach(this::writeObject);
        indentLevel--;
        writeLine(indentString() + "</" + collection.getClass().getSimpleName() + ">");
    }

    /**
     * Write object to file in xml format
     *
     * @param object Object to write
     */
    private void writeObject(Object object) throws RuntimeException {
        writeLine(indentString() + "<" + object.getClass().getSimpleName() + ">");
        indentLevel++;
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                if (List.of("Address", "Coordinates", "Organization", "Product").contains(field.getType().getSimpleName())) {
                    writeObject(field.get(object));
                    return;
                }
                writeLine(indentString() + "<" + toSnakeCase(field.getName()) + ">" + field.get(object) + "</" + toSnakeCase(field.getName()) + ">");
            } catch (IllegalAccessException e) {
                System.out.println("Error while writing to file: " + e.getMessage());
            }
        });
        --indentLevel;
        writeLine(indentString() + "</" + object.getClass().getSimpleName() + ">");
    }

    /**
     * Generate indent string
     *
     * @return Indent string
     */
    private @NotNull String indentString() {
        return new String(new char[indentLevel * 4]).replace("\0", " ");
    }
}


