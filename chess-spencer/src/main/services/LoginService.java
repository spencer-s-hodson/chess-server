package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import services.requests.LoginRequest;
import services.responses.LoginResponse;

import java.util.Objects;

/**
 * This class represents the service of logging in an existing user
 */
public class LoginService {
    private final UserDAO userDAO = new UserDAO();
    private final AuthDAO authDAO = new AuthDAO();

    /**
     * Logs in an existing user and returns a response
     * @param r The request for logging in an existing user
     * @return The response upon logging in
     */
    public LoginResponse login(LoginRequest r) {
        try {
            User foundUser = userDAO.findUser(r.getUsername());
            if (foundUser == null || !Objects.equals(foundUser.getPassword(), r.getPassword())) {
                throw new DataAccessException("Error 401 unauthorized");
            }

            AuthToken newAuthToken = new AuthToken(foundUser.getUsername());
            authDAO.addAuthToken(newAuthToken);
            return new LoginResponse(foundUser.getUsername(), newAuthToken.getAuthCode());

        } catch (DataAccessException e) {
            return new LoginResponse(e.getMessage());
        }

    }
}
