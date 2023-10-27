package handlers;

import com.google.gson.Gson;
import spark.*;

import java.util.Scanner;

public class Handler {
    public static <T> T makeObj(Request request, Class<T> someClass) throws RuntimeException {
        var body = new Gson().fromJson(request.body(), someClass);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

    public static String makeJson(Object someResponse) throws RuntimeException {
        String body = new Gson().toJson(someResponse);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

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

    public static String getHeader(Request r) {
        String header = r.headers("authorization");
        if (header == null) {
            throw new RuntimeException("there is no header");
        }
        return header;
    }
}
