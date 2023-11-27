package dataAccess;

import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * The class provides methods to access and manipulate User objects in the data store.
 */
public class UserDAO {
    /**
     * These are the SQL executions to be used for the database
     */
    private static final String INSERT = "INSERT into users (username, password, email) VALUE (?,?,?)";
    private static final String FIND = "SELECT * FROM users WHERE username = ?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String CLEAR = "DELETE FROM users";
    public static final Database myDatabase = new Database();

    /**
     * Configures the database
     * @throws DataAccessException If the database can't be created for some reason
     */
    public UserDAO() throws DataAccessException {
        configureDatabase();
    }

    /**
     * Connects to the database, and creates the table if it doesn't exist for some reason
     * @throws DataAccessException If fails to connect to the database
     */
    private void configureDatabase() throws DataAccessException {
        Connection connection = myDatabase.getConnection();
        try {
            connection.setCatalog(Database.DB_NAME);
            String createTable = """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                PRIMARY KEY (username)
            )""";

            // Create the table if it doesn't exist yet
            PreparedStatement createTableStatement = connection.prepareStatement(createTable);
            createTableStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Adds a user to the data store
     * @param user The user to be added to the data store
     * @throws DataAccessException When the user already exists in the data store
     */
    public void addUser(User user) throws DataAccessException {
        // Missing a password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new DataAccessException("Error 400 bad request");
        }

        // The username already exists
        else if (findUser(user.getUsername()) != null) {
            throw new DataAccessException("Error 403 username already taken");
        }

//        // The email already exists
//        else if (Objects.equals(findUser(user.getUsername()).getEmail(), user.getEmail())) {
//            throw new DataAccessException("Error 403 email already taken");
//        }

        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.execute();

        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());

        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Finds a user in the data store
     * @param username The username that we are looking for in the data store
     * @return true if the user is found, otherwise return false
     */
    public User findUser(String username) throws DataAccessException {
//        for (User user : users) {
//            if (Objects.equals(user.getUsername(), username) ) {
//                return user;
//            }
//        }
//        return null;
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

    /**
     * Returns the hash set of users
     * @return The hash set of users
     */
    public HashSet<User> findAllUsers() throws DataAccessException {
        HashSet<User> users = new HashSet<>();
        Connection connection = myDatabase.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User userToAdd = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
                users.add(userToAdd);
            }
            return users;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        } finally {
            myDatabase.closeConnection(connection);
        }
    }

//    /**
//     * Returns the user with the associated username
//     * @param username The username of the associated user
//     * @return The user with the associated username
//     * @throws DataAccessException When there is no user with the associated username
//     */
//    public User getUserByUsername(String username) throws DataAccessException {
//        for (User user : users) {
//            if (Objects.equals(user.getUsername(), username)) {
//                return user;
//            }
//        }
//        throw new DataAccessException("Error no user with that username");
//    }

    /**
     * Clears all the Users in the database.
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
}
