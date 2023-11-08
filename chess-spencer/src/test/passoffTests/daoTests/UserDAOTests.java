package passoffTests.daoTests;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.User;
import org.junit.jupiter.api.*;

import java.util.HashSet;

public class UserDAOTests {
    private static final UserDAO userDAO;

    static {
        try {
            userDAO = new UserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    @DisplayName("Add User")
    public void addUser() {
        User newUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(newUser));
    }

    @Test
    @DisplayName("Username Already Exists")
    public void usernameAlreadyExists() {
        User existingUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(existingUser));

        User newUser = new User("username", "new_password", "new_email");
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.addUser(newUser));
    }

    @Test
    @DisplayName("Email Already Exists")
    public void emailAlreadyExists() {
        User existingUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(existingUser));

        User newUser = new User("new_username", "new_password", "email");
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.addUser(newUser));
    }

    @Test
    @DisplayName("Need Password")
    public void needPassword() {
        User newUser = new User("new_username", null, "new_email");
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.addUser(newUser));
    }

    @Test
    @DisplayName("Find User")
    public void findUser() {
        User newUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(newUser));

        User foundUser = Assertions.assertDoesNotThrow(() -> userDAO.findUser(newUser.getUsername()));
        Assertions.assertEquals(foundUser, newUser);
    }

    @Test
    @DisplayName("User Not Found")
    public void userNotFound() {
        User newUser = new User("username", "password", "email");
        Assertions.assertNull(Assertions.assertDoesNotThrow(() -> userDAO.findUser(newUser.getUsername())));
    }

    @Test
    @DisplayName("Find All Users")
    public void findAllUsers() {
        User newUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(newUser));

        User othernewUser = new User("username1", "password1", "email1");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(othernewUser));

        Assertions.assertEquals(Assertions.assertDoesNotThrow(userDAO::findAllUsers).size(), 2);

        HashSet<User> users = new HashSet<>();
        users.add(newUser);
        users.add(othernewUser);
        Assertions.assertEquals(users, Assertions.assertDoesNotThrow(userDAO::findAllUsers));
    }

    @Test
    @DisplayName("Fail to Find All Users")
    public void failToFindAllUsers() {
        User newUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(newUser));

        User othernewUser = new User("username1", "password1", "email1");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(othernewUser));

        // ???
        Assertions.assertNotEquals(Assertions.assertDoesNotThrow(userDAO::findAllUsers).size(), 1);
    }

    @Test
    @DisplayName("Clear Users")
    public void clearUsers() {
        User newUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(newUser));

        User othernewUser = new User("username1", "password1", "email1");
        Assertions.assertDoesNotThrow(() -> userDAO.addUser(othernewUser));

        Assertions.assertDoesNotThrow(userDAO::clear);
        Assertions.assertTrue(Assertions.assertDoesNotThrow(userDAO::findAllUsers).isEmpty());
    }
}
