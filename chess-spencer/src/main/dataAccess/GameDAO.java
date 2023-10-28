package dataAccess;

import chess.ChessGame;
import models.Game;

import java.util.HashSet;

/**
 * The class provides methods to access and manipulate Game objects in the data store.
 */
public class GameDAO {

    /**
     * A hash set of game models
     */
    private static HashSet<Game> games = new HashSet<>();


    /**
     * Returns the Game associated with a game ID
     * @param id The game ID
     * @return The Game associated with a game ID if the game exists, otherwise return null
     */
    public Game getGameByID(int id) {
        for (Game game : games) {
            if (game.getGameID() == id) {
                return game;
            }
        }
        return null;
    }

    /**
     * Adds a game to the data store
     * @param game The game that needs to be added to the data store
     * @throws DataAccessException When the game already exists in the data store
     */
    public void addGame(Game game) throws DataAccessException {
        games.add(game);
    }

    /**
     * Adds a username to a given team color
     * @param teamColor The team color that is receiving the user
     * @param username The username of the user that is claiming a spot
     * @throws DataAccessException When there is already a user in the teamColor spot
     */
    public void claimSpot(ChessGame.TeamColor teamColor, String username, int gameID) throws DataAccessException {
        Game game = getGameByID(gameID);

        // Game not found
        if (game == null) {
            throw new DataAccessException("Error 400 bad request");
        }

        // Join as a spectator
        if (teamColor == null) {
            return;
        }

        // Join as a player
        switch (teamColor) {
            // Join team white
            case WHITE -> {
                if (game.getWhiteUsername() == null) {
                    game.setWhiteUsername(username);
                } else {
                    throw new DataAccessException("Error 403 already taken");
                }
            }
            // Join team black
            case BLACK -> {
                if (game.getBlackUsername() == null) {
                    game.setBlackUsername(username);
                } else {
                    throw new DataAccessException("Error 403 already taken");
                }
            }
        }
    }

    /**
     * Clears all the Games in the database.
     */
    public void clear() {
        games = new HashSet<>();
    }

    /**
     * Returns the hash set of games
     * @return The hash set of games
     */
    public HashSet<Game> getGames() {
        return games;
    }
}
