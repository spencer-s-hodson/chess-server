package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.Objects;
import java.util.UUID;

/**
 * This class represents the service of logging in an existing user
 */
public class LoginService {
    private static final UserDAO userDAO;
    private static final AuthDAO authDAO;

    static {
        try {
            userDAO = new UserDAO();
            authDAO = new AuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Logs in an existing user and returns a response
     * @param r The request for logging in an existing user
     * @return The response upon logging in
     */
    public LoginResponse login(LoginRequest r) {
        try {
            User foundUser = userDAO.findUser(r.getUsername());
            if (foundUser == null) {
                throw new DataAccessException("Error 401 unauthorized, user does not exist");
            }

            if (!Objects.equals(foundUser.getPassword(), r.getPassword())) {
                throw new DataAccessException("Error 401 unauthorized, password is incorrect");
            }

            AuthToken newAuthToken = new AuthToken(UUID.randomUUID().toString(), foundUser.getUsername());
            authDAO.addAuthToken(newAuthToken);
            return new LoginResponse(foundUser.getUsername(), newAuthToken.getAuthToken());

        } catch (DataAccessException e) {
            return new LoginResponse(e.getMessage());
        }

    }
}
