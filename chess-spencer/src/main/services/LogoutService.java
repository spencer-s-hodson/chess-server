package services;

import results.LogoutResponse;

/**
 * This class represents the service of logging a user out, represented by an authToken
 */
public class LogoutService {
    /**
     * Submits a request to log the user out, and returns a response
     * @return The response from logging out
     */
    public LogoutResponse logout() {
        return new LogoutResponse("message");
    }
}
