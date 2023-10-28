package handlers;

import services.ClearApplicationService;
import spark.Response;

public class ClearApplicationHandler extends Handler {
    public ClearApplicationService clearApplicationService = new ClearApplicationService();
    public String clearApplication(Response response) {
        response.type("application/json");
        return makeJson(clearApplicationService.clearApplication());
    }
}
