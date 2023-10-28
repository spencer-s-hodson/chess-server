package passoffTests.serverTests;

import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.LoginService;
import services.LogoutService;
import services.RegisterService;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.responses.LoginResponse;

public class LogoutServiceTest {
    private static final UserDAO userDAO = new UserDAO();
    private static final AuthDAO authDAO = new AuthDAO();
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
    private static final LogoutService logoutService = new LogoutService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "Auth DAO did not clear successfully");

        Assertions.assertTrue(userDAO.getUsers().isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(authDAO.getAuthTokens().isEmpty(), "Auth DAO is not empty");
    }

    @Test
    @DisplayName("Successful Logout")
    public void logout() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        Assertions.assertDoesNotThrow(() -> registerService.register(registerRequest), "Registration failure");

        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), testUser.getPassword());
        LoginResponse loginResponse = Assertions.assertDoesNotThrow(() -> loginService.login(loginRequest), "Login failure");

        Assertions.assertTrue(authDAO.getAuthTokens().size() == 2, "User not added after login");

        logoutService.logout(loginResponse.getAuthToken());

        Assertions.assertTrue(authDAO.getAuthTokens().size() == 1, "User not removed after logout");
    }

    @Test
    @DisplayName("Unsuccessful Logout")
    public void logoutFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        Assertions.assertDoesNotThrow(() -> registerService.register(registerRequest), "Registration failure");

        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), testUser.getPassword());
        Assertions.assertDoesNotThrow(() -> loginService.login(loginRequest), "Login failure");

        Assertions.assertTrue(userDAO.getUsers().size() == 1 && authDAO.getAuthTokens().size() == 2, "Auth token not added");

        logoutService.logout("non-existing auth token");

        Assertions.assertFalse(authDAO.getAuthTokens().size() == 1);
    }
}
