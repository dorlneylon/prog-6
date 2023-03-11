package itmo.lab6.commands.implemented;

import itmo.lab6.basic.auxiliary.Convertible;
import itmo.lab6.basic.baseclasses.Coordinates;
import itmo.lab6.basic.baseclasses.Location;
import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.basic.baseclasses.Person;
import itmo.lab6.basic.baseenums.MovieGenre;
import itmo.lab6.basic.baseenums.Color;
import itmo.lab6.basic.baseenums.MpaaRating;
import itmo.lab6.commands.CommandHandler;
import itmo.lab6.server.UdpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static itmo.lab6.basic.baseclasses.Movie.blcr;
import static itmo.lab6.basic.baseclasses.Movie.whcr;
import static itmo.lab6.server.UdpServer.collection;

public class InsertCommand extends AbstractCommand {
    private Movie arg;
    CommandHandler handler;
    private static final String rrcr = "\u001B[31m";

    public InsertCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() throws IOException {
        Long k = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            k = Long.parseLong(handler.getCommand().split(" ")[1]);
            if (collection.containsKey(k)) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + "Key already exists. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                return;
            }
            else {
                this.arg = check();
                try {
                    this.arg.setId(k);
                } catch (NumberFormatException e) {
                    handler.getChannel().send(ByteBuffer.wrap((rrcr + "Illegal key argument." + whcr).getBytes()), handler.getAddress());
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        } catch (NumberFormatException e) {
            handler.getChannel().send(ByteBuffer.wrap((rrcr + "Illegal key argument." + whcr).getBytes()), handler.getAddress());
            return;
        } catch (Exception e) {
            handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            return;
        }

        try {
            if (arg == null) throw new NullPointerException();
            collection.insert(arg);
            String response = "Done!";
            buffer.clear();
            buffer.put(response.getBytes());
            buffer.flip();
            handler.getChannel().send(buffer, handler.getAddress());
        } catch (NullPointerException e) {
            String response = "Empty fields or illegal arguments detected.";
            buffer.clear();
            buffer.put(response.getBytes());
            buffer.flip();
            handler.getChannel().send(buffer, handler.getAddress());
        } catch (Exception e) {
            String response = "Error occured.";
            buffer.clear();
            buffer.put(response.getBytes());
            buffer.flip();
            handler.getChannel().send(buffer, handler.getAddress());
        }
    }

