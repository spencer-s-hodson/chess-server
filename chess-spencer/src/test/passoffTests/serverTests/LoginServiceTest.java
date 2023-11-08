package passoffTests.serverTests;


import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.LoginService;
import services.RegisterService;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;


public class LoginServiceTest {
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
    private static final RegisterService registerService = new RegisterService();
    private static final LoginService loginService = new LoginService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "Auth DAO did not clear successfully");

        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty(), "Auth DAO is not empty");
    }
    @Test
    @DisplayName("Successful Login")
    public void login() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        Assertions.assertDoesNotThrow(() -> registerService.register(registerRequest), "Registration failure");

        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), testUser.getPassword());
        Assertions.assertDoesNotThrow(() -> loginService.login(loginRequest), "Login failure");

        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).size() == 1 && Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).size() == 2, "Auth token not added");
    }

    @Test
    @DisplayName("Unsuccessful Login")
    public void loginFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        Assertions.assertDoesNotThrow(() -> registerService.register(registerRequest), "Registration failure");

        LoginRequest loginRequest = new LoginRequest("Bad Username", testUser.getPassword());
        Assertions.assertDoesNotThrow(() -> loginService.login(loginRequest), "Login failure");

        Assertions.assertFalse(Assertions.assertDoesNotThrow(userDAO::findAllUsers).size() == 1 && Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).size() == 2);
    }






}


