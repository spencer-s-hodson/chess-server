package services;

import models.AuthToken;
import requests.LoginRequest;
import results.LoginResult;

public class LoginService {
    public LoginResult login(LoginRequest r) {
        return new LoginResult(200, r.getUsername(), new AuthToken("foobar", r.getUsername()));
    }
}
