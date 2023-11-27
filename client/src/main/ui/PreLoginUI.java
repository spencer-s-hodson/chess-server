package ui;

import requests.RegisterRequest;
import responses.RegisterResponse;
import server.ServerFacade;

import java.util.Scanner;

public class PreLoginUI {
    public static final ServerFacade server = new ServerFacade();
    public void help(String curr) throws Exception {
        String helpString = """
                           register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                           login <USERNAME> <PASSWORD> - to play chess
                           quit - playing chess
                           help - with possible commands
                        """;

        System.out.println(helpString);

        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println(curr);
            String response = scanner.next();
            System.out.println(response);

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
                    break;
                case "login":
                    // login the already existing user, throw an error if the user doesn't exist
                    String u = scanner.next();
                    String p = scanner.next();
                    login(u, p);
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    private void register(String username, String password, String email) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
        RegisterResponse registerResponse = server.register(registerRequest);
    }

    private void login(String username, String password) {
        // the log the user in and then move to the post login ui
    }
}
