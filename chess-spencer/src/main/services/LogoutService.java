package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import services.responses.LogoutResponse;

/**
 * This class represents the service of logging a user out, represented by an authToken
 */
public class LogoutService {
    private AuthDAO authDAO = new AuthDAO();

    /**
     * Submits a request to log the user out, and returns a response
     * @return The response from logging out
     */
    public LogoutResponse logout(String authToken) {
        try {
            AuthToken at = authDAO.findAuthToken(authToken);
            if (at == null) {
                throw new DataAccessException("Error 401 unauthorized");
            }
            authDAO.removeAuthToken(at);
            // delete from usersDAO

            return new LogoutResponse(null);

        }
        catch (DataAccessException e) {
            return new LogoutResponse(e.getMessage());
        }
    }
}
