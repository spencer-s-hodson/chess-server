package dataAccess;

import models.User;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.Objects;

/**
 * The class provides methods to access and manipulate User objects in the data store.
 */
public class UserDAO {
    /**
     * Returns the user with the associated username
     * @param username The username of the associated user
     * @return The user with the associated username
     * @throws DataAccessException When there is no user with the associated username
     */
    private static HashSet<User> users = new HashSet<>();

    public User getUserByUsername(String username) throws DataAccessException {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        throw new DataAccessException("Error no user with that username");
    }



    /**
     * Updates information about a user
     * @param user The user that needs to be updated
     * @param username The new username for the user
     * @param password The new password for the user
     * @param email The new email for the user
     * @throws DataAccessException When the given user doesn't exist
     */
    public void updateUser(User user, String username, String password, String email) throws DataAccessException {
        // TODO
    }

    /**
     * Adds a user to the data store
     * @param user The user to be added to the data store
     * @throws DataAccessException When the user already exists in the data store
     */
    public void addUser(User user) throws DataAccessException {
        // 403 already taken
        if (findUser(user.getUsername()) != null) {
            throw new DataAccessException("Error 403 already taken");
        }

        // 400 bad request
        else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new DataAccessException("Error 400 bad request");
        }

        // 200
        else {
            users.add(user);
        }
    }

    /**
     * Removes a user from the data store
     * @param user The user to be removed
     * @throws DataAccessException When the user doesn't exist in the data store
     */
    public void removeUser(User user) throws DataAccessException {
        // TODO
    }

    /**
     * Clears all the Users in the database.
     */
    public void clear() throws DataAccessException {
        users = new HashSet<>();
    }

    /**
     * Finds a user in the data store
     * @param username The username that we are looking for in the data store
     * @return true if the user is found, otherwise return false
     */
    public User findUser(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }

    public String findPassword(String password) {
        for (User user : users) {
            if (Objects.equals(user.getPassword(), password)) {
                return password;
            }
        }
        return null;
    }
}
