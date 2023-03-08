package itmo.kxrxh.lab5.utils.parser;

import itmo.kxrxh.lab5.Constants;
import itmo.kxrxh.lab5.types.builders.Builder;
import itmo.kxrxh.lab5.utils.annotations.Generated;
import itmo.kxrxh.lab5.utils.annotations.Length;
import itmo.kxrxh.lab5.utils.annotations.NonNull;
import itmo.kxrxh.lab5.utils.annotations.Value;
import itmo.kxrxh.lab5.utils.generators.IdGenerator;
import itmo.kxrxh.lab5.utils.generators.Time;
import itmo.kxrxh.lab5.utils.strings.StringUtils;
import itmo.kxrxh.lab5.utils.terminal.Colors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Parser {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This method reads object of type T from console.
     * It uses builder pattern to create object. It reads all fields of the object
     * and creates object using builder.
     * <p>
     * Also, it uses annotations to validate input data.
     * <p>List of annotations: {@link Value}, {@link Length}, {@link NonNull}, {@link Generated}.
     *
     * @param objectType Type of object to read.
     * @param <T>        Generic type of object to read.
     * @return Object of type T.
     * @see itmo.kxrxh.lab5.types.builders
     */
    @SuppressWarnings("unchecked")
    public static <T> @Nullable T readObject(Class<T> objectType) {
        // Getting class of the class builder for the object.
        Class<?> builderClass;
        try {
            builderClass = Class.forName("%s.%sBuilder".formatted(Constants.BuildersPath, objectType.getSimpleName()));
        } catch (ClassNotFoundException e) {
            System.err.println("Can't find builder for class: " + objectType.getSimpleName());
            return null;
        }
        // Creating builder object.
        Builder object;
        try {
            object = (Builder) builderClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.println("Can't create builder");
            return null;
        }
        System.out.printf("%s%s%s%n", Colors.AsciiRed, objectType.getSimpleName(), Colors.AsciiReset);
        for (Field field : builderClass.getDeclaredFields()) {
            field.setAccessible(true);
            // If field is Enum then converting string value to Enum<?> value.
            if (field.getType().isEnum()) {
                System.out.printf("\u001B[35m%s (%s): %n\u001B[0m", StringUtils.capitalize(field.getName()), field.getType().getSimpleName());
                while (true) {
                    System.out.print(">: ");
                    String value;
                    try {
                        value = scanner.nextLine();
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                        return null;
                    }
                    try {
                        setValueToField(field, object, stringToEnum(field, value));
                        break;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Enum value is not valid");
                        System.err.println("Enum constants: " + Arrays.toString(field.getType().getEnumConstants()));
                    }
                }
                continue;
            }
            // If field is annotated with @Generated then generate value.
            // Else read value from console
            if (field.isAnnotationPresent(Generated.class)) {
                setValueToField(field, object, generateValueByType(field));
                continue;
            }

            // if field type is built-in type or String then read it
            switch (field.getType().getSimpleName()) {
                case "Integer", "int" -> readNumber("Integer", field, object);
                case "Long", "long" -> readNumber("Long", field, object);
                case "Double", "double" -> readNumber("Double", field, object);
                case "Float", "float" -> readNumber("Float", field, object);
                case "String" -> {
                    System.out.printf("\u001B[35m%s (String): %n\u001B[0m", StringUtils.capitalize(field.getName()));
                    while (true) {
                        System.out.print(">: ");
                        String value;
                        try {
                            value = scanner.nextLine();
                        } catch (NoSuchElementException e) {
                            System.exit(0);
                            return null;
                        }
                        if (checkString(field, value)) {
                            if (value.equals("")) {
                                value = null;
                            }
                            setValueToField(field, object, value);
                            break;
                        }
                    }

                }
                default -> {
                    // if field type is not built-in type then read it recursively
                    try {
                        field.set(object, readObject(field.getType()));
                    } catch (IllegalAccessException e) {
                        System.err.println("Cannot read field: " + field.getName());
                    }
                }
            }
        }
        return (T) object.build();
    }

    private static void readNumber(String numType, Field field, Builder object) {
        System.out.printf("\u001B[35m%s (%s): %n\u001B[0m", StringUtils.capitalize(field.getName()), numType);
        while (true) {
            System.out.print(">: ");
            Number value;
            try {
                value = NumberFormat.getInstance().parse(scanner.nextLine());
            } catch (ParseException e) {
                System.err.printf("%sInput is not of type %s%s%n", Colors.AsciiRed, numType, Colors.AsciiReset);
                continue;
            }

            value = switch (numType) {
                case "Integer" -> value.intValue();
                case "Long" -> value.longValue();
                case "Double" -> value.doubleValue();
                case "Float" -> value.floatValue();
                default -> value;
            };
            if (checkNumber(field, value)) {
                // System.out.println(value);
                setValueToField(field, object, value);
                break;
            }
        }
    }

    /**
     * This method checks if value of field is valid.
     * <p>
     * If field is annotated with {@link Value} then it checks if value is in range.
     *
     * @param field field is used to get annotation
     * @param value value to check
     * @param <T>   type of value
     * @return true if value is valid
     */
    private static <T extends Number> boolean checkNumber(Field field, T value) {
        Value valueAnnotation = null;
        if (field.isAnnotationPresent(Value.class)) {
            valueAnnotation = field.getAnnotation(Value.class);
        }
        if (valueAnnotation != null) {
            if (value.doubleValue() <= valueAnnotation.min()) {
                System.err.printf("%sValue must be greater than %s%s%n".formatted(Colors.AsciiRed, valueAnnotation.min(), Colors.AsciiReset));
                return false;
            }
            if (value.doubleValue() >= valueAnnotation.max()) {
                System.err.printf("%sValue must be less than %s%s%n".formatted(Colors.AsciiRed, valueAnnotation.max(), Colors.AsciiReset));
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if string is valid. If string is empty and field is annotated with {@link NonNull} then it will return false.
     * <p>
     * If string length is less than {@link Length#min()} then it will return false.
     * <p>
     * If string length is greater than {@link Length#max()} then it will return false.
     * <p>
     * Otherwise, it will return true.
     *
     * @param field field is needed to get annotations from it
     * @param value value to check
     * @return true if value is valid
     */
    private static boolean checkString(Field field, String value) {
        if (field.isAnnotationPresent(NonNull.class) && value.isEmpty()) {
            System.err.println("Field can't be null");
            return false;
        }
        if (field.isAnnotationPresent(Length.class)) {
            Length lengthAnnotation = field.getAnnotation(Length.class);
            if (value.length() < lengthAnnotation.min()) {
                System.err.printf("\u001B[31mLength must be greater than %d\n\u001B[0m", lengthAnnotation.min() - 1);
                return false;
            }
            if (value.length() > lengthAnnotation.max()) {
                System.err.printf("\u001B[31mLength must be less than %d\n\u001B[0m", lengthAnnotation.max() + 1);
                return false;
            }
        }
        return true;
    }

    /**
     * This method calls method to generate value by type.
     *
     * @param field field to generate value
     * @param <T>   type of field
     * @return generated value
     * @see IdGenerator
     * @see Time
     */
    private static <T> T generateValueByType(Field field) {
        return (T) switch (field.getType().getSimpleName()) {
            case "Integer", "int" -> IdGenerator.generateIntId();
            case "Long", "long" -> IdGenerator.generateLongId();
            case "LocalDateTime" -> Time.getTime();
            default -> null;
        };
    }

    /**
     * This method sets value to field.
     *
     * @param field  field to set value
     * @param object object to set value
     * @param value  value to set
     */
    private static void setValueToField(Field field, Object object, Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            System.err.println("\u001B[31mCan't set value to field\u001B[0m");
        } catch (InputMismatchException e) {
            System.err.printf("%sWrong input%s%s%n", Colors.AsciiRed, e.getMessage(), Colors.AsciiReset);
        }
    }

    /**
     * This method converts string to enum.
     *
     * @param field field to get type
     * @param value value to convert
     * @return converted value
     * @see Enum
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static @NotNull Enum<?> stringToEnum(Field field, String value) {
        return (Enum<?>) Enum.valueOf((Class<Enum>) field.getType(), value);
    }
}
