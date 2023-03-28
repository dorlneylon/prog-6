package itmo.lab6.basic.utils.parser;

import itmo.lab6.basic.types.builders.Builder;
import itmo.lab6.basic.types.builders.annotations.Generated;
import itmo.lab6.basic.types.builders.annotations.NotNull;
import itmo.lab6.basic.types.builders.annotations.Value;
import itmo.lab6.basic.utils.generators.IdGenerator;
import itmo.lab6.basic.utils.generators.Time;
import itmo.lab6.basic.utils.parser.exceptions.BadArgumentException;
import itmo.lab6.basic.utils.parser.exceptions.ObjectParsingException;
import itmo.lab6.basic.utils.terminal.Colors;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class AbstractParser {

    protected Scanner scanner;

    public AbstractParser(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    /**
     * This method reads object of type T from console.
     * It uses builder pattern to create object. It reads all fields of the object
     * and creates object using builder.
     * <p>
     * Also, it uses annotations to validate input data.
     * <p>List of annotations: {@link Value}, {@link NotNull}, {@link Generated}.
     *
     * @param objectType Type of object to read.
     * @param <T>        Generic type of object to read.
     * @return Object of type T.
     */
    abstract <T> @Nullable T readObject(Class<T> objectType);


    /**
     * This method checks if value of field is valid.
     * <p>
     * If field is annotated with {@link Value} then it checks if value is in range.
     *
     * @param field field is used to get annotation
     * @param value value to check
     * @param <T>   type of value
     * @throws IllegalArgumentException if number is not valid
     */
    protected <T extends Number> void checkNumber(Field field, T value) throws IllegalArgumentException {
        Value valueAnnotation = null;
        if (field.isAnnotationPresent(Value.class)) {
            valueAnnotation = field.getAnnotation(Value.class);
        }
        if (valueAnnotation != null) {
            if (value.doubleValue() <= valueAnnotation.min()) {
                throw new IllegalArgumentException("%sValue must be greater than %s%s".formatted(Colors.AsciiRed, valueAnnotation.min(), Colors.AsciiReset));
            }
            if (value.doubleValue() >= valueAnnotation.max()) {
                throw new IllegalArgumentException("%sValue must be less than %s%s".formatted(Colors.AsciiRed, valueAnnotation.max(), Colors.AsciiReset));
            }
        }
    }

    /**
     * Checks if string is valid. If string is empty and field is annotated with {@link NotNull} then it will return false.
     * <p>
     * Otherwise, it will return true.
     *
     * @param field field is needed to get annotations from it
     * @param value value to check
     * @throws IllegalArgumentException if string is not valid
     */
    protected static void checkString(Field field, String value) throws IllegalArgumentException {
        if (field.isAnnotationPresent(NotNull.class) && value.isEmpty()) {
            throw new IllegalArgumentException("Value must not be null");
        }
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
    protected static <T> T generateValueByType(Field field) {
        return (T) switch (field.getType().getSimpleName()) {
            case "Integer", "int" -> IdGenerator.generateIntId();
            case "Long", "long" -> IdGenerator.generateLongId();
            case "LocalDateTime" -> Time.getTime();
            case "ZonedDateTime" -> Time.getZonedDateTime();
            default -> null;
        };
    }

    /**
     * This method sets value to field.
     *
     * @param field  field to set value
     * @param object object to set value
     * @param value  value to set
     * @throws InputMismatchException if value has wrong type
     */
    protected static void setValueToField(Field field, Object object, Object value) throws InputMismatchException {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException ignored) {
            // guess it won't happen in our case
        } catch (InputMismatchException e) {
            throw new InputMismatchException("%sWrong input%s%s".formatted(Colors.AsciiRed, e.getMessage(), Colors.AsciiReset));
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
    protected static Enum<?> stringToEnum(Field field, String value) {
        return (Enum<?>) Enum.valueOf((Class<Enum>) field.getType(), value);
    }


    /**
     * @param field   field to write
     * @param builder builder object
     * @throws BadArgumentException if field's value is not valid
     */
    protected void readString(Field field, Builder builder) throws BadArgumentException {
        String value = null;
        try {
            value = scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No line found");
        }
        try {
            checkString(field, value); // Throws an IllegalArgumentException
            if (value.equals("")) {
                value = null;
            }
            setValueToField(field, builder, value);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        }
    }

    /**
     * @param numType number's type name
     * @param field   field to write
     * @param builder builder object
     * @throws BadArgumentException if field's value is not valid
     */
    protected void readNumber(String numType, Field field, Builder builder) throws BadArgumentException {
        Number value;
        try {
            value = NumberFormat.getInstance().parse(scanner.nextLine());
        } catch (ParseException e) {
            throw new BadArgumentException("Invalid number format");
        }
        value = switch (numType) {
            case "Integer" -> value.intValue();
            case "Long" -> value.longValue();
            case "Double" -> value.doubleValue();
            case "Float" -> value.floatValue();
            default -> value;
        };
        try {
            checkNumber(field, value); // Throws an IllegalArgumentException
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        }
        setValueToField(field, builder, value);
    }

    /**
     * @param field   field to write
     * @param builder builder object
     * @throws BadArgumentException if field's value is not valid
     */
    protected void readDate(Field field, Builder builder) throws BadArgumentException {
        Date value;
        try {
            value = new SimpleDateFormat("dd.MM.yyyy").parse(scanner.nextLine());
        } catch (ParseException e) {
            throw new BadArgumentException("Invalid date format for the field: " + field.getName());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No line found");
        }
        if (value != null) {
            setValueToField(field, builder, value);
        }
    }

    protected void readEnum(Field field, Builder builder) throws ObjectParsingException {
        String value;
        try {
            value = scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("");
        }
        try {
            setValueToField(field, builder, stringToEnum(field, value));
        } catch (IllegalArgumentException e) {
            System.err.println("Enum value is not valid");
            throw new ObjectParsingException(field.getType());
        }
    }
}
