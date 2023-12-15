package services;

import dataAccess.*;
import models.*;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.util.UUID;

/**
 * This class represents the registration of a new user.
 */
public class RegisterService {
    private static final UserDAO userDAO;
    private static final AuthDAO authDAO;
    static {
        try {
            userDAO = new UserDAO();
            authDAO = new AuthDAO();
        } catch (DataAccessException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * Registers a new user, and returns a response
     * @param r The request to register a user
     * @return The response from submitting the request
     */
    public RegisterResponse register(RegisterRequest r) {
        try {
            User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail());
            userDAO.addUser(newUser);
            AuthToken newAuthToken = new AuthToken(UUID.randomUUID().toString(), newUser.getUsername());
            authDAO.addAuthToken(newAuthToken);
            return new RegisterResponse(newUser.getUsername(), newAuthToken.getAuthToken());
        } catch (DataAccessException e) {
            return new RegisterResponse(e.getMessage());
        }
    }
}
