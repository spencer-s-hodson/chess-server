package services;

import results.ClearApplicationResponse;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    /**
     * Clear all data from the database, and returns a response
     * @return The result of trying to clear the database
     */
    public ClearApplicationResponse clearApplication() {
        return new ClearApplicationResponse( "Error: description");
    }
}
