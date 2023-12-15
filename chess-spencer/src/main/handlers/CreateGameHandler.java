package handlers;

import services.CreateGameService;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler {
    private final CreateGameService createGameService = new CreateGameService();

    public String createGame(Request request, Response response) {
        CreateGameRequest bodyObj = makeObj(request, CreateGameRequest.class);
        response.type("application/json");
        String authToken = getHeader(request);
        CreateGameResponse result = createGameService.createGame(bodyObj, authToken);
        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }
        return makeJson(result);
    }
}
