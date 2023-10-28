package passoffTests.serverTests;


import chess.ChessGame;
import models.Game;
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
import services.requests.CreateGameRequest;
import services.requests.JoinGameRequest;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.responses.CreateGameResponse;
import services.responses.JoinGameResponse;
import services.responses.LoginResponse;
import services.responses.RegisterResponse;

public class JoinGameServiceTest {
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
    private static final UserDAO userDAO = new UserDAO();
    private static final AuthDAO authDAO = new AuthDAO();
    private static final GameDAO gameDAO = new GameDAO();
    private static final CreateGameService createGameService = new CreateGameService();
    private static final JoinGameService joinGameService = new JoinGameService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(gameDAO::clear, "User DAO did not clear successfully");

        Assertions.assertTrue(userDAO.getUsers().isEmpty(), "Game DAO is not empty");
        Assertions.assertTrue(authDAO.getAuthTokens().isEmpty(), "Game DAO is not empty");
        Assertions.assertTrue(gameDAO.getGames().isEmpty(), "Game DAO is not empty");
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
        Assertions.assertNotNull(Assertions.assertDoesNotThrow(() -> gameDAO.getGameByID(createGameResponse.getGameID()).getWhiteUsername()));
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
        Assertions.assertNull(Assertions.assertDoesNotThrow(() -> gameDAO.getGameByID(createGameResponse.getGameID()).getWhiteUsername()));
    }
}
