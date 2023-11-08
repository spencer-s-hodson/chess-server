package passoffTests.daoTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class GameDAOTests {
    private static final GameDAO gameDAO;

    static {
        try {
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(gameDAO::clear);
    }

    @Test
    @DisplayName("Add Game")
    public void addGame() {
        models.Game newGame = new Game("gameName", new chess.Game());
        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(newGame));
    }

    @Test
    @DisplayName("Find Game")
    public void findGame() throws DataAccessException {
        models.Game newGame = new models.Game("gameName", new chess.Game());
        int id = gameDAO.addGame(newGame);
        newGame.setGameID(id);

        models.Game theGame = Assertions.assertDoesNotThrow(() -> gameDAO.findGame(id));

        Assertions.assertNotNull(theGame);

        // Override equals
        Assertions.assertTrue(newGame.equals(theGame));
    }

    @Test
    @DisplayName("Fail to Find Game")
    public void failToFindGame() throws DataAccessException {
        models.Game newGame = new models.Game("gameName", new chess.Game());
        Assertions.assertNull(gameDAO.findGame(newGame.getGameID()));
    }

    @Test
    @DisplayName("Find All Games")
    public void findAllGames() {
        models.Game newGame = new models.Game("gameName", new chess.Game());
        int newID = Assertions.assertDoesNotThrow(() -> gameDAO.addGame(newGame));
        newGame.setGameID(newID);

        models.Game othertNewGame = new models.Game("gameName", new chess.Game());
        int otherNewID = Assertions.assertDoesNotThrow(() -> gameDAO.addGame(othertNewGame));
        newGame.setGameID(otherNewID);

        HashSet<Game> foundGames = Assertions.assertDoesNotThrow(gameDAO::findAllGames);
        Assertions.assertEquals(foundGames.size(), 2);
    }

    @Test
    @DisplayName("Find Games When Empty")
    public void findGamesWhenEmpty() {
        Assertions.assertTrue(Assertions.assertDoesNotThrow(gameDAO::findAllGames).isEmpty());
    }

    @Test
    @DisplayName("Clear Games")
    public void clearGames() {
        models.Game newGame = new models.Game("gameName", new chess.Game());
        Assertions.assertDoesNotThrow(() -> gameDAO.addGame(newGame));
        Assertions.assertDoesNotThrow(gameDAO::clear);
        Assertions.assertTrue(Assertions.assertDoesNotThrow(gameDAO::findAllGames).isEmpty());
    }

    @Test
    @DisplayName("Clear Empty Games")
    public void clearEmptyGames() {
        Assertions.assertTrue(Assertions.assertDoesNotThrow(gameDAO::findAllGames).isEmpty());
        Assertions.assertDoesNotThrow(gameDAO::clear);
        Assertions.assertTrue(Assertions.assertDoesNotThrow(gameDAO::findAllGames).isEmpty());
    }

    @Test
    @DisplayName("Update Game")
    public void updateGame() {
        models.Game testGame = new Game("gameName", new chess.Game());
        int gameID = Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame));
        testGame.setGameID(gameID);

        Assertions.assertDoesNotThrow(() -> gameDAO.claimSpot(ChessGame.TeamColor.WHITE, "newWhiteUsername", gameID));
        Assertions.assertDoesNotThrow(() -> gameDAO.claimSpot(ChessGame.TeamColor.BLACK, "newBlackUsername", gameID));
    }

    @Test
    @DisplayName("Fail Update Game")
    public void failUpdateGame() {
        models.Game testGame = new Game("gameName", new chess.Game());
        testGame.setWhiteUsername("whiteUsername");
        testGame.setBlackUsername("blackUsername");
        int gameID = Assertions.assertDoesNotThrow(() -> gameDAO.addGame(testGame));
        testGame.setGameID(gameID);

        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.claimSpot(ChessGame.TeamColor.WHITE, "newWhiteUsername", testGame.getGameID()));
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.claimSpot(ChessGame.TeamColor.BLACK, "newBlackUsername", testGame.getGameID()));
    }
}
