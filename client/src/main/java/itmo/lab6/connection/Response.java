package itmo.lab6.connection;

/**
 * This class is used to represent server response.
 *
 */
public class Response {
    private final String responseMessage;
    private final ResponseType responseType;

    public Response(String responseMessage, ResponseType responseType) {
        this.responseMessage = responseMessage;
        this.responseType = responseType;
    }

    public String getMessage() {
        return responseMessage;
    }

    public ResponseType getType() {
        return responseType;
    }
}
