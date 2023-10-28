package passoffTests.serverTests;

import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.CreateGameService;
import services.LoginService;
import services.RegisterService;
import services.requests.CreateGameRequest;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.responses.CreateGameResponse;
import services.responses.LoginResponse;
import services.responses.RegisterResponse;

public class CreateGameServiceTest {
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
    private static final UserDAO userDAO = new UserDAO();
    private static final AuthDAO authDAO = new AuthDAO();
    private static final GameDAO gameDAO = new GameDAO();
    private static final CreateGameService createGameService = new CreateGameService();
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
    @DisplayName("Successful Game Creation")
    public void createGame() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("test game");
        createGameService.createGame(createGameRequest, loginResponse.getAuthToken());

        Assertions.assertFalse(gameDAO.getGames().isEmpty());
    }

    @Test
    @DisplayName("Unsuccessful Game Creation")
    public void createGameFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        loginService.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("test game");
        createGameService.createGame(createGameRequest, "some invalid authtoken");

        Assertions.assertTrue(gameDAO.getGames().isEmpty());
    }
}
