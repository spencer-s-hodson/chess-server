package ui;

import server.ServerFacade;

import java.util.Scanner;

public class PostLoginUI {
    public static final ServerFacade server = new ServerFacade();
    public void login(String curr) {
        String helpString = """
                           create <NAME> - a game
                           list - games
                           join <ID> [WHITE|BLACK|<empty>] - a games
                           observe <ID> - a game
                           logout - when you are done
                           quit - playing chess
                           help - with possible commands
                        """;

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
                    // break out of the loop to quit the program
                    break label;
                case "create":
                    // creates a new game where people can play
                    String gameName = scanner.next();
                    createGame(gameName);
                    break;
                case "list":
                    // lists all the created games
                    listGames();
                    break;
                case "join":
                    // joins a previously created game as a player
                    int id = scanner.nextInt();
                    // this isn't supposed to be a string though, right?
                    // [WHITE|BLACK|<empty>] confused
                    String teamColor = scanner.next();
                    joinGame(id, teamColor);
                    break;
                case "observe":
                    // joins the game as a spectator
                    int gameID = scanner.nextInt();
                    observeGame(gameID);
                    break;
                case "logout":
                    // logs out the logged-in user
                    logout();
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    private void createGame(String gameName) {
    }
    private void listGames() {
    }
    private void joinGame(int id, String teamColor) {
    }
    private void observeGame(int gameID) {
    }
    private void logout() {
    }
}
