package itmo.lab6.server.utils.logger;

public class ServerLogger {
    public static void log(String message, LogLevel level) {
        String colorCode = switch (level) {
            case ERROR -> "\u001b[31;1m"; // Red
            case WARNING -> "\u001b[33;1m"; // Yellow
            case INFO -> "\u001b[32;1m"; // Green
        };
        System.out.printf("%s%s%s%n", colorCode, message, "\u001b[0m");
    }
}
