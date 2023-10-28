package handlers;

import services.ListGamesService;
import services.responses.ListGamesResponse;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends Handler {
    private final ListGamesService listGamesService = new ListGamesService();

    public Object listGames(Request request, Response response) {
        response.type("application/json");

        String authToken = getHeader(request);
        ListGamesResponse result = listGamesService.listGames(authToken);

        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }

        return makeJson(result);
    }
}
