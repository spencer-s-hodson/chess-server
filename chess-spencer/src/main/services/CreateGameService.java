package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import services.requests.CreateGameRequest;
import services.responses.CreateGameResponse;
import services.responses.LogoutResponse;

/**
 * This class represents the service that creates a new chess game to be played
 */
public class CreateGameService {
    private AuthDAO authDAO = new AuthDAO();
    private GameDAO gameDAO = new GameDAO();
    /**
     * Creates the new game, and return a response
     * @param r The request to create a new game
     * @return The response upon creating a new game
     */
    public CreateGameResponse createGame(CreateGameRequest r, String authToken) {
        try {
            AuthToken at = authDAO.findAuthToken(authToken);
            if (at == null) {
                throw new DataAccessException("Error 401 unauthorized");
            }

            if (r.getGameName() == null) {
                throw new DataAccessException("Error 400 bad request");

            }
            Game newGame = new Game(r.getGameName());
            gameDAO.addGame(newGame);
            return new CreateGameResponse(newGame.getGameID());

        }
        catch (DataAccessException e) {
            return new CreateGameResponse(e.getMessage());
        }
    }
}
