package webSocketMessages.userCommands;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private int gameID;
    private ChessMove move;
    public MakeMoveCommand(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }
}
