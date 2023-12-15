package dataAccess;

import models.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * The class provides methods to access and manipulate AuthToken objects in the data store.
 */
public class AuthDAO {
    /** A hash set of auth token models */
    private static final String INSERT = "INSERT into authTokens (authToken, username) VALUES (?,?)";
    private static final String CLEAR = "DELETE FROM authTokens";
    private static final String DELETE = "DELETE FROM authTokens WHERE authToken = ?";
    private static final String FIND = "SELECT * FROM authTokens WHERE authToken = ?";
    private static final String FIND_ALL = "SELECT * FROM authTokens";
    private static final Database myDatabase = new Database();

    public AuthDAO() throws DataAccessException {
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        try (Connection connection = myDatabase.getConnection()) {
            connection.setCatalog(Database.DB_NAME);
            String createTable = """
            CREATE TABLE IF NOT EXISTS authTokens (
                authToken VARCHAR(255) NOT NULL UNIQUE,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (authToken)
            )""";

            // Create the table if it doesn't exist yet
            PreparedStatement createTableStatement = connection.prepareStatement(createTable);
            createTableStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    /**
     * Adds an AuthToken to the data store
     * @param authToken The AuthToken to add
     * @throws DataAccessException when the AuthToken already exists in the data store
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, authToken.getAuthToken());
            preparedStatement.setString(2, authToken.getUsername());
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Removes an AuthToken from the data store
     * @param authToken The AuthToken to remove
     * @throws DataAccessException when the AuthToken doesn't exist in the data store
     */
    public void removeAuthToken(AuthToken authToken) throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement clearStatement = connection.prepareStatement(DELETE);
            clearStatement.setString(1, authToken.getAuthToken());
            clearStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());

        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Finds an AuthToken in the data store
     * @param authToken The AuthToken to find
     * @return AuthToken object if found, otherwise return false
     */
    public AuthToken findAuthToken(String authToken) throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND);
            preparedStatement.setString(1, authToken);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new AuthToken(resultSet.getString("authToken"), resultSet.getString("username"));
            }
            throw new DataAccessException("Error 401 unauthorized");
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Clears all the AuthTokens in the database.
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
     * Returns a hash set of auth tokens
     * @return A hash set of auth tokens
     */
    public HashSet<AuthToken> findAllAuthTokens() throws DataAccessException {
        HashSet<AuthToken> authTokens = new HashSet<>();
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AuthToken authTokenToAdd = new AuthToken(resultSet.getString("authToken"), resultSet.getString("username"));
                authTokens.add(authTokenToAdd);
            }
            return authTokens;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }
}
