package serialization;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessMoveAdapter implements JsonDeserializer<ChessMove> {
    @Override
    public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ChessPosition startPosition = jsonDeserializationContext.deserialize(jsonObject.get("startPosition"), ChessPosition.class);
        ChessPosition endPosition = jsonDeserializationContext.deserialize(jsonObject.get("endPosition"), ChessPosition.class);
        // Assuming promotionPiece is optional and can be null
        ChessPiece.PieceType promotionPiece = jsonDeserializationContext.deserialize(jsonObject.get("promotionPiece"), ChessPiece.PieceType.class);
        return new Move(startPosition, endPosition, promotionPiece);
    }
}
