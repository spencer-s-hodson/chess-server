package services;

import dataAccess.*;
import services.responses.ClearApplicationResponse;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    private final UserDAO userDAO = new UserDAO();
    private final AuthDAO authToken = new AuthDAO();
    private final GameDAO gameDAO = new GameDAO();

     /**
     * Clear all data from the database, and returns a response
     * @return The result of trying to clear the database
     */
    public ClearApplicationResponse clearApplication() {
        try {
            userDAO.clear();
            authToken.clear();
            gameDAO.clear();

            return new ClearApplicationResponse("nice job");
        } catch (DataAccessException e) {
            return new ClearApplicationResponse("Error: " + e.getMessage());
        }
    }
}
