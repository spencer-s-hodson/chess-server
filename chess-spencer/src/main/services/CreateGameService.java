package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * This class represents the service that creates a new chess game to be played
 */
public class CreateGameService {
    private static final AuthDAO authDAO;
    private static final GameDAO gameDAO;

    static {
        try {
            authDAO = new AuthDAO();
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            models.Game newGame = new Game(createGameRequest.getGameName(), new chess.Game());

            int gameID = gameDAO.addGame(newGame);
            newGame.setGameID(gameID);
            return new CreateGameResponse(newGame.getGameID());

        } catch (DataAccessException e) {
            return new CreateGameResponse(e.getMessage());
        }
    }
}
