package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import responses.LogoutResponse;

/**
 * This class represents the service of logging a user out, represented by an authToken
 */
public class LogoutService {
    private static final AuthDAO authDAO;
    static {
        try {
            authDAO = new AuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Submits a request to log the user out, and returns a response
     * @return The response from logging out
     */
    public LogoutResponse logout(String authToken) {
        try {
            AuthToken at = authDAO.findAuthToken(authToken);
            authDAO.removeAuthToken(at);
            return new LogoutResponse(null);
        } catch (DataAccessException e) {
            return new LogoutResponse(e.getMessage());
        }
    }
}
