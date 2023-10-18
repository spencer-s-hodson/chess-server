package dataAccess;

import chess.ChessGame;
import models.Game;

/**
 * The class provides methods to access and manipulate Game objects in the data store.
 */
public class GameDAO {
    /**
     * Returns the Game associated with a game ID
     * @param id The game ID
     * @return The Game associated with a game ID
     * @throws DataAccessException When there is no game with the provided gameID
     */
    public Game getGameByID(int id) throws DataAccessException {
        // TODO
        return null;
    }

    /**
     * Updates the logic within the game
     * @param game The game that needs to be updated
     * @throws DataAccessException When the game doesn't exist in the data store
     */
    public void updateGame(Game game) throws DataAccessException {
        // TODO
    }

    /**
     * Adds a game to the data store
     * @param game The game that needs to be added to the data store
     * @throws DataAccessException When the game already exists in the data store
     */
    public void addGame(Game game) throws DataAccessException {
        // TODO
    }

    /**
     * Removes a game from the data store
     * @param game The game that needs to be removed from the data store
     * @throws DataAccessException When the game doesn't exist in the data store
     */
    public void removeGame(Game game) throws DataAccessException {
        // TODO
    }

    /**
     * Adds a username to a given team color
     * @param teamColor The team color that is receiving the user
     * @param username The username of the user that is claiming a spot
     * @throws DataAccessException When there is already a user in the teamColor spot
     */
    public void claimSpot(ChessGame.TeamColor teamColor, String username) throws DataAccessException {
        // TODO
    }
}
