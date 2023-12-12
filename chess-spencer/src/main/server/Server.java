package server;

import handlers.*;
import spark.*;

/**
 * This class represents the main server that handles incoming HTTP requests.
 * It initializes all the necessary handlers for specific endpoints and starts
 * the Spark server to listen for requests.
 */
public class Server {

    /** Handler for the "clear application" operation. */
    private final ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();

    /** Handler for registering a user. */
    private final RegisterHandler registerHandler = new RegisterHandler();

    /** Handler for user login operations. */
    private final LoginHandler loginHandler = new LoginHandler();

    /** Handler for user logout operations. */
    private final LogoutHandler logoutHandler = new LogoutHandler();

    /** Handler for listing available games. */
    private final ListGamesHandler listGamesHandler = new ListGamesHandler();

    /** Handler for creating a new game. */
    private final CreateGameHandler createGameHandler = new CreateGameHandler();

    /** Handler for joining an existing game. */
    private final JoinGameHandler joinGameHandler = new JoinGameHandler();

    /**
     * The main entry point of the server application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Server().run();
    }

    /**
     * Starts the Spark server and sets up all routes.
     */
    private void run() {
        Spark.port(8080);
        System.out.println("Listening on port 8080");

        Spark.webSocket("/connect", GameConnectionHandler.class);


        Spark.externalStaticFileLocation("web");

        routes();
    }

    /**
     * Sets up routes (endpoints) and associates them with the appropriate handlers.
     */
    private void routes() {
        Spark.delete("/db", this::clearApplication);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("game", this::createGame);
        Spark.put("/game", this::joinGame);
    }

    /**
     * Handles the request to clear application data.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response object.
     * @return An object representing the result of the clear operation.
     */
    private Object clearApplication(Request request, Response response) {
        return clearApplicationHandler.clearApplication(response);
    }

    /**
     * Handles the registration of a new user.
     *
     * @param request  The incoming HTTP request containing registration details.
     * @param response The HTTP response object.
     * @return An object representing the result of the registration operation.
     */
    private Object register(Request request, Response response) {
        return registerHandler.register(request, response);
    }

    /**
     * Handles user login operations.
     *
     * @param request  The incoming HTTP request containing login details.
     * @param response The HTTP response object.
     * @return An object representing the result of the login operation.
     */
    private Object login(Request request, Response response) {
        return loginHandler.login(request, response);
    }

    /**
     * Handles user logout operations.
     *
     * @param request  The incoming HTTP request for logout.
     * @param response The HTTP response object.
     * @return An object representing the result of the logout operation.
     */
    private Object logout(Request request, Response response) {
        return logoutHandler.logout(request, response);
    }

    /**
     * Lists the available games.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response object.
     * @return An object representing a list of available games.
     */
    private Object listGames(Request request, Response response) {
        return listGamesHandler.listGames(request, response);
    }

    /**
     * Handles the creation of a new game.
     *
     * @param request  The incoming HTTP request containing game creation details.
     * @param response The HTTP response object.
     * @return An object representing the result of the game creation operation.
     */
    private Object createGame(Request request, Response response) {
        return createGameHandler.createGame(request, response);
    }

    /**
     * Allows a user to join an existing game.
     *
     * @param request  The incoming HTTP request containing game joining details.
     * @param response The HTTP response object.
     * @return An object representing the result of the join game operation.
     */
    private Object joinGame(Request request, Response response) {
        return joinGameHandler.joinGame(request, response);
    }
}



