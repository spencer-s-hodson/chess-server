package services;

import models.AuthToken;
import requests.RegisterRequest;
import results.RegisterResponse;

/**
 * This class represents the registration of a new user.
 */
public class RegisterService {
    /**
     * Registers a new user, and returns a response
     * @param r The request to register a user
     * @return The response from submitting the request
     */
    public RegisterResponse register(RegisterRequest r) {
        return new RegisterResponse(r.getUsername(), new AuthToken("foobar", r.getUsername()));
    }
}
