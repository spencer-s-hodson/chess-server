package passoffTests.serverTests;


import chess.ChessGame;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.CreateGameService;
import services.JoinGameService;
import services.LoginService;
import services.RegisterService;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.CreateGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;

public class JoinGameServiceTest {
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
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
    private static final CreateGameService createGameService = new CreateGameService();
    private static final JoinGameService joinGameService = new JoinGameService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() throws DataAccessException {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(gameDAO::clear, "User DAO did not clear successfully");

        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty(), "Game DAO is not empty");
        Assertions.assertTrue(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty(), "Game DAO is not empty");
        Assertions.assertTrue(gameDAO.findAllGames().isEmpty(), "Game DAO is not empty");
    }

    @Test
    @DisplayName("Successfully Join Game")
    public void joinGame() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("test game");
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, loginResponse.getAuthToken());

        JoinGameRequest joinGameAsWhite = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID());

        joinGameService.joinGame(joinGameAsWhite, loginResponse.getAuthToken());
        Assertions.assertNotNull(Assertions.assertDoesNotThrow(() -> gameDAO.findGame(createGameResponse.getGameID()).getWhiteUsername()));
    }

    @Test
    @DisplayName("Unsuccessfully Join Game")
    public void joinGameFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("test game");
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, loginResponse.getAuthToken());

        JoinGameRequest joinGameAsWhite = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID());

        joinGameService.joinGame(joinGameAsWhite, "some invalid auth token");
        Assertions.assertNull(Assertions.assertDoesNotThrow(() -> gameDAO.findGame(createGameResponse.getGameID()).getWhiteUsername()));
    }
}
