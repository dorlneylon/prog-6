package itmo.lab6;
import itmo.lab6.connection.Connector;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector(5050);
        connector.send("Hello World!");
		connector.send("show");
        String s = connector.receive();
		System.out.println(s);
        connector.send("info");
        s = connector.receive();
        System.out.println(s);
        connector.close();
    }
}
