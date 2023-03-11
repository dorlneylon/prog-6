package itmo.lab6;
import java.util.Scanner;
import itmo.lab6.connection.Connector;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector(5050);
		Scanner sc = new Scanner(System.in);
		while (true) {
			String command = sc.nextLine();
			connector.send(command);
			System.out.println(connector.receive());
		}
	}
}
