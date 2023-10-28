package dataAccess;

import models.User;

import java.util.HashSet;
import java.util.Objects;

/**
 * The class provides methods to access and manipulate User objects in the data store.
 */
public class UserDAO {
    /**
     * A hash set of users
     */
    private static HashSet<User> users = new HashSet<>();

    /**
     * Returns the user with the associated username
     * @param username The username of the associated user
     * @return The user with the associated username
     * @throws DataAccessException When there is no user with the associated username
     */
    public User getUserByUsername(String username) throws DataAccessException {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        throw new DataAccessException("Error no user with that username");
    }

    /**
     * Adds a user to the data store
     * @param user The user to be added to the data store
     * @throws DataAccessException When the user already exists in the data store
     */
    public void addUser(User user) throws DataAccessException {
        // The user already exists
        if (findUser(user.getUsername()) != null) {
            throw new DataAccessException("Error 403 already taken");
        }

        // Missing a password
        else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new DataAccessException("Error 400 bad request");
        }

        // Successfully add the user
        else {
            users.add(user);
        }
    }

    /**
     * Finds a user in the data store
     * @param username The username that we are looking for in the data store
     * @return true if the user is found, otherwise return false
     */
    public User findUser(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username) ) {
                return user;
            }
        }
        return null;
    }

    /**
     * Clears all the Users in the database.
     */
    public void clear() throws DataAccessException {
        users = new HashSet<>();
    }

    /**
     * Returns the hash set of users
     * @return The hash set of users
     */
    public HashSet<User> getUsers() {
        return users;
    }
}
