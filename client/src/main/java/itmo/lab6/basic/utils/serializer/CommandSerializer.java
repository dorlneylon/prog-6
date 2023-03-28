package itmo.lab6.basic.utils.serializer;

import itmo.lab6.commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * CommandSerializer is used to serialize a command
 * {@link Command}
 */
public final class CommandSerializer {
    /**
     * Serializes object and returns the serialized object as a byte array
     *
     * @param object object to serialize
     * @param <T>    Type of object to serialize
     * @return bytes of serialized object
     */
    public static <T extends Serializable> byte[] serialize(T object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            System.err.println("Unable to create ObjectOutputStream: " + e);
            return new byte[0];
        }
        try {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            System.err.println("Unable to serialize object: " + object.getClass().getSimpleName() + ": " + e);
            return new byte[0];
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            System.err.println("Unable to close ObjectOutputStream");
        }
        return byteArrayOutputStream.toByteArray();
    }
}
