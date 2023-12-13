package websockets;

import chess.ChessGame;
import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.userCommands.JoinPlayerCommand;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    private Session session;

    public WebSocketFacade() throws Exception {
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    System.out.println(message);

                }
            });
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // sounds like i can ignore this or something
    }

    public void joinPlayer(int gameID, ChessGame.TeamColor teamColor, String authToken) throws Exception {
        try {
            // creates the join player command
            JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(gameID, teamColor, authToken);

            // what is this doing: this is sending the text to the ws server i think?
            String message = new Gson().toJson(joinPlayerCommand);
            this.session.getBasicRemote().sendText(message);

            // how do i get the load game message back?

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }
    // THIS IS WHERE ALL THE USER COMMANDS WILL BE
}
