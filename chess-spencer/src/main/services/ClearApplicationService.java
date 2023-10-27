package services;

import dataAccess.*;
import services.responses.ClearApplicationResponse;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    private UserDAO userDAO = new UserDAO();
    private AuthDAO authToken = new AuthDAO();
    private GameDAO gameDAO = new GameDAO();

    /**
     * Clear all data from the database, and returns a response
     * @return The result of trying to clear the database
     */
    public ClearApplicationResponse clearApplication() {
        try {
            userDAO.clear();
            authToken.clear();
            gameDAO.clear();

            // return a 200 message
            return new ClearApplicationResponse("nice job");
        } catch (DataAccessException e) {
            // return a 500 message
            return new ClearApplicationResponse("Error: " + e.getMessage());
        }
    }
}
