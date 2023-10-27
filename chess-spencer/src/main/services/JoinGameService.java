package services;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import services.requests.JoinGameRequest;
import services.responses.JoinGameResponse;

import javax.xml.crypto.Data;

/**
 * Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
 * If no color is specified the user is joined as an observer.
 * This request is idempotent.
 */
public class JoinGameService {
    private GameDAO gameDAO = new GameDAO();
    private AuthDAO authDAO = new AuthDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Attempts to join the game and returns a response
     * @param r The request to join the game
     * @return The response from trying to join a game
     */
    public JoinGameResponse joinGame(JoinGameRequest r, String authToken) {
        try {
            AuthToken at = authDAO.findAuthToken(authToken);
            if (at == null) {
                throw new DataAccessException("Error 401 unauthorized");
            }

            String username = authDAO.findAuthToken(authToken).getUsername();

            System.out.println(r.getTeamColor());

            gameDAO.claimSpot(r.getTeamColor(), username, r.getGameID());
            return new JoinGameResponse(null);
        }
        catch (DataAccessException e) {
            return new JoinGameResponse(e.getMessage());
        }
    }
}
