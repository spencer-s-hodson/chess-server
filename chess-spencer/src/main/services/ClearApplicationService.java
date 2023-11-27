package services;

import dataAccess.*;
import responses.ClearApplicationResponse;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    private static final UserDAO userDAO;
    private static final AuthDAO authDAO;
    private static final GameDAO gameDAO;

    static {
        try {
            userDAO = new UserDAO();
            authDAO = new AuthDAO();
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

     /**
     * Clear all data from the database, and returns a response
     * @return The result of trying to clear the database
     */
    public ClearApplicationResponse clearApplication() {
        try {
            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();

            return new ClearApplicationResponse("Database Clear");
        } catch (DataAccessException ex) {
            return new ClearApplicationResponse(ex.getMessage());
        }
    }
}
