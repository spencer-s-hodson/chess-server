package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.JoinObserverCommand;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.Endpoint;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;

@WebSocket
public class GameConnectionHandler {
    private static final GameDAO gameDAO;

    static {
        try {
            gameDAO = new GameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final ConnectionManager connectionManager = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        // create the superclass, so we know which command type it is
        UserGameCommand gameCommand = new Gson().fromJson(message, UserGameCommand.class);

        // use switch cases to decide what kind of command we are using
        switch  (gameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinGame(session, message);
//            case JOIN_OBSERVER -> joinGame(session, message, JoinObserverCommand.class);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leave(message);
            case RESIGN -> resign(message);
        }

//
//
//        // use a switch case
//        if (gameCommand.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
//            /*
//                1. Server sends a LOAD_GAME message back to the root client.
//                2. Server sends a Notification message to all other clients in that game informing them what color the root client is joining as.
//             */
//            JoinPlayerCommand joinPlayerCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
//            // first validate skip this first
//            // need a session to send a message
//            // this.session.getBasicRemote().sendText(message);
//            // session.getRemote().sendString(message); where the message is a json of the message
        }

    // still need to do the verification stuff here!
    private void joinGame(Session session, String message) throws IOException, DataAccessException {
        // stuff im using here
        JoinPlayerCommand joinCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
        ChessGame.TeamColor teamColor = joinCommand.getPlayerColor();
        String authToken = joinCommand.getAuthToken();
        int gameID = joinCommand.getGameID();

        // 1. Server sends a LOAD_GAME message back to the root client.
        String gameAsJson = new Gson().toJson(gameDAO.findGame(gameID));
        LoadGameMessage loadGameMessage = new LoadGameMessage(gameAsJson);
//        NotificationMessage gameAsJsonNotification = new NotificationMessage(gameAsJson);
        connectionManager.send(authToken, loadGameMessage);

        // 2. Server sends a Notification message to all other clients in that game informing them what color the root client is joining as.
        connectionManager.add(authToken, session);
        String messageToAll = "Someone has joined game #%d as %s".formatted(gameID, teamColor);
        NotificationMessage notificationMessage = new NotificationMessage(messageToAll);
        connectionManager.broadcast(authToken, notificationMessage);
    }

    private void makeMove(String message) {
    }

    private void leave(String message) {
    }

    private void resign(String message) {
    }
}
