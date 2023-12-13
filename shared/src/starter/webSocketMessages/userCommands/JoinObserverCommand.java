package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserverCommand extends UserGameCommand {
    private final int gameID;
    private final ChessGame.TeamColor playerColor;
    private final String authToken;
    public JoinObserverCommand(int gameID, ChessGame.TeamColor playerColor, String authToken) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.authToken = authToken;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public String getAuthToken(){
        return authToken;
    }
}
