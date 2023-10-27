package handlers;

import services.LoginService;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.responses.LoginResponse;
import services.responses.RegisterResponse;
import spark.*;

public class LoginHandler extends Handler {
    public LoginService loginService = new LoginService();

    public String login(Request request, Response response) {
        LoginRequest bodyObj = makeObj(request, LoginRequest.class);
        response.type("application/json");

        LoginResponse result = loginService.login(bodyObj);

        if (result.getMessage() != null) {
            response.status(getStatus(result.getMessage()));
        }

        return makeJson(result);
    }
}
