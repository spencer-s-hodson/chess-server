package serialization;

import chess.ChessGame;
import chess.Game;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameAdapter implements JsonDeserializer<chess.Game> {
    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        chess.Board board = jsonDeserializationContext.deserialize(jsonObject.get("board"), chess.Board.class);
        ChessGame.TeamColor currTurn = ChessGame.TeamColor.valueOf(jsonObject.get("currTurn").getAsString());
        boolean isOver = jsonObject.get("isOver").getAsBoolean();
        return new chess.Game(board, currTurn, isOver);
    }
}
