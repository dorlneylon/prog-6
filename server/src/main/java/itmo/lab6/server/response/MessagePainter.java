package itmo.lab6.server.response;

public final class MessagePainter {
    public static String ColoredInfoMessage(String message) {
        String pattern = "^(.*?): (.*$)";
        StringBuilder resultSb = new StringBuilder();
        for (String line : message.split("\n")) {
            resultSb.append(line.replaceAll(pattern, "%s$1: %s$2".formatted(Color.PURPLE, Color.RESET))).append("\n");
        }
        return resultSb.deleteCharAt(resultSb.length() - 1).toString();
    }
}
