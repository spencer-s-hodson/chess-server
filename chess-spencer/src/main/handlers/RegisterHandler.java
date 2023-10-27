package handlers;

import services.RegisterService;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;
import spark.*;

public class RegisterHandler extends Handler {
    private RegisterService registerService = new RegisterService();

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
