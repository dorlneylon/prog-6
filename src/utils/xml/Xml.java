package itmo.kxrxh.lab5.utils.xml;

import java.io.File;

public class Xml {
    protected final File xmlFile;

    public Xml(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public XmlReader newReader() {
        return new XmlReader(this);
    }

    public XmlWriter newWriter() {
        return new XmlWriter(this);
    }

    public File getXmlFile() {
        return xmlFile;
    }
}
