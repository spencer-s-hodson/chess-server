package services;

import results.LogoutResult;

public class LogoutService {
    LogoutResult logout() {
        return new LogoutResult(401, "message");
    }
}
