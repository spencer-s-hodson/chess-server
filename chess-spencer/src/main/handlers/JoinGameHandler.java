package handlers;

import services.JoinGameService;
import services.requests.JoinGameRequest;
import services.responses.JoinGameResponse;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends Handler {
    private final JoinGameService joinGameService = new JoinGameService();

    public Object joinGame(Request request, Response response) {
        JoinGameRequest bodyObj = makeObj(request, JoinGameRequest.class);
        response.type("application/json");

        String authToken = getHeader(request);
        JoinGameResponse result = joinGameService.joinGame(bodyObj, authToken);

        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }

        return makeJson(result);
    }
}
