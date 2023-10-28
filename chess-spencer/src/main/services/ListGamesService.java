package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import services.responses.ListGamesResponse;

import java.util.HashSet;

/**
 * This class represents the service of getting a list of all of previous games
 */
public class ListGamesService {
    private final AuthDAO authDAO = new AuthDAO();
    private final GameDAO gameDAO = new GameDAO();

    /**
     * Gets the list of all previous games played, and returns a response
     * @return The response from getting the previous games
     */
    public ListGamesResponse listGames(String authToken) {
        try {
            authDAO.findAuthToken(authToken);
            HashSet<Game> games = gameDAO.getGames();
            return new ListGamesResponse(games);

        } catch (DataAccessException e) {
            return new ListGamesResponse(e.getMessage());
        }
    }
}
