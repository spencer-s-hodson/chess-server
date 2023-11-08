package serialization;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {

    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var jsonObject = jsonElement.getAsJsonObject();

        // extract all elements, return a new piece with the same elements
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.valueOf(jsonObject.get("teamColor").getAsString());
        ChessPiece.PieceType pieceType = ChessPiece.PieceType.valueOf(jsonObject.get("pieceType").getAsString());

        switch (pieceType) {
            case KING:
                return new King(teamColor);
            case QUEEN:
                return new Queen(teamColor);
            case ROOK:
                return new Rook(teamColor);
            case BISHOP:
                return new Bishop(teamColor);
            case KNIGHT:
                return new Knight(teamColor);
            case PAWN:
                return new Pawn(teamColor);
        }
        return null;
    }
}
