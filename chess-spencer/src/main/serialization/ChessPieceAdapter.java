package serialization;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {

    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var jsonObject = jsonElement.getAsJsonObject();
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.valueOf(jsonObject.get("teamColor").getAsString());
        ChessPiece.PieceType pieceType = ChessPiece.PieceType.valueOf(jsonObject.get("pieceType").getAsString());
        return switch (pieceType) {
            case KING -> new King(teamColor);
            case QUEEN -> new Queen(teamColor);
            case ROOK -> new Rook(teamColor);
            case BISHOP -> new Bishop(teamColor);
            case KNIGHT -> new Knight(teamColor);
            case PAWN -> new Pawn(teamColor);
        };
    }
}
