package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializationHelper {
    public static String serializeChessGame(chess.Game game) {
        Gson gson = new Gson();
        return gson.toJson(game);
    }

    // register type adapter?
    // pass in a class, when you see this class, it's actually this other class, called on the GSON object
    public static chess.Game deserializeChessGame(String gameData) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(chess.Game.class, new ChessGameAdapter());
        builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
        builder.registerTypeAdapter(chess.ChessPiece.class, new ChessPieceAdapter());
        Gson gson = builder.create();
        return gson.fromJson(gameData, chess.Game.class);
    }
}
