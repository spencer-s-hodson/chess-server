package services;

//import requests.ClearApplicationRequest;
import results.ClearApplicationResult;

/**
 * Clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    public ClearApplicationResult clearApplication() {
        // call on some method that ClearApplicationRequest has
        return new ClearApplicationResult(500, "Error: description");
    }
}
