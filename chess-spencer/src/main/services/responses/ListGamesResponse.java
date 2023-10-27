package services.responses;

import models.Game;

import java.util.HashSet;

/**
 * This class represents the response from getting the list of previous games played
 */
public class ListGamesResponse {
    /**
     * The list of games previously played
     */
    private HashSet<Game> games;
    /**
     * The message that displays upon failure to get the list of previously played games
     */
    private String message;

    /**
     * Constructs a ListGamesResponse object that represents a successful attempt to get the list of previously played games
     * @param games The list of previously played games
     */
    public ListGamesResponse(HashSet<Game> games) {
        this.games = games;
        this.message = null;
    }

    /**
     * Constructs a ListGamesResponse object that represent a failed attempt to get the list of previously played games
     * @param message The message that displays upon failure to get the list of previously played games
     */
    public ListGamesResponse(String message) {
        this.games = null;
        this.message = message;
    }

    /**
     * Returns the list of previously played games
     * @return The list of previously played games
     */
    public HashSet<Game> getGames() {
        return games;
    }

    /**
     * Sets the list of previously played games
     * @param games The list of previously played games
     */
    public void setGames(HashSet<Game> games) {
        this.games = games;
    }

    /**
     * Returns the message that displays upon failure to get the list of previously played games
     * @return The message that displays upon failure to get the list of previously played games
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the messages that displays upon failure to get the list of previously played games
     * @param message The message that displays upon failure to get the list of previously played games
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
