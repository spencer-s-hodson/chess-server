package results;

import models.AuthToken;

public class RegisterResult {
    private int statusCode;
    private String username;
    private AuthToken authToken;
    private String message;
    public RegisterResult(int statusCode, String username, AuthToken authToken) {
        this.statusCode = statusCode;
        this.username = username;
        this.authToken = authToken;
    }

    public RegisterResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
