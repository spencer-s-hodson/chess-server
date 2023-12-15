package websockets;

import chess.ChessGame;
import com.google.gson.Gson;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.LeaveCommand;
import webSocketMessages.userCommands.MakeMoveCommand;
import chess.ChessMove;
import webSocketMessages.userCommands.ResignCommand;
import javax.management.Notification;
import javax.websocket.*;
import java.net.URI;

public class WebSocketFacade extends Endpoint {
    private final Session session;

    public WebSocketFacade() throws Exception {
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            this.session.addMessageHandler((MessageHandler.Whole<String>) message -> {
                Notification notification = new Gson().fromJson(message, Notification.class);
                System.out.println(new Gson().toJson(notification));
            });
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void joinPlayer(String authToken, int gameID, ChessGame.TeamColor teamColor) throws Exception {
        try {
            JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(gameID, teamColor, authToken);
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayerCommand));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void leave(String authToken, int gameID) throws Exception {
        try {
            LeaveCommand leaveCommand = new LeaveCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(leaveCommand));
            this.session.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void makeMove(String authToken,int gameID, ChessMove move) throws Exception {
        try {
            MakeMoveCommand makeMoveCommand = new MakeMoveCommand(authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(makeMoveCommand));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void resign(String authToken, int gameID) throws Exception {
        try {
            ResignCommand resignCommand = new ResignCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(resignCommand));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
