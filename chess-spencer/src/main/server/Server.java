package server;

import handlers.*;
import spark.*;

public class Server {
    private ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    private RegisterHandler registerHandler = new RegisterHandler();
    private LoginHandler loginHandler = new LoginHandler();
    private LogoutHandler logoutHandler = new LogoutHandler();
    private ListGamesHandler listGamesHandler = new ListGamesHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();


    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files (this works)
        Spark.externalStaticFileLocation("web");

        // Register handlers for each endpoint using the method reference syntax
        routes();
    }

    private void routes() {
        Spark.delete("/db", this::clearApplication);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("game", this::createGame);
        Spark.put("/game", this::joinGame);
    }

    private Object clearApplication(Request request, Response response) {
        return clearApplicationHandler.clearApplication(request, response);
    }

    private Object register(Request request, Response response) {
        return registerHandler.register(request, response);
    }

    private Object login(Request request, Response response) {
        return loginHandler.login(request, response);
    }

    private Object logout(Request request, Response response) {
        return logoutHandler.logout(request, response);
    }

    private Object listGames(Request request, Response response) {
        return listGamesHandler.listGames(request, response);
    }

    private Object createGame(Request request, Response response) {
        return createGameHandler.createGame(request, response);
    }

    private Object joinGame(Request request, Response response) {
        return joinGameHandler.joinGame(request, response);
    }
}



