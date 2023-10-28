package passoffTests.serverTests;

import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import services.RegisterService;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;

public class RegisterServiceTest {
    private static final UserDAO userDAO = new UserDAO();
    private static final AuthDAO authDAO = new AuthDAO();
    private static final RegisterService registerService = new RegisterService();
    private static final User testUser = new User("username", "password", "email");

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(userDAO::clear, "User DAO did not clear successfully");
        Assertions.assertDoesNotThrow(authDAO::clear, "Auth DAO did not clear successfully");

        Assertions.assertTrue(userDAO.getUsers().isEmpty(), "User DAO is not empty");
        Assertions.assertTrue(authDAO.getAuthTokens().isEmpty(), "Auth DAO is not empty");
    }

    @Test
    @DisplayName("Successful Registration")
    public void register() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(), testUser.getEmail());
        RegisterResponse registerResponse = registerService.register(registerRequest);

        Assertions.assertEquals(testUser.getUsername(), registerResponse.getUsername(), "The username was added improperly");
        Assertions.assertEquals(testUser.getUsername(), Assertions.assertDoesNotThrow(() -> authDAO.findAuthToken(registerResponse.getAuthToken())).getUsername(), "Auth token not added to DAO");
        Assertions.assertEquals(testUser, Assertions.assertDoesNotThrow(() -> userDAO.getUserByUsername(testUser.getUsername())), "User not added to DAO");
    }

    @Test
    @DisplayName("Unsuccessful Registration")
    public void registerFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), null, testUser.getEmail());
        registerService.register(registerRequest);
        Assertions.assertTrue(userDAO.getUsers().isEmpty());
    }
}
