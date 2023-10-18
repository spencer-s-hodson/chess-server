package services;

import models.AuthToken;
import requests.LoginRequest;
import results.LoginResponse;

/**
 * This class represents the service of logging in an existing user
 */
public class LoginService {
    /**
     * Logs in an existing user and returns a response
     * @param r The request for logging in an existing user
     * @return The response upon logging in
     */
    public LoginResponse login(LoginRequest r) {
        return new LoginResponse(r.getUsername(), new AuthToken("foobar", r.getUsername()));
    }
}
