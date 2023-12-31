package handlers;

import services.LogoutService;
import responses.LogoutResponse;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler {
    public LogoutService logoutService = new LogoutService();
    public String logout(Request request, Response response) {
        response.type("application/json");
        String authToken = getHeader(request);
        LogoutResponse result = logoutService.logout(authToken);
        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }
        return makeJson(result);
    }
}
