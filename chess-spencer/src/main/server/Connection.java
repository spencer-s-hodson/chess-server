package server;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    private String authToken;
    private Session session;

    public Connection(String authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }
     public void send(String message) throws IOException {
        session.getRemote().sendString(message);
     }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
