package dataAccess;

import models.AuthToken;

/**
 * The class provides methods to access and manipulate AuthToken objects in the data store.
 */

public class AuthDAO {
    /**
     * Returns an AuthToken associated with a specified username
     * @param userName The username used to get the AuthToken
     * @return The AuthToken associated with the username
     * @throws DataAccessException when the username doesn't exist in the data store
     */
    public AuthToken getAuthTokenByUsername(String userName) throws DataAccessException {
        // TODO
        return null;
    }

    /**
     * Updates information within an AuthToken
     * @param authToken The information to be updated
     * @param username The new username
     * @param authCode The new authCode
     * @throws DataAccessException when the AuthToken doesn't exist in the data store
     */
    public void updateAuthToken(AuthToken authToken, String username, String authCode) throws DataAccessException {
        // TODO
    }

    /**
     * Adds an AuthToken to the data store
     * @param authToken The AuthToken to add
     * @throws DataAccessException when the AuthToken already exists in the data store
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        // TODO
    }

    /**
     * Removes an AuthToken from the data store
     * @param authToken The AuthToken to remove
     * @throws DataAccessException when the AuthToken doesn't exist in the data store
     */
    public void removeAuthToken(AuthToken authToken) throws DataAccessException {
        // TODO
    }

    /**
     * Finds an AuthToken in the data store
     * @param authToken The AuthToken to find
     * @return true if found, otherwise return false
     */
    public boolean findAuthToken(AuthToken authToken) {
        // TODO
        return false;
    }
}
