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
    private static final ClearApplicationService clearApplicationService = new ClearApplicationService();

    @BeforeEach
    public void setup() throws DataAccessException {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "Auth DAO did not clear successfully");
        Assertions.assertDoesNotThrow(gameDAO::clear, "Game DAO did not clear successfully");

        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty(), "Auth DAO is not empty");
        Assertions.assertTrue(gameDAO.findAllGames().isEmpty(), "Game DAO is not empty");
    }

    @Test
    @DisplayName("Clear Data")
    public void clearData() throws DataAccessException {
        User testUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(testUser), "User not added");

        AuthToken testAuthToken = new AuthToken("username", "testAuthToken");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(testAuthToken), "Auth token not added");

        Game testGame = new Game("game name", new chess.Game());
        Assertions.assertDoesNotThrow(() ->gameDAO.addGame(testGame), "Game not added");

        Assertions.assertFalse(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty(), "User DAO is empty");
        Assertions.assertFalse(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty(), "Auth DAO is empty");
        Assertions.assertFalse(gameDAO.findAllGames().isEmpty(), "Game DAO is empty");

        Assertions.assertDoesNotThrow(clearApplicationService::clearApplication);

        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty(), "Auth DAO is not empty");
        Assertions.assertTrue(gameDAO.findAllGames().isEmpty(), "Game DAO is not empty");
    }
}
