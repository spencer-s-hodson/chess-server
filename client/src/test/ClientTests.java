import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.*;
import server.ServerFacade;

import java.util.HashSet;

public class ClientTests {
    private static final ServerFacade server = new ServerFacade();
    private static final UserDAO userDAO;
    private static final AuthDAO authDAO;
    private static final GameDAO gameDAO;

    static {
        try {
            userDAO = new UserDAO();
            authDAO = new AuthDAO();
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear);
        Assertions.assertDoesNotThrow(authDAO::clear);
        Assertions.assertDoesNotThrow(gameDAO::clear);
    }

    @Test
    @DisplayName("Register User")
    public void registerUser() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> server.register(registerRequest));
        models.User user = Assertions.assertDoesNotThrow(() -> userDAO.findUser(registerRequest.getUsername()));
        Assertions.assertEquals(user.getUsername(), registerRequest.getUsername());
    }
    
    @Test
    @DisplayName("Fail to Register User")
    public void registerUserFail() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> server.register(registerRequest));
        Assertions.assertThrows(Exception.class, () -> server.register(registerRequest));
    }

    @Test
    @DisplayName("Login")
    public void login() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        Assertions.assertDoesNotThrow(() -> server.logout(registerResponse.getAuthToken()));

        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = Assertions.assertDoesNotThrow(() -> server.login(loginRequest));
        models.AuthToken authToken = Assertions.assertDoesNotThrow(() -> authDAO.findAuthToken(loginResponse.getAuthToken()));
        Assertions.assertEquals(authToken.getAuthToken(), loginResponse.getAuthToken());
    }

    @Test
    @DisplayName("Fail to Login")
    public void failToLogin() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Assertions.assertThrows(Exception.class, () -> server.login(loginRequest));
    }

    @Test
    @DisplayName("Create Game")
    public void createGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest("Spencer");
        CreateGameResponse createGameResponse = Assertions.assertDoesNotThrow(() -> server.createGame(createGameRequest, registerResponse.getAuthToken()));

        Game game = Assertions.assertDoesNotThrow(() -> gameDAO.findGame(createGameResponse.getGameID()));
        Assertions.assertEquals(game.getGameName(), "Spencer");
    }

    @Test
    @DisplayName("Fail to Create Game")
    public void failToCreateGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest(null);
        Assertions.assertThrows(Exception.class, () -> server.createGame(createGameRequest, registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("List Games")
    public void listGames() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest("Spencer");
        Assertions.assertDoesNotThrow(() -> server.createGame(createGameRequest, registerResponse.getAuthToken()));

        HashSet<Game> games = Assertions.assertDoesNotThrow(() -> server.listGames(registerResponse.getAuthToken())).getGames();
        System.out.println(games.toString());
        Assertions.assertEquals(1, games.size());
    }

    @Test
    @DisplayName("Fail to List Games")
    public void failToListGames() {
        Assertions.assertThrows(Exception.class, () -> server.listGames("bad authToken"));
    }

    @Test
    @DisplayName("Join Game")
    public void joinGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest("Spencer");
        CreateGameResponse createGameResponse = Assertions.assertDoesNotThrow(() -> server.createGame(createGameRequest, registerResponse.getAuthToken()));

        JoinGameRequest joinGameRequestWhite = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID());
        Assertions.assertDoesNotThrow(() -> server.joinGame(joinGameRequestWhite, registerResponse.getAuthToken()));

        JoinGameRequest joinGameRequestBlack = new JoinGameRequest(ChessGame.TeamColor.BLACK, createGameResponse.getGameID());
        Assertions.assertDoesNotThrow(() -> server.joinGame(joinGameRequestBlack, registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("Fail to Join Game")
    public void failToJoinGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest("Spencer");
        CreateGameResponse createGameResponse = Assertions.assertDoesNotThrow(() -> server.createGame(createGameRequest, registerResponse.getAuthToken()));

        JoinGameRequest joinGameRequestWhite = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID());
        Assertions.assertDoesNotThrow(() -> server.joinGame(joinGameRequestWhite, registerResponse.getAuthToken()));

        Assertions.assertThrows(Exception.class, () -> server.joinGame(joinGameRequestWhite, registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("Observe")
    public void observeGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        CreateGameRequest createGameRequest = new CreateGameRequest("Spencer");
        CreateGameResponse createGameResponse = Assertions.assertDoesNotThrow(() -> server.createGame(createGameRequest, registerResponse.getAuthToken()));

        JoinGameRequest joinGameRequestSpectator = new JoinGameRequest(null, createGameResponse.getGameID());
        Assertions.assertDoesNotThrow(() -> server.joinGame(joinGameRequestSpectator, registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("Fail to Observe")
    public void failToObserveGame() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        JoinGameRequest joinGameRequestSpectator = new JoinGameRequest(null, -1);
        Assertions.assertThrows(Exception.class, () -> server.joinGame(joinGameRequestSpectator, registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("Logout")
    public void logout() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse registerResponse = Assertions.assertDoesNotThrow(() -> server.register(registerRequest));

        Assertions.assertDoesNotThrow(() -> server.logout(registerResponse.getAuthToken()));
    }

    @Test
    @DisplayName("Fail to Logout")
    public void failToLogOut() {
        Assertions.assertThrows(Exception.class, () -> server.logout("badAuthToken"));
    }
}
