package server;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Connection connection) {
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
                    c.send(new Gson().toJson(notificationMessage));
                }
            } else {
                removeList.add(c);
            }
        }

        // Get rid of closed connections.
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

    public void sendGameToAll(LoadGameMessage message) throws IOException {
        for (Connection c : connections.values()) {
            c.send(new Gson().toJson(message));
        }
    }

    public ConcurrentHashMap<String, Connection> getConnections() {
        return connections;
    }
}
