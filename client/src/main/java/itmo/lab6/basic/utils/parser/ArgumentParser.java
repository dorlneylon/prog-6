package itmo.lab6.basic.utils.parser;

import itmo.lab6.basic.types.builders.Builder;
import itmo.lab6.basic.types.builders.BuilderFactory;
import itmo.lab6.basic.types.builders.annotations.Generated;
import itmo.lab6.basic.utils.parser.exceptions.BadArgumentException;
import itmo.lab6.basic.utils.parser.exceptions.ObjectParsingException;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;

public class ArgumentParser extends AbstractParser {
    public ArgumentParser(String[] movieFields) {
        super(new ByteArrayInputStream(String.join("\n", movieFields).getBytes()));
    }

    @Override
    public <T> @Nullable T readObject(Class<T> objectType) throws ObjectParsingException {
        Builder builderObject = BuilderFactory.getBuilder(objectType.getSimpleName());
        Class<?> builderClass = builderObject.getClass();
        for (Field field : builderClass.getDeclaredFields()) {
            if (field.getType().isEnum()) {
                readEnum(field, builderObject);
                continue;
            }
            // If field is annotated with @Generated then generate value.
            // Else read value from input stream
            if (field.isAnnotationPresent(Generated.class)) {
                setValueToField(field, builderObject, generateValueByType(field));
                continue;
            }
            try {
                switch (field.getType().getSimpleName()) {
                    case "Date", "date" -> readDate(field, builderObject);
                    case "Integer", "int" -> readNumber("Integer", field, builderObject);
                    case "Long", "long" -> readNumber("Long", field, builderObject);
                    case "Double", "double" -> readNumber("Double", field, builderObject);
                    case "Float", "float" -> readNumber("Float", field, builderObject);
                    case "String" -> readString(field, builderObject);
                    default -> {
                        // if field type is not built-in type then read it recursively
                        try {
                            Object innerObject = readObject(field.getType());
                            field.setAccessible(true);
                            field.set(builderObject, innerObject);
                        } catch (IllegalAccessException ignored) {
                            // it isn't possible, I guess...
                        }
                    }
                }
            } catch (BadArgumentException e) {
                System.err.println(e.getMessage());
                throw new ObjectParsingException(objectType);
            } catch (NoSuchElementException e) {
                System.out.printf("Ctrl-D detected.\n Shutting the client down...\n", e.getMessage());
                System.exit(1);
            }
        }
        return (T) builderObject.build();
    }
}
