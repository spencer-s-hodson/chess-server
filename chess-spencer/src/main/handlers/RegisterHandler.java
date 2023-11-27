package handlers;

import spark.Response;
import spark.Request;
import services.RegisterService;
import requests.RegisterRequest;
import responses.RegisterResponse;


public class RegisterHandler extends Handler {
    private final RegisterService registerService = new RegisterService();

    public String register(Request request, Response response) {
        RegisterRequest bodyObj = makeObj(request, RegisterRequest.class);
        response.type("application/json");

        RegisterResponse result = registerService.register(bodyObj);

        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }

        return makeJson(result);
    }
}
