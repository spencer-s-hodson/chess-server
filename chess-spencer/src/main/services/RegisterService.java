package services;

import dataAccess.*;
import models.*;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;

import java.util.UUID;

/**
 * This class represents the registration of a new user.
 */

public class RegisterService {
    private final UserDAO userDAO = new UserDAO();
    private final AuthDAO authDao = new AuthDAO();

    /**
     * Registers a new user, and returns a response
     * @param r The request to register a user
     * @return The response from submitting the request
     */
    public RegisterResponse register(RegisterRequest r) {
        try {
            User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail());
            userDAO.addUser(newUser);

            AuthToken newAuthToken = new AuthToken(newUser.getUsername());
            authDao.addAuthToken(newAuthToken);

            return new RegisterResponse(newUser.getUsername(), newAuthToken.getAuthCode());

        } catch (DataAccessException e) {
            return new RegisterResponse(e.getMessage());
        }
    }
}
