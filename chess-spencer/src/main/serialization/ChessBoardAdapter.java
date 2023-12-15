package serialization;

import chess.Board;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<chess.Board> {
    @Override
    public Board deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        chess.ChessPiece[][] board = jsonDeserializationContext.deserialize(jsonObject.get("board"), chess.ChessPiece[][].class);
        return new chess.Board(board);
    }
}
