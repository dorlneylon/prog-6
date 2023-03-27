package itmo.lab6.basic.utils.parser;

import itmo.lab6.basic.types.builders.Builder;
import itmo.lab6.basic.types.builders.BuilderFactory;
import itmo.lab6.basic.types.builders.annotations.Generated;
import itmo.lab6.basic.utils.parser.exceptions.BadArgumentException;
import itmo.lab6.basic.utils.parser.exceptions.ObjectParsingException;
import itmo.lab6.basic.utils.strings.StringUtils;
import itmo.lab6.basic.utils.terminal.Colors;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;

public class UserInputParser extends AbstractParser {
    public UserInputParser() {
        super(System.in);
    }

    public <T> @Nullable T readObject(Class<T> objectType) throws ObjectParsingException {
        Builder builderObject = BuilderFactory.getBuilder(objectType.getSimpleName());
        Class<?> builderClass = builderObject.getClass();
        for (Field field : builderClass.getDeclaredFields()) {
            if (field.getType().isEnum()) {
                System.out.printf("\u001B[35m%s: %s%s%s%n", StringUtils.capitalize(field.getName()), Colors.AsciiRed, field.getType().getSimpleName(), Colors.AsciiReset);
                System.out.printf("\u001B[35mEnum constants: %s\u001B[0m%n", Arrays.toString(field.getType().getEnumConstants()));
                while (true) {
                    try {
                        System.out.print(">: ");
                        readEnum(field, builderObject);
                        break;
                    } catch (ObjectParsingException ignored) {
                        // continue
                    }
                }
                continue;
            }
            // If field is annotated with @Generated then generate value.
            // Else read value from input stream
            if (field.isAnnotationPresent(Generated.class)) {
                setValueToField(field, builderObject, generateValueByType(field));
                continue;
            }
            while (true) {
                System.out.printf("\u001B[35m%s: %s%s%s%n", StringUtils.capitalize(field.getName()), Colors.AsciiRed, field.getType().getSimpleName(), Colors.AsciiReset);
                try {
                    switch (field.getType().getSimpleName()) {
                        case "Date", "date" -> {
                            System.out.print(">: ");
                            readDate(field, builderObject);
                        }
                        case "Integer", "int" -> {
                            System.out.print(">: ");
                            readNumber("Integer", field, builderObject);
                        }
                        case "Long", "long" -> {
                            System.out.print(">: ");
                            readNumber("Long", field, builderObject);
                        }
                        case "Double", "double" -> {
                            System.out.print(">: ");
                            readNumber("Double", field, builderObject);
                        }
                        case "Float", "float" -> {
                            System.out.print(">: ");
                            readNumber("Float", field, builderObject);
                        }
                        case "String" -> {
                            System.out.print(">: ");
                            readString(field, builderObject);
                        }
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
                    break;
                } catch (BadArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return (T) builderObject.build();
    }
}
