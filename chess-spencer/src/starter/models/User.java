package models;

/**
 * JavaDoc comment about the User class.
 */

public interface User {
    /*
    questions i have:
    - should these classes be interfaces or actual java classes?
    - the models don't have any methods?
    - the DAO classes do tho?
    - can we pass this off as a grade more than once?
     */







    /**
     * @return the username of the user
     */
    String getUsername();

    /**
     * @return the password of the user
     */
    String getPassword();

    /**
     * @return the email of the user
     */
    String getEmail();

}
