package itmo.lab6;

import itmo.lab6.basic.auxiliary.Convertible;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.utils.parser.Parser;
import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandType;
import itmo.lab6.connection.Connector;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector(5050);
        connector.setBufferSize(8192*8192);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String command = sc.nextLine();
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
            if (command.split(" ").length > 1) {
                switch (command.split(" ")[0]) {
                    case "insert" -> oos1.writeObject(new Command(cast(command), setId(Objects.requireNonNull(Parser.readObject(Movie.class)), Long.parseLong(command.split(" ")[1]))));
                    case "update" -> update(command, oos1, connector);
                    case "remove_key" -> oos1.writeObject(new Command(cast(command), Long.parseLong(command.split(" ")[1])));
                    case "remove_greater" -> oos1.writeObject(new Command(cast(command), Long.parseLong(command.split(" ")[1])));
                }
            } else oos1.writeObject(new Command(cast(command)));
            oos1.flush();
            connector.send(baos1.toByteArray());
            System.out.println(connector.receive());
            oos1.close();
        }
    }

    public static CommandType cast(String message) {
        return (Convertible.convert(message.split(" ")[0], CommandType.class) != null) ? Convertible.convert(message.split(" ")[0], CommandType.class) : CommandType.DEFAULT;
    }

    public static void update(String command, ObjectOutputStream oos1, Connector connector) throws Exception {
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
        oos2.writeObject(new Command(CommandType.SERVICE, "check_id " + command.split(" ")[1]));
        oos2.flush();
        connector.send(baos2.toByteArray());
        boolean isExist = Boolean.parseBoolean(connector.receive());
        if (!isExist) {
            System.out.println("No such element in the collection");
            oos1.close();
            oos1.writeObject(new Command(CommandType.SERVICE, ""));
            return;
        }
        oos2.close();
        oos1.close();
        oos1.writeObject(new Command(cast(command), setId(Parser.readObject(Movie.class), Long.parseLong(command.split(" ")[1]))));
        oos1.flush();
    }

    public static Movie setId(Movie movie, Long id) {
        movie.setId(id);
        return movie;
    }
}
