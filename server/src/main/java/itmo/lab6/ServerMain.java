package itmo.lab6;

import itmo.lab6.basic.moviecollection.MovieCollection;
import itmo.lab6.server.UdpServer;
import itmo.lab6.xml.Xml;

import java.io.File;
import java.util.Arrays;


public class ServerMain {
    public static void main(String[] args) {
        MovieCollection collection = new Xml(new File("col.xml")).newReader().parse();
        System.out.println(Arrays.toString(collection.values()));
        UdpServer server = new UdpServer(collection, 5050);
        try {
            server.run();
        } catch (Exception e) {
            // kxr: Попадает ли он сюда вообще??
            // nyl: Нет, не попадает. Да и хуй с ним.
            System.err.println(e.getMessage());
        }
    }
}