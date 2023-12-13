package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Session session) {
        Connection connection = new Connection(authToken, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String excludeAuthToken, NotificationMessage notificationMessage) throws IOException {
        ArrayList<Connection> removeList = new ArrayList<>();
        for (Connection c : connections.values()) {
            if (c.getSession().isOpen()) {
                if (!c.getAuthToken().equals(excludeAuthToken)) {
                    c.send(notificationMessage.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.getAuthToken());
        }
    }

    public void send(String authToken, ServerMessage message) throws IOException {
        Connection connection = connections.get(authToken);
        if (connection.getSession().isOpen()) {
            connection.send(new Gson().toJson(message));
        }
    }
}
