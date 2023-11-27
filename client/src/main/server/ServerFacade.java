package server;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;


public class ServerFacade {
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        // this needs to call sendRequest and receive response
        String url = "http://localhost:8080/user";
        String method = "POST";
        String body = new Gson().toJson(registerRequest);
        System.out.println(body);

        HttpURLConnection http = sendRequest(url, method, body);

//        HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
//
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/json; utf-8");
//        connection.setRequestProperty("Accept", "application/json");
//        connection.setDoOutput(true);
//
//        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}",
//                username, password, email);
//        // connection.connect()
//
//        OutputStream outputStream = connection.getOutputStream();
//        // create the header and body of the request
//        // get the response and do stuff with that
        return null;
    }

    private static HttpURLConnection sendRequest(String url, String method, String body) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
        writeRequestBody(body, http);
        http.connect();
        System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return http;
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws Exception {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static void receiveResponse(HttpURLConnection http) throws Exception {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();

        Object responseBody = readResponseBody(http);
        System.out.printf("= Response =========\n[%d] %s\n\n%s\n\n", statusCode, statusMessage, responseBody);
    }

    private static Object readResponseBody(HttpURLConnection http) throws Exception {
        Object responseBody = "";
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            responseBody = new Gson().fromJson(inputStreamReader, Map.class);
        }
        return responseBody;
    }
}
