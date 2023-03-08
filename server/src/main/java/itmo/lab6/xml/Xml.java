package itmo.lab6.xml;

import java.io.File;

public record Xml(File xmlFile) {


    public XmlReader newReader() {
        return new XmlReader(this);
    }

    public XmlWriter newWriter() {
        return new XmlWriter(this);
    }

}