    public Movie check() throws IOException, InterruptedException {
        String name = checkName();

        Coordinates coordinates = checkCoordinates();

        long oscarsCount = checkOscarsCount();

        MovieGenre genre = checkGenre();

        MpaaRating mpaaRating = checkMpaaRating();

        Person director = checkDirector();

        try {
            return new Movie(name, coordinates, oscarsCount, genre, mpaaRating, director);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String checkName() {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                handler.getChannel().send(ByteBuffer.wrap("Enter name".getBytes()), handler.getAddress());

                while (true) {
                    buffer.clear();
                    handler.getChannel().receive(buffer);
                    if (buffer.position() > 0) {
                        String response = new String(buffer.array(), buffer.arrayOffset(), buffer.position());
                        if (!response.equals("")) return response;
                        break;
                    }
                    // Wait for some time before checking the socket again
                    Thread.sleep(100);
                }

                handler.getChannel().send(ByteBuffer.wrap((rrcr + "Empty field detected. Try calling the function again." + whcr).getBytes()), handler.getAddress());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Coordinates checkCoordinates() throws IOException {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                handler.getChannel().send(ByteBuffer.wrap("coordinates".getBytes()), handler.getAddress());
                boolean isValid = false;
                while (!isValid) {
                    buffer.clear();
                    handler.getChannel().receive(buffer);
                    if (buffer.position() > 0) {
                        String[] cs = new String(buffer.array(), buffer.arrayOffset(), buffer.position()).split(" ");
                        try {
                            float x = Float.parseFloat(cs[0]);
                            int y = Integer.parseInt(cs[1]);
                            return new Coordinates(x, y);
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            handler.getChannel().send(ByteBuffer.wrap((rrcr + "Incorrect input format. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                        }
                    }
                    // Wait for some time before checking the socket again
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private long checkOscarsCount() throws IOException {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            try {
                handler.getChannel().send(ByteBuffer.wrap("oscarsCount".getBytes()), handler.getAddress());
                boolean isValid = false;
                while (!isValid) {
                    buffer.clear();
                    if (buffer.position() > 0) {
                        String response = new String(buffer.array(), buffer.arrayOffset(), buffer.position());
                        try {
                            long oscarsCount = Long.parseLong(response);
                            return oscarsCount;
                        } catch (NumberFormatException e) {
                            handler.getChannel().send(ByteBuffer.wrap((rrcr + "Incorrect input format. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                        }
                    }
                    // Wait for some time before checking the socket again
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private MovieGenre checkGenre() throws IOException, InterruptedException {
        while (true) {
            byte[] buffer = new byte[1024];
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

            String message = "Enter genre (ACTION, COMEDY, TRAGEDY, THRILLER)";
            byteBuffer.put(message.getBytes());
            handler.getChannel().send(byteBuffer, handler.getAddress());

            boolean isValid = false;
            while (!isValid) {
                byteBuffer.clear();
                handler.getChannel().receive(byteBuffer);
                if (byteBuffer.position() > 0) {
                    String input = new String(buffer).trim();
                    MovieGenre genre = Convertible.convert(input, MovieGenre.class);
                    if (genre != null) {
                        return genre;
                    }
                    message = "Incorrect input format\n";
                    byteBuffer.clear();
                    byteBuffer.put(message.getBytes());
                    handler.getChannel().send(byteBuffer, handler.getAddress());
                }
                // Wait for some time before checking the socket again
                Thread.sleep(100);
            }
        }
    }

    private MpaaRating checkMpaaRating() throws IOException {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                handler.getChannel().send(ByteBuffer.wrap("mpaaRating".getBytes()), handler.getAddress());
                handler.getChannel().receive(buffer);
                String response = new String(buffer.array(), buffer.arrayOffset(), buffer.position());
                MpaaRating mpaaRating = Convertible.convert(response.trim(), MpaaRating.class);
                if (mpaaRating != null) {
                    return mpaaRating;
                } else {
                    handler.getChannel().send(ByteBuffer.wrap((rrcr + "Incorrect input format. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                }
            } catch (IOException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            }
        }
    }

    private int checkIntInput(String message, String errorMessage) throws IOException {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                buffer.put(message.getBytes());
                handler.getChannel().send(buffer, handler.getAddress());
                buffer.clear();
                handler.getChannel().receive(buffer);
                String input = new String(buffer.array(), StandardCharsets.UTF_8).trim();
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    handler.getChannel().send(ByteBuffer.wrap((rrcr + errorMessage + whcr).getBytes()), handler.getAddress());
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + "Incorrect input format. Try calling the function again." + whcr).getBytes()), handler.getAddress());
            } catch (IOException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            }
        }
    }

    private Person checkDirector() throws IOException {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                buffer.put("director".getBytes());
                handler.getChannel().send(buffer, handler.getAddress());
                buffer.clear();
                handler.getChannel().receive(buffer);
                String name = new String(buffer.array(), StandardCharsets.UTF_8).trim();
                if (name.equals("")) {
                    handler.getChannel().send(ByteBuffer.wrap((rrcr + "Director's name cannot be empty. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                } else {
                    java.util.Date bday = checkBirthday();
                    int height = checkIntInput("Enter director's height", "Director's height must be a positive integer greater than 0.");
                    if (height <= 0) {
                        handler.getChannel().send(ByteBuffer.wrap((rrcr + "Director's height must be a positive integer greater than 0. Try calling the function again." + whcr).getBytes()), handler.getAddress());
                    } else {
                    }
                }
            } catch (IOException e) {
                handler.getChannel().send(ByteBuffer.wrap((rrcr + e.getMessage() + whcr).getBytes()), handler.getAddress());
            }
        }
    }

    private java.util.Date checkBirthday() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        String message = "Enter birthday in yyyy-MM-dd format:";
        byteBuffer.put(message.getBytes());
        handler.getChannel().send(byteBuffer, handler.getAddress());

        byteBuffer.clear();
        handler.getChannel().receive(byteBuffer);

        String dateString = new String(byteBuffer.array(), StandardCharsets.UTF_8).trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            message = "Invalid date format. Please enter the date in yyyy-MM-dd format.";
            byteBuffer.clear();
            byteBuffer.put(message.getBytes());
            handler.getChannel().send(byteBuffer, handler.getAddress());
            return checkBirthday();
        }
    }


    private Location checkLocation() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Enter director's location (long double double - separated by spaces and dots, provided in one line)\n$ ".getBytes());
        buffer.flip();
        handler.getChannel().send(buffer, handler.getAddress());

        buffer.clear();
        handler.getChannel().receive(buffer);
        buffer.flip();

        String[] loc = new String(buffer.array(), buffer.position(), buffer.remaining()).trim().split(" ");
        try {
            return new Location(Long.parseLong(loc[0]), Double.parseDouble(loc[1]), Float.parseFloat(loc[2]));
        } catch (NumberFormatException e) {
            buffer.clear();
            buffer.put("Incorrect location format. Please enter the location in the format long double double.\n$ ".getBytes());
            buffer.flip();
            handler.getChannel().send(buffer, handler.getAddress());
            return checkLocation();
        }
    }

    private Color checkHairColor() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Enter director's haircolor (GREEN, RED, BLACK, BLUE, YELLOW)\n$ ".getBytes());
        buffer.flip();
        handler.getChannel().send(buffer, handler.getAddress());

        buffer.clear();
        handler.getChannel().receive(buffer);
        buffer.flip();

        String hairColorStr = new String(buffer.array(), buffer.position(), buffer.remaining()).trim();
        Color hairColor = Convertible.convert(hairColorStr, Color.class);

        if (hairColor == null) {
            buffer.clear();
            buffer.put("Invalid haircolor. Please enter a valid haircolor.\n$ ".getBytes());
            buffer.flip();
            handler.getChannel().send(buffer, handler.getAddress());
            return checkHairColor();
        }

        return hairColor;
    }
}
