package dataAccess;

import models.User;

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
    public User getUserByUsername(String username) throws DataAccessException {
        // TODO
        return null;
    }

    /**
     * Return the user with the associated email
     * @param email The email of the associated user
     * @return The user with the associated email
     * @throws DataAccessException When there is no user with the associated email
     */
    public User getUserByEmail(String email) throws DataAccessException {
        // TODO
        return null;
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
        // TODO
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
     * Finds a user in the data store
     * @param user The user that we are looking for in the data store
     * @return true if the user is found, otherwise return false
     */
    public boolean findUser(User user) {
        // TODO
        return false;
    }
}
