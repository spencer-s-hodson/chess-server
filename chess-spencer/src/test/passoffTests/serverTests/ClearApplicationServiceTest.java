package passoffTests.serverTests;

import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.ClearApplicationService;


public class ClearApplicationServiceTest {
    private static final UserDAO userDAO = new UserDAO();
    private static final AuthDAO authDAO = new AuthDAO();
    private static final GameDAO gameDAO = new GameDAO();
    private static final ClearApplicationService clearApplicationService = new ClearApplicationService();

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "Auth DAO did not clear successfully");
        Assertions.assertDoesNotThrow(gameDAO::clear, "Game DAO did not clear successfully");

        Assertions.assertTrue(userDAO.getUsers().isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(authDAO.getAuthTokens().isEmpty(), "Auth DAO is not empty");
        Assertions.assertTrue(gameDAO.getGames().isEmpty(), "Game DAO is not empty");
    }

    @Test
    @DisplayName("Clear Data")
    public void clearData() {
        User testUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(testUser), "User not added");

        AuthToken testAuthToken = new AuthToken("username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(testAuthToken), "Auth token not added");

        Game testGame = new Game("game name");
        Assertions.assertDoesNotThrow(() ->gameDAO.addGame(testGame), "Game not added");

        Assertions.assertFalse(userDAO.getUsers().isEmpty(), "User DAO is empty");
        Assertions.assertFalse(authDAO.getAuthTokens().isEmpty(), "Auth DAO is empty");
        Assertions.assertFalse(gameDAO.getGames().isEmpty(), "Game DAO is empty");

        Assertions.assertDoesNotThrow(clearApplicationService::clearApplication);

        Assertions.assertTrue(userDAO.getUsers().isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(authDAO.getAuthTokens().isEmpty(), "Auth DAO is not empty");
        Assertions.assertTrue(gameDAO.getGames().isEmpty(), "Game DAO is not empty");
    }
}
