package serialization;

import chess.ChessGame;
import chess.Game;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameAdapter implements JsonDeserializer<chess.Game> {
    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // give context to each element as necessary, boards are handled by ChessBoardAdapter, pieces are handled by ChessPieceAdapter
        chess.Board board = jsonDeserializationContext.deserialize(jsonObject.get("board"), chess.Board.class);
        ChessGame.TeamColor currTurn = ChessGame.TeamColor.valueOf(jsonObject.get("currTurn").getAsString());

        return new chess.Game(board, currTurn);
    }
}
