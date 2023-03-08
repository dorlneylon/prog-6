package itmo.kxrxh.lab5.utils.terminal;

import java.lang.reflect.Field;

public final class Printer {
    public static <T> void print(T object, int indentLevel) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                // System.out.print(field.getName());
                System.out.printf("%s%s%s%s".formatted(Colors.AsciiPurple, getIndentString(indentLevel), field.getName(), Colors.AsciiReset));
                System.out.print(": ");
                if (field.getType().isEnum()) {
                    System.out.println(field.get(object));
                    continue;
                }
                switch (field.getType().getSimpleName()) {
                    case "String", "Integer", "Double", "Boolean", "int", "long", "float", "double", "LocalDateTime" ->
                            System.out.println(field.get(object));
                    default -> {
                        System.out.print("\n");
                        print(field.get(object), indentLevel + 1);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getIndentString(int indentLevel) {
        return new String(new char[indentLevel * 4]).replace("\0", " ");
    }

    public static <T> void print(T object) {
        print(object, 0);
    }
}
