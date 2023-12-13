package ui;

import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import server.ServerFacade;
import websockets.WebSocketFacade;

import java.util.Scanner;

public class PreLoginUI {
    public static final ServerFacade server = new ServerFacade();
    public static final WebSocketFacade ws;

    static {
        try {
            ws = new WebSocketFacade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void help() {
        String helpString = """
                           register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                           login <USERNAME> <PASSWORD> - to play chess
                           quit - playing chess
                           help - with possible commands
                        """;

        String curr = "[LOGGED_OUT] >>> ";
        System.out.println(helpString);

        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println(curr);
            String response = scanner.next();

            switch (response.toLowerCase()) {
                case "help":
                    // print out the help options again
                    System.out.println(helpString);
                    break;
                case "quit":
                    // break out of the loop
                    break label;
                case "register":
                    // register with the information, and then log the user in
                    String username = scanner.next();
                    String password = scanner.next();
                    String email = scanner.next();
                    register(username, password, email);
                    break label;
                case "login":
                    // login the already existing user, throw an error if the user doesn't exist
                    String u = scanner.next();
                    String p = scanner.next();
                    login(u, p);
                    break label;
                default:
                    // invalid command
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }

    private void register(String username, String password, String email) {
        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
        try {
            RegisterResponse registerResponse = server.register(registerRequest);
            System.out.printf("Success: %s has been successfully registered and logged in.\n", registerResponse.getUsername());

            PostLoginUI postLoginUI = new PostLoginUI();
            postLoginUI.setAuthToken(registerResponse.getAuthToken());
            postLoginUI.login();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        try {
            LoginResponse loginResponse = server.login(loginRequest);
            System.out.printf("Success: %s has been successfully logged in\n", loginResponse.getUsername());

            PostLoginUI postLoginUI = new PostLoginUI();
            postLoginUI.setAuthToken(loginResponse.getAuthToken());
            postLoginUI.login();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
