package itmo.lab6.server.response;

import java.util.Objects;

/**
 * This class is used to represent server response.
 */
public class Response {
    private final String responseMessage;
    private final ResponseType responseType;
    private final Object[] objects;

    public Response(String responseMessage, ResponseType responseType) {
        this.responseMessage = responseMessage;
        this.responseType = responseType;
        this.objects = null;
    }

    public Response(Object[] objects, ResponseType responseType) {
        this.responseMessage = null;
        this.responseType = responseType;
        this.objects = objects;
    }

    public String getMessage() {
        if (getType() == ResponseType.SUCCESS) {
            return Color.PURPLE + responseMessage + Color.RESET;
        }
        if (getType() == ResponseType.ERROR) {
            return Color.RED + responseMessage + Color.RESET;
        }
        return responseMessage;
    }

    public ResponseType getType() {
        return responseType;
    }
}
