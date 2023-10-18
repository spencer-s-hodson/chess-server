package services;

import requests.JoinGameRequest;
import results.JoinGameResponse;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
 * If no color is specified the user is joined as an observer.
 * This request is idempotent.
 */
public class JoinGameService {
    /**
     * Attempts to join the game and returns a response
     * @param r The request to join the game
     * @return The response from trying to join a game
     */
    public JoinGameResponse joinGame(JoinGameRequest r) {
        return new JoinGameResponse("Error");
    }
}
