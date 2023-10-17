package services;

import models.AuthToken;
import requests.RegisterRequest;
import results.RegisterResult;

public class RegisterService {
    public RegisterResult register(RegisterRequest r) {
        return new RegisterResult(200, r.getUsername(), new AuthToken("foobar", r.getUsername()));
    }
}
