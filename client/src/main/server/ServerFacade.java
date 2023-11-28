package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import requests.*;
import responses.*;
import serialization.ChessBoardAdapter;
import serialization.ChessGameAdapter;
import serialization.ChessPieceAdapter;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;


public class ServerFacade {
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        String url = "http://localhost:8080/user";
        String method = "POST";
        String body = new Gson().toJson(registerRequest);
        return sendRequest(url, method, body, RegisterResponse.class, null);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        String url = "http://localhost:8080/session";
        String method = "POST";
        String body = new Gson().toJson(loginRequest);
        return sendRequest(url, method, body, LoginResponse.class, null);
    }

    public CreateGameResponse createGame(CreateGameRequest createGameRequest, String authToken) throws Exception {
        String url = "http://localhost:8080/game";
        String method = "POST";
        String body = new Gson().toJson(createGameRequest);
        return sendRequest(url, method, body, CreateGameResponse.class, authToken);
    }

    public ListGamesResponse listGames(String authToken) throws Exception {
        String url = "http://localhost:8080/game";
        String method = "GET";
        return sendRequest(url, method, null, ListGamesResponse.class, authToken);
    }

    public JoinGameResponse joinGame(JoinGameRequest joinGameRequest, String authToken) throws Exception {
        String url = "http://localhost:8080/game";
        String method = "PUT";
        String body = new Gson().toJson(joinGameRequest);
        return sendRequest(url, method, body, JoinGameResponse.class, authToken);
    }

    public LogoutResponse logout(String authToken) throws Exception {
        String url = "http://localhost:8080/session";
        String method = "DELETE";
        return sendRequest(url, method, null, LogoutResponse.class, authToken);
    }

    private static <T> T sendRequest(String url, String method, String body, Class<T> responseClass, String authToken) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
        if (body != null) {
            http.addRequestProperty("Content-Type", "application/json; utf-8");
            http.addRequestProperty("Accept", "application/json");
        }
        if (authToken != null) {
            http.addRequestProperty("authorization", authToken);
        }
        if (body != null) {
            writeRequestBody(body, http);
        }
        http.connect();
        System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return receiveResponse(http, responseClass);
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws Exception {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static <T> T receiveResponse(HttpURLConnection http, Class<T> responseClass) throws Exception {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();

        T responseBody = readResponseBody(http, responseClass);
        System.out.printf("= Response =========\n[%d] %s\n\n%s\n\n", statusCode, statusMessage, responseBody);
        return responseBody;
    }

    private static <T> T readResponseBody(HttpURLConnection http, Class<T> responseClass) throws Exception {
        T responseBody;
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(chess.Game.class, new ChessGameAdapter());
            builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
            builder.registerTypeAdapter(chess.ChessPiece.class, new ChessPieceAdapter());
            Gson gson = builder.create();

            responseBody = gson.fromJson(inputStreamReader, responseClass);
        }
        return responseBody;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
