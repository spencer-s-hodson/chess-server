package results;

/**
 * This class represents the response upon attempting to create a new game
 */
public class CreateGameResponse {
    /**
     * The game ID for the newly created chess game
     */
    private int gameID;
    /**
     * The message that displays when a new chess game can't be created
     */
    private String message;
    /**
     * Constructs a CreateGameResponse object when a new chess game is successfully created
     * @param gameID The game ID for the newly created chess game
     */
    public CreateGameResponse(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Constructs a CreateGameResponse object when a new chess game fails to be created
     * @param message The message that displays when a new chess game can't be created
     */
    public CreateGameResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the game ID for the newly created chess game
     * @return The game ID for the newly created chess game
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets game ID for the newly created chess game
     * @param gameID The game ID for the newly created chess game
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Returns the message that displays when a new chess game can't be created
     * @return The message that displays when a new chess game can't be created
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that displays when a new chess game can't be created
     * @param message The message that displays when a new chess game can't be created
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
