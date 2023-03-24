package itmo.lab6.server.response;

public enum Color {
    GREEN("\u001B"), PURPLE("\u001B[35m"), RED("\u001B[31m"), RESET("\u001B[0m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public String toString() {
        return colorCode;
    }
}
