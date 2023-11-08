package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import services.requests.JoinGameRequest;
import services.responses.JoinGameResponse;

/**
 * This class represents the service of joining a chess game as a player or spectator
 */
public class JoinGameService {
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
     * Tries to join the game as a player or a spectator
     * @param joinGameRequest The request to join the game
     * @param authToken The auth token for verification
     * @return A response on what happened when trying to join the game
     */
    public JoinGameResponse joinGame(JoinGameRequest joinGameRequest, String authToken) {
        try {
            String username = authDAO.findAuthToken(authToken).getUsername();
            gameDAO.claimSpot(joinGameRequest.getTeamColor(), username, joinGameRequest.getGameID());
            return new JoinGameResponse(null);

        } catch (DataAccessException e) {
            return new JoinGameResponse(e.getMessage());
        }
    }
}
