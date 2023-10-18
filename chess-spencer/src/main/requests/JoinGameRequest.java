package requests;

import chess.ChessGame;

/**
 * This class represents the request to join a chess game
 */
public class JoinGameRequest {
    /**
     * The team color that the user is trying to join as
     */
    private ChessGame.TeamColor teamColor;
    /**
     * The gameID of the game that the user is trying to join
     */
    private int gameID;

    /**
     * Constructs a JoinGameRequest object that requests to join a chess game
     * @param teamColor The team color that the user is trying to join as
     * @param gameID The gameID of the game that the user is trying to join
     */
    public JoinGameRequest(ChessGame.TeamColor teamColor, int gameID) {
        this.teamColor = teamColor;
        this.gameID = gameID;
    }

    /**
     * Returns the team color that the user is trying to join as
     * @return The team color that the user is trying to join as
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * Sets the team color that the user is trying to join as
     * @param teamColor The team color that the user is trying to join as
     */
    public void setTeamColor(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Returns the gameID of the game that the user is trying to join
     * @return The gameID of the game that the user is trying to join
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the gameID of the game that the user is trying to join
     * @param gameID The gameID of the game that the user is trying to join
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
