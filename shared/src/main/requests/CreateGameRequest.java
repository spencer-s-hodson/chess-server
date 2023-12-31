package requests;

/**
 * This class represents the request to create a new chess game
 */
public class CreateGameRequest {
    /**
     * The name of the newly created chess game
     */
    private final String gameName;

    /**
     * Constructs a CreateGameRequest
     * @param gameName The name of the newly created chess game
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns the name of the newly created chess game
     * @return The name of the newly created chess game
     */
    public String getGameName() {
        return gameName;
    }
}
