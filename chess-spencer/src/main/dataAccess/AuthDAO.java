package dataAccess;

import models.AuthToken;

import java.util.HashSet;
import java.util.Objects;

/**
 * The class provides methods to access and manipulate AuthToken objects in the data store.
 */
public class AuthDAO {

    /** A hash set of auth token models */
    private static HashSet<AuthToken> authTokens = new HashSet<>();

    /**
     * Adds an AuthToken to the data store
     * @param authToken The AuthToken to add
     * @throws DataAccessException when the AuthToken already exists in the data store
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("invalid auth token");
        }
        authTokens.add(authToken);
    }

    /**
     * Removes an AuthToken from the data store
     * @param authToken The AuthToken to remove
     * @throws DataAccessException when the AuthToken doesn't exist in the data store
     */
    public void removeAuthToken(AuthToken authToken) throws DataAccessException {
        for (AuthToken at : authTokens) {
            if (at == authToken) {
                authTokens.remove(authToken);
            }
        }
        throw new DataAccessException("auth token not found");
    }

    /**
     * Finds an AuthToken in the data store
     * @param authToken The AuthToken to find
     * @return true if found, otherwise return false
     */
    public AuthToken findAuthToken(String authToken) throws DataAccessException {
        for (AuthToken at : authTokens) {
            if (Objects.equals(at.getAuthCode(), authToken)) {
                return at;
            }
        }
        throw new DataAccessException("Error 401 unauthorized");
    }

    /**
     * Clears all the AuthTokens in the database.
     */
    public void clear() throws DataAccessException {
        authTokens = new HashSet<>();
    }

    /**
     * Returns a hash set of auth tokens
     * @return A hash set of auth tokens
     */
    public HashSet<AuthToken> getAuthTokens() {
        return authTokens;
    }
}
