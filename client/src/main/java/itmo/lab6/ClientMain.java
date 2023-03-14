package itmo.lab6;

import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandType;
import itmo.lab6.connection.Connector;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector(5050);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream2.writeObject(new Command(CommandType.INFO));
        objectOutputStream2.flush();
        // Предлагаю отправлять класс Response с сервера
        connector.send(byteArrayOutputStream.toByteArray());
        System.out.println(connector.receive());
        connector.close();
    }
}
