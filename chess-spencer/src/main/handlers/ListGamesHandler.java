package handlers;

import services.ListGamesService;
import services.requests.ListGamesRequest;
import services.requests.RegisterRequest;
import services.responses.ListGamesResponse;
import services.responses.LogoutResponse;
import spark.Request;
import spark.Response;


public class ListGamesHandler extends Handler {
    private ListGamesService listGamesService = new ListGamesService();

    public Object listGames(Request request, Response response) {
        response.type("application/json");

        // this is some authToken
        String authToken = getHeader(request);

        ListGamesResponse result = listGamesService.listGames(authToken);

        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }

        return makeJson(result);
    }
}
