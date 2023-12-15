package serialization;

import chess.ChessPosition;
import chess.Position;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPositionAdapter implements JsonDeserializer<ChessPosition> {
    @Override
    public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int row = jsonObject.get("row").getAsInt();
        int col = jsonObject.get("column").getAsInt();
        return new Position(row, col);
    }
}
