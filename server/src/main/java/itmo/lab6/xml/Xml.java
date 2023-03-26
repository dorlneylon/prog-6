package itmo.lab6.xml;

import itmo.lab6.server.ServerLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class Xml {

    private final File xmlFile;

    public Xml(File file, boolean createFile) throws IOException {
        if (file.exists()) {
            xmlFile = file;
            return;
        }
        if (createFile) {
            boolean isCreated = file.createNewFile();
            if (isCreated) {
                ServerLogger.getLogger().log(Level.INFO, "New xml file was created successfully");
                this.xmlFile = file;
                return;
            }
        }
        throw new FileNotFoundException();
    }

    public Xml(File file) throws IOException {
        this(file, false);
    }

    public File getXmlFile() {
        return xmlFile;
    }

    public XmlReader newReader() {
        return new XmlReader(this);
    }

    public XmlWriter newWriter() {
        return new XmlWriter(this);
    }

}
