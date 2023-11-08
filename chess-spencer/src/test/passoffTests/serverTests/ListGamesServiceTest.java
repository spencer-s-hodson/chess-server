package passoffTests.serverTests;

import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.ListGamesService;
import services.LoginService;
import services.RegisterService;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.responses.ListGamesResponse;
import services.responses.LoginResponse;
import services.responses.RegisterResponse;

public class ListGamesServiceTest {
    private static final GameDAO gameDAO;

    static {
        try {
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
    private static final ListGamesService listGamesService = new ListGamesService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() throws DataAccessException {
        Assertions.assertDoesNotThrow(gameDAO::clear, "User DAO did not clear successfully");
        Assertions.assertTrue(gameDAO.findAllGames().isEmpty(), "Game DAO is not empty");
    }

    @Test
    @DisplayName("Successfully List Games")
    public void listGames() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);

        models.Game testGame1 = new Game("test game 1", new chess.Game());
        models.Game testGame2 = new Game("test game 2", new chess.Game());

        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame1));
        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame2));

        Assertions.assertDoesNotThrow(() -> listGamesService.listGames(loginResponse.getAuthToken()));

        Assertions.assertEquals(2, gameDAO.findAllGames().size());
    }

    @Test
    @DisplayName("Unsuccessfully List Games")
    public void listGamesFail() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerResponse.getUsername(), registerRequest.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);

        Game testGame1 = new Game("test game 1", new chess.Game());
        Game testGame2 = new Game("test game 2", new chess.Game());

        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame1));
        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame2));

        ListGamesResponse listGamesResponse = Assertions.assertDoesNotThrow(() -> listGamesService.listGames("Some invalid auth token"));

        Assertions.assertNotEquals(listGamesResponse.getGames(), gameDAO.findAllGames());
    }
}
