package handlers;

import services.ClearApplicationService;
import spark.Request;
import spark.Response;

public class ClearApplicationHandler extends Handler {
    public ClearApplicationService clearApplicationService = new ClearApplicationService();
    public String clearApplication(Request request, Response response) {
        response.type("application/json");

        return makeJson(clearApplicationService.clearApplication());
    }
}
