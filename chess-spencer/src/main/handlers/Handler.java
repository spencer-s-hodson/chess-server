package handlers;

import com.google.gson.Gson;
import spark.*;

import java.util.Scanner;

/**
 * This class is extended by all other handlers. Handles Spark requests and responses.
 */
public class Handler {

    /**
     * Converts a spark request into the desired service request
     * @param request The Spark request
     * @param someClass The type of request to convert to
     * @return The new service request
     * @param <T> The type of service request
     * @throws RuntimeException If the Spark request has no body
     */
    public static <T> T makeObj(Request request, Class<T> someClass) throws RuntimeException {
        var body = new Gson().fromJson(request.body(), someClass);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

    /**
     * Converts a service response into a Spark response (JSON string)
     * @param someResponse The service response
     * @return The Spark response (JSON string)
     * @throws RuntimeException If the service response has no body
     */
    public static String makeJson(Object someResponse) throws RuntimeException {
        String body = new Gson().toJson(someResponse);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

    /**
     * Parses the error message containing the status
     * @param message The message that gets parsed
     * @return The status found in the message
     */
    public static int getStatus(String message) {
        Scanner scanner = new Scanner(message);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            scanner.next();
        }
        return 200;
    }

    /**
     * Gets the header from the Spark request
     * @param r The Spark request
     * @return The header spark request
     * @throws RuntimeException If the Spark request doesn't have a header
     */
    public static String getHeader(Request r) throws RuntimeException {
        String header = r.headers("authorization");
        if (header == null) {
            throw new RuntimeException("There is no header.");
        }
        return header;
    }
}
