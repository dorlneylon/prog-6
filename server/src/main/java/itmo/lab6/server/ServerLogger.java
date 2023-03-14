package itmo.lab6.server;

import java.util.logging.Logger;

public class ServerLogger {
    private static final Logger logger = Logger.getLogger("ServerLogger");
//    private static final FileHandler fileHandler;
//
//    static {
//        try {
//            fileHandler = new FileHandler("server.log");
//            logger.addHandler(fileHandler);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static Logger getLogger() {
        return logger;
    }
}
