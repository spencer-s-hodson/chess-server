package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.Game;
import serialization.SerializationHelper;

import java.sql.*;
import java.util.HashSet;

import static serialization.SerializationHelper.*;

/**
 * The class provides methods to access and manipulate Game objects in the data store.
 */
public class GameDAO {
    /**
     * A hash set of game models
     */
    private static final String CLEAR = "DELETE FROM games";
    private static final String FIND = "SELECT * FROM games where gameID = ?";
    private static final String FIND_ALL = "SELECT * FROM games";
    private static final String INSERT = "INSERT into games (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE games SET whiteUsername = ?, blackUsername = ? WHERE gameID = ?";
    private static final Database myDatabase = new Database();

    public GameDAO() throws DataAccessException {
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        try (Connection connection = myDatabase.getConnection()) {
            connection.setCatalog(Database.DB_NAME);
            String createTable = """
            CREATE TABLE IF NOT EXISTS games (
                gameID INT NOT NULL AUTO_INCREMENT,
                whiteUsername VARCHAR(255),
                blackUsername VARCHAR(255),
                gameName VARCHAR(255) NOT NULL,
                game longtext NOT NULL,
                PRIMARY KEY (gameID)
            )""";

            // Create the table if it doesn't exist yet
            PreparedStatement createTableStatement = connection.prepareStatement(createTable);
            createTableStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    /**
     * Returns the Game associated with a game ID
     * @param id The game ID
     * @return The Game associated with a game ID if the game exists, otherwise return null
     */
    public models.Game findGame(int id) throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int gameID = resultSet.getInt("gameID");
                String whiteUsername = resultSet.getString("whiteUsername");
                String blackUsername = resultSet.getString("blackUsername");
                String gameName = resultSet.getString("gameName");
                String gameData = resultSet.getString("game");

                // Deserialize the game data back into a Game object
                chess.Game game = SerializationHelper.deserializeChessGame(gameData);
                return new models.Game(gameID, whiteUsername, blackUsername, gameName, game);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Adds a game to the data store
     * @param game The game that needs to be added to the data store
     * @throws DataAccessException When the game already exists in the data store
     */
    public Integer addGame(models.Game game) throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, game.getWhiteUsername());
            preparedStatement.setString(2, game.getBlackUsername());
            preparedStatement.setString(3, game.getGameName());
            preparedStatement.setString(4, serializeChessGame(game.getGame()));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Adds a username to a given team color
     * @param teamColor The team color that is receiving the user
     * @param username The username of the user that is claiming a spot
     * @throws DataAccessException When there is already a user in the teamColor spot
     */
    public void claimSpot(ChessGame.TeamColor teamColor, String username, int gameID) throws DataAccessException {
        models.Game game = findGame(gameID);
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
        updatePlayers(gameID, game);
    }

    private void updatePlayers(int gameID, Game game) throws DataAccessException {
        Connection conn = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE);
            preparedStatement.setString(1, game.getWhiteUsername());
            preparedStatement.setString(2, game.getBlackUsername());
            preparedStatement.setInt(3, gameID);
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(conn);
        }
    }

    /**
     * Clears all the Games in the database.
     */
    public void clear() throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement clearStatement = connection.prepareStatement(CLEAR);
            clearStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Returns the hash set of games
     * @return The hash set of games
     */
    public HashSet<Game> findAllGames() throws DataAccessException {
        HashSet<Game> games = new HashSet<>();
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gameID = resultSet.getInt("gameID");
                String whiteUsername = resultSet.getString("whiteUsername");
                String blackUsername = resultSet.getString("blackUsername");
                String gameName = resultSet.getString("gameName");
                String gameData = resultSet.getString("game");
                // Deserialize the game data back into a Game object
                chess.Game game = SerializationHelper.deserializeChessGame(gameData);
                games.add(new models.Game(gameID, whiteUsername, blackUsername, gameName, game));
            }
            return games;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    public void updateGame(Game game) throws DataAccessException {
        String sql = "UPDATE Games SET whiteUsername=?, blackUsername=?, gameName=?, game=? WHERE gameName=?;";
        Connection connection = myDatabase.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, game.getWhiteUsername());
            stmt.setString(2, game.getBlackUsername());
            stmt.setString(3, game.getGameName());
            stmt.setString(4, new Gson().toJson(game.getGame()));
            stmt.setString(5, game.getGameName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while updating the game in the database.");
        }
    }
}
