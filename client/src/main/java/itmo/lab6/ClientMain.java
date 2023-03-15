package itmo.lab6;

import itmo.lab6.basic.auxiliary.Convertible;
import itmo.lab6.basic.utils.parser.Parser;
import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandType;
import itmo.lab6.connection.Connector;
import itmo.lab6.basic.baseclasses.Movie;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
		Connector connector = new Connector(5050);
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter command:");
			String command = sc.nextLine();
			ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
			if (command.split(" ").length > 1) {
				if (command.split(" ")[0].equals("insert")) {
					Movie movie = Parser.readObject(Movie.class);
					movie.setId(Long.parseLong(command.split(" ")[1]));
					oos1.writeObject(new Command(cast(command), movie));
				}
				else oos1.writeObject(new Command(cast(command), "TODO"));
			}
			else oos1.writeObject(new Command(cast(command)));
			oos1.flush();
			connector.send(baos1.toByteArray());
			System.out.println(connector.receive());
			oos1.close();
		}
   }

	public static CommandType cast(String message) {
		return (Convertible.convert(message.split(" ")[0], CommandType.class) != null) ? Convertible.convert(message.split(" ")[0], CommandType.class) : CommandType.DEFAULT;
	}
}
