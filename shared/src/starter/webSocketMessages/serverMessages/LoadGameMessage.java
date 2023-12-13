package webSocketMessages.serverMessages;

import chess.Game;

public class LoadGameMessage extends ServerMessage {
    private String game;

    public LoadGameMessage(String game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    public String getGame() {
        return this.game;
    }
}
