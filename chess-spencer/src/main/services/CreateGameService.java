package services;

import requests.CreateGameRequest;
import results.CreateGameResponse;

/**
 * This class represents the service that creates a new chess game to be played
 */
public class CreateGameService {
    /**
     * Creates the new game, and return a response
     * @param r The request to create a new game
     * @return The response upon creating a new game
     */
    public CreateGameResponse createGame(CreateGameRequest r) {
        return new CreateGameResponse(1234);
    }
}
