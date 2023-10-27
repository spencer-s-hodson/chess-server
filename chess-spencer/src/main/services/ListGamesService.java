package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import services.responses.ListGamesResponse;

import java.util.HashSet;

/**
 * This class represents the service of getting a list of all of previous games
 */
public class ListGamesService {
    private AuthDAO authDAO = new AuthDAO();
    private GameDAO gameDAO = new GameDAO();

    /**
     * Gets the list of all previous games played, and returns a response
     * @return The response from getting the previous games
     */
    public ListGamesResponse listGames(String authToken) {
        try {
            AuthToken at = authDAO.findAuthToken(authToken);
            if (at == null) {
                throw new DataAccessException("Error 401 unauthorized");
            }
            HashSet<Game> games = GameDAO.getGames();
            // 200
            return new ListGamesResponse(games);

        } catch (DataAccessException e) {
            return new ListGamesResponse(e.getMessage());
        }
    }
}
