package models;

import chess.ChessGame;

import java.util.Objects;

/**
 * This class contains information about a chess game
 */

public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;

    /**
     * Constructs a new game object
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new chess.Game(); // like this?
    }

    /**
     * Returns the game ID
     * @return the game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets a new game ID
     * @param gameID the new game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Returns the username for the white team
     * @return the white team's username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * Sets a new username for the white team
     * @param whiteUsername the white team's new username
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * Returns the username for the black team
     * @return the black team's username
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * Set a new username for the black team
     * @param blackUsername the black team's new username
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * Returns the game's name
     * @return the game's name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets a new name for the game
     * @param gameName the new name of the game
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns the all logic and board for the chess game
     * @return the logic and board for the chess game
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * Sets the logic and a new board for the chess game
     * @param game the logic and new board for the chess game
     */
    public void setGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", gameName='" + gameName + '\'' +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game1 = (Game) o;
        return gameID == game1.gameID && Objects.equals(whiteUsername, game1.whiteUsername) && Objects.equals(blackUsername, game1.blackUsername) && Objects.equals(gameName, game1.gameName) && Objects.equals(game, game1.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game);
    }
}
