package passoffTests.daoTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class AuthDAOTests {
    private static final AuthDAO authDAO;

    static {
        try {
            authDAO = new AuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        Assertions.assertDoesNotThrow(authDAO::clear);
    }

    @Test
    @DisplayName("Add AuthToken")
    public void addAuthToken() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));
    }

    @Test
    @DisplayName("Fail to Add AuthToken")
    public void failToAddAuthToken() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        AuthToken otherNewAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.addAuthToken(otherNewAuthToken));
    }

    @Test
    @DisplayName("Find AuthToken")
    public void findUser() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        AuthToken foundAuthToken = Assertions.assertDoesNotThrow(() -> authDAO.findAuthToken(newAuthToken.getAuthToken()));
        Assertions.assertEquals(foundAuthToken, newAuthToken);
    }

    @Test
    @DisplayName("Fail to Find AuthToken")
    public void failToFindAuthToken() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.findAuthToken(newAuthToken.getAuthToken()));
    }

    @Test
    @DisplayName("Find All AuthTokens")
    public void findAllAuthTokens() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        AuthToken otherNewAuthToken = new AuthToken("testAuthToken1", "username1");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(otherNewAuthToken));

        Assertions.assertEquals(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).size(), 2);

        HashSet<AuthToken> authTokens = new HashSet<>();
        authTokens.add(newAuthToken);
        authTokens.add(otherNewAuthToken);
        Assertions.assertEquals(authTokens, Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens));
    }

    @Test
    @DisplayName("Fail to Find All AuthTokens")
    public void failToFindAllAuthTokens() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        AuthToken otherNewAuthToken = new AuthToken("testAuthToken1", "username1");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(otherNewAuthToken));

        // ???
        Assertions.assertNotEquals(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).size(), 1);
    }

    @Test
    @DisplayName("Remove AuthToken")
    public void removeAuthToken() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        Assertions.assertDoesNotThrow(() -> authDAO.removeAuthToken(newAuthToken));
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.findAuthToken(newAuthToken.getAuthToken()));
    }

    @Test
    @DisplayName("Fail to Remove AuthToken")
    public void failToRemoveAuthToken() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.findAuthToken(newAuthToken.getAuthToken()));
    }

    @Test
    @DisplayName("Clear AuthTokens")
    public void clearAuthTokens() {
        AuthToken newAuthToken = new AuthToken("testAuthToken", "username");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(newAuthToken));

        AuthToken otherNewAuthToken = new AuthToken("testAuthToken1", "username1");
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthToken(otherNewAuthToken));

        Assertions.assertDoesNotThrow(authDAO::clear);
        Assertions.assertTrue(Assertions.assertDoesNotThrow(authDAO::findAllAuthTokens).isEmpty());
    }
}
