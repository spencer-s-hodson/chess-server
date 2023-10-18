package services;

import results.ListGamesResponse;

/**
 * This class represents the service of getting a list of all of previous games
 */
public class ListGamesService {
    /**
     * Gets the list of all previous games played, and returns a response
     * @return The response from getting the previous games
     */
    public ListGamesResponse listGames() {
        return new ListGamesResponse(new models.Game[1]);
    }
}
