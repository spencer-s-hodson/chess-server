package services;

import requests.ListGamesRequest;
import results.ListGamesResult;

public class ListGamesService {
    public ListGamesResult listGames(ListGamesRequest r) {
        return new ListGamesResult();
    }
}
