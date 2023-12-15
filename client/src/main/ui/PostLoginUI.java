package ui;

import chess.ChessGame;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import server.ServerFacade;
import websockets.WebSocketFacade;

import java.util.Scanner;

public class PostLoginUI {
    public static final ServerFacade server = new ServerFacade();
    public String authToken;
    public static final WebSocketFacade ws;

    static {
        try {
            ws = new WebSocketFacade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
        String helpString = """
                           create <NAME> - a game
                           list - games
                           join <ID> [WHITE|BLACK|<empty>] - a games
                           observe <ID> - a game
                           logout - when you are done
                           quit - playing chess
                           help - with possible commands
                        """;
        String curr = "[LOGGED_IN] >>> ";

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
                    // list all the created games
                    listGames();
                    break;
                case "join":
                    // join a previously created game as a player
                    int id = scanner.nextInt();
                    String teamColorAsString = scanner.next();
                    ChessGame.TeamColor teamColor = switch (teamColorAsString.toLowerCase()) {
                        case "white" -> ChessGame.TeamColor.WHITE;
                        case "black" -> ChessGame.TeamColor.BLACK;
                        default -> null;
                    };
                    joinGame(id, teamColor);
                    break;
                case "observe":
                    // join the game as a spectator
                    int gameID = scanner.nextInt();
                    observeGame(gameID);
                    break;
                case "logout":
                    // log out the logged-in user
                    logout();
                    break label;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    private void createGame(String gameName) {
        CreateGameRequest createGameRequest = new CreateGameRequest(gameName);
        try {
            CreateGameResponse createGameResponse = server.createGame(createGameRequest, authToken);
            System.out.printf("Success: %s game has been created with the gameID as %d\n", gameName, createGameResponse.getGameID());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private void listGames() {
        try {
            ListGamesResponse listGamesResponse = server.listGames(authToken);
            System.out.println("Success: Here is the list of all the existing games:");
            for (models.Game game : listGamesResponse.getGames()) {
                System.out.printf("- %s #%d White: %s Black: %s\n", game.getGameName(), game.getGameID(), game.getWhiteUsername(), game.getBlackUsername());
            }
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private void joinGame(int gameID, ChessGame.TeamColor teamColor) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(teamColor, gameID);
            try {
                server.joinGame(joinGameRequest, authToken);
                ws.joinPlayer(authToken, gameID, teamColor);
                System.out.printf("Success: You've joined game #%d as a player. Good luck!\n", gameID);
                GameplayUI gameplayUI = new GameplayUI();
                gameplayUI.joinGame(authToken, gameID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void observeGame(int gameID) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(null, gameID);
        try {
            server.joinGame(joinGameRequest, authToken);
            // TODO call on and create ws.joinObserver()
            System.out.printf("Success: You've joined game #%d as an observer.\n", gameID);
            GameplayUI gameplayUI = new GameplayUI();
            gameplayUI.joinGame(authToken, gameID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void logout() {
        try {
            server.logout(authToken);
            System.out.println("Success: You have been logged out\n");
            PreLoginUI preLoginUI = new PreLoginUI();
            preLoginUI.help();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
