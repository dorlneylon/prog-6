package itmo.lab6.basic.types.builders;

public class BuilderFactory {
    public static Builder getBuilder(String className) {
        return switch (className.toLowerCase()) {
            case "coordinates" -> new CoordinatesBuilder();
            case "date" -> new DateBuilder();
            case "person" -> new DirectorBuilder();
            case "location" -> new LocationBuilder();
            case "movie" -> new MovieBuilder();
            default -> null;
        };
    }
}
