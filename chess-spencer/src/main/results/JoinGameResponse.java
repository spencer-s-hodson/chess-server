package results;

/**
 * This class represents the response upon attempting to join an existing chess game
 */
public class JoinGameResponse {
    /**
     * The message displayed when the user is unable to join an existing chess game
     */
    private String message;

    /**
     * Constructs a JoinGameResponse that represents an unsuccessful attempt to join the chess game
     * @param message The message displayed when the user is unable to join an existing chess game
     */
    public JoinGameResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the message displayed when the user is unable to join an existing chess game
     * @return The message displayed when the user is unable to join an existing chess game
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message displayed when the user is unable to join an existing chess game
     * @param message The message displayed when the user is unable to join an existing chess game
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
