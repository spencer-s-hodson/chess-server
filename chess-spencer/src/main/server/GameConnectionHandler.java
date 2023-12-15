package server;

import chess.*;
import com.google.gson.*;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;
import java.io.IOException;

/**
 * The handler class for all Websocket communications.
 */
@WebSocket
public class GameConnectionHandler {
    private final ConnectionManager connectionManager = new ConnectionManager();
    /**
     * The game access object that pulls and updates game data from the database.
     */
    private static final GameDAO gameDAO;
    /**
     * The game access object that pulls and updates authToken data from the database.
     */
    private static final AuthDAO authDAO;
    static {
        try {
            gameDAO = new GameDAO();
            authDAO = new AuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand gameCommand = new Gson().fromJson(message, UserGameCommand.class);
        try {
            if (connectionManager.getConnections().get(gameCommand.getAuthString()) == null) {
                connectionManager.add(gameCommand.getAuthString(), new Connection(gameCommand.getAuthString(), session));
            }
            Connection connection = connectionManager.getConnections().get(gameCommand.getAuthString());
            if (gameCommand.getCommandType() != null) {
                switch (gameCommand.getCommandType()) {
                    case JOIN_PLAYER -> joinGameAsPlayer(connection, message);
                    case JOIN_OBSERVER -> joinGameAsObserver(connection, message);
                    case MAKE_MOVE -> makeMove(connection, message);
                    case LEAVE -> leave(message);
                    case RESIGN -> resign(message);
                }
            } else {
                throw new Exception("ERROR: you need to connect to a game!");
            }
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
    }

    private void joinGameAsPlayer(Connection connection, String message) throws Exception {
        JoinPlayerCommand joinCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
        ChessGame.TeamColor teamColor = joinCommand.getPlayerColor();
        String authToken = joinCommand.getAuthString();
        int gameID = joinCommand.getGameID();
        models.Game game = gameDAO.findGame(gameID);
        // bad gameID
        if (game == null) {
            throw new Exception("Error: this game does not exist");
        }
        // bad authToken
        try {
            authDAO.findAuthToken(authToken);
        } catch (DataAccessException e) {
            throw new Exception("Error: this authToken doesn't exist");
        }
        // empty game
        String username = authDAO.findAuthToken(authToken).getUsername();
        if (teamColor != null && (teamColor == ChessGame.TeamColor.WHITE && !game.getWhiteUsername().equals(username) || teamColor == ChessGame.TeamColor.BLACK && !game.getBlackUsername().equals(username))) {
            throw new Exception("Error: You are not a player in this game");
        }
        connection.setGame(game);
        String gameAsJson = new Gson().toJson(gameDAO.findGame(gameID));
        LoadGameMessage loadGameMessage = new LoadGameMessage(gameAsJson);
        connectionManager.add(authToken, connection);
        connectionManager.send(authToken, loadGameMessage);
        String messageToAll = "Someone has joined game #%d as %s".formatted(gameID, teamColor);
        NotificationMessage notificationMessage = new NotificationMessage(messageToAll);
        connectionManager.broadcast(authToken, notificationMessage);
    }



    private void makeMove(Connection connection, String message) throws Exception {
        MakeMoveCommand makeMoveCommand = createGsonSerializer().fromJson(message, MakeMoveCommand.class);
        String authToken = makeMoveCommand.getAuthString();
        ChessMove move = makeMoveCommand.getMove();
        models.Game modelGame = gameDAO.findGame(makeMoveCommand.getGameID());
        chess.Game chessGame = modelGame.getGame();
        String player = null;
        switch (chessGame.getTeamTurn()) {
            case WHITE -> player = modelGame.getWhiteUsername();
            case BLACK -> player = modelGame.getBlackUsername();
        }
        // check for the right player
        if (!authDAO.findAuthToken(authToken).getUsername().equals(player))
            throw new Exception("It is not your turn");
        // check that the game is still going
        if (chessGame.getIsOver()) {
            throw new Exception("Moves cannot be made when the game is already over");
        }
        chessGame.makeMove(move);
        if (chessGame.isInCheckmate(chessGame.getTeamTurn())) {
            chessGame.setIsOver(true);
        }
        gameDAO.updateGame(modelGame);
        connection.setGame(modelGame);
        connectionManager.sendGameToAll(new LoadGameMessage(createGsonSerializer().toJson(chessGame)));
        connectionManager.broadcast(authToken, new NotificationMessage("a move was made!"));
    }

    private void leave(String message) throws IOException {
        LeaveCommand leaveCommand = new Gson().fromJson(message, LeaveCommand.class);
        String authToken = leaveCommand.getAuthString();
        connectionManager.remove(authToken);
        String messageToAll = "someone left the game!";
        NotificationMessage notificationMessage = new NotificationMessage(messageToAll);
        connectionManager.broadcast(authToken, notificationMessage);
    }

    private void resign(String message) throws Exception {
        ResignCommand resignCommand = new Gson().fromJson(message, ResignCommand.class);
        int gameID = resignCommand.getGameID();
        models.Game game = gameDAO.findGame(gameID);
        String player = authDAO.findAuthToken(resignCommand.getAuthString()).getUsername();
        // make sure the player is actually playing
        if (!player.equals(game.getWhiteUsername()) && !player.equals(game.getBlackUsername())) {
            throw new Exception("You are an observer");
        }
        // make sure that the game is still going
        if (game.getGame().getIsOver()) {
            throw new Exception("The game is already over");
        }
        game.getGame().setIsOver(true);
        gameDAO.updateGame(game);
        connectionManager.broadcast(null, new NotificationMessage("The game is over"));
    }

    private void joinGameAsObserver(Connection connection, String message) throws Exception {
        JoinObserverCommand joinCommand = new Gson().fromJson(message, JoinObserverCommand.class);
        int gameID = joinCommand.getGameID();
        String authToken = joinCommand.getAuthString();
        models.Game game = gameDAO.findGame(gameID);
        // bad gameID
        if (game == null) {
            throw new Exception("Error: this game does not exist");
        }
        // bad authToken
        try {
            authDAO.findAuthToken(authToken);
        } catch (DataAccessException e) {
            throw new Exception("Error: this authToken doesn't exist");
        }
        connection.setGame(game);
        String gameAsJson = new Gson().toJson(gameDAO.findGame(gameID));
        LoadGameMessage loadGameMessage = new LoadGameMessage(gameAsJson);
        connectionManager.add(authToken, connection);
        connectionManager.send(authToken, loadGameMessage);
        String messageToAll = "Someone has joined game #%d as an observer".formatted(gameID);
        NotificationMessage notificationMessage = new NotificationMessage(messageToAll);
        connectionManager.broadcast(authToken, notificationMessage);
    }

    public static Gson createGsonSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessGame.class,
                (JsonDeserializer<ChessGame>) (el, type, ctx) -> ctx.deserialize(el, chess.Game.class));
        gsonBuilder.registerTypeAdapter(ChessBoard.class,
                (JsonDeserializer<ChessBoard>) (el, type, ctx) -> ctx.deserialize(el, Board.class));
        gsonBuilder.registerTypeAdapter(ChessPiece.class,
                (JsonDeserializer<ChessPiece>) (el, type, ctx) -> ctx.deserialize(el, ChessPiece.class));
        gsonBuilder.registerTypeAdapter(ChessMove.class,
                (JsonDeserializer<ChessMove>) (el, type, ctx) -> ctx.deserialize(el, Move.class));
        gsonBuilder.registerTypeAdapter(ChessPosition.class,
                (JsonDeserializer<ChessPosition>) (el, type, ctx) -> ctx.deserialize(el, Position.class));
        gsonBuilder.registerTypeAdapter(ChessPiece.class,
                (JsonDeserializer<ChessPiece>) (el, type, ctx) -> {
                    ChessPiece chessPiece = null;
                    if (el.isJsonObject()) {
                        String pieceType = el.getAsJsonObject().get("pieceType").getAsString();
                        switch (ChessPiece.PieceType.valueOf(pieceType)) {
                            case PAWN -> chessPiece = ctx.deserialize(el, Pawn.class);
                            case ROOK -> chessPiece = ctx.deserialize(el, Rook.class);
                            case KNIGHT -> chessPiece = ctx.deserialize(el, Knight.class);
                            case BISHOP -> chessPiece = ctx.deserialize(el, Bishop.class);
                            case QUEEN -> chessPiece = ctx.deserialize(el, Queen.class);
                            case KING -> chessPiece = ctx.deserialize(el, King.class);
                        }
                    }
                    return chessPiece;
                });
        return gsonBuilder.create();
    }
}
