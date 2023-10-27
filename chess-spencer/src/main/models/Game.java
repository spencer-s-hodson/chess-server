package models;

import chess.ChessGame;

import java.util.Objects;

/**
 * This class contains information about a chess game
 */

public class Game {
    /**
     * Counter to keep track of the game ID upon creation
     */
    private static int counter = 1;

    /**
     * The game ID for the chess game
     */
    private int gameID;
    /**
     * The white team's username for the chess game
     */
    private String whiteUsername;
    /**
     * The black team's username for the chess game
     */
    private String blackUsername;
    /**
     * The name of the chess game
     */
    private String gameName;
    /**
     * The logic for the chess game
     */
    private ChessGame game;

    public Game(String gameName) {
        this.gameID = counter++;
        this.gameName = gameName;
        this.game = new chess.Game();
    }

    /**
     * Returns the game ID
     * @return The game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets a new game ID
     * @param gameID The new game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Returns the username for the white team
     * @return The white team's username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * Sets a new username for the white team
     * @param whiteUsername The white team's new username
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * Returns the username for the black team
     * @return The black team's username
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * Set a new username for the black team
     * @param blackUsername The black team's new username
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * Returns the game's name
     * @return The game's name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets a new name for the game
     * @param gameName The new name of the game
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns the all logic and board for the chess game
     * @return The logic and board for the chess game
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * Sets the logic and a new board for the chess game
     * @param game The logic and new board for the chess game
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
