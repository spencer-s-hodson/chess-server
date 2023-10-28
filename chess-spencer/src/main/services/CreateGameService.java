package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import services.requests.CreateGameRequest;
import services.responses.CreateGameResponse;

/**
 * This class represents the service that creates a new chess game to be played
 */
public class CreateGameService {
    private final AuthDAO authDAO = new AuthDAO();
    private final GameDAO gameDAO = new GameDAO();

    /**
     * Creates the new game, and return a response
     * @param createGameRequest The request to create a new game
     * @return The response upon creating a new game
     */
    public CreateGameResponse createGame(CreateGameRequest createGameRequest, String authToken) {
        try {
            if (createGameRequest.getGameName() == null) {
                throw new DataAccessException("Error 400 bad request");
            }

            authDAO.findAuthToken(authToken);
            Game newGame = new Game(createGameRequest.getGameName());

            gameDAO.addGame(newGame);
            return new CreateGameResponse(newGame.getGameID());

        } catch (DataAccessException e) {
            return new CreateGameResponse(e.getMessage());
        }
    }
}
