package ui;

import chess.*;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import websockets.WebSocketFacade;
import java.util.Scanner;

public class GameplayUI {
    public static final GameDAO gameDAO;
    public static final WebSocketFacade ws;
    static {
        try {
            gameDAO = new GameDAO();
            ws = new WebSocketFacade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void joinGame(String authToken, int gameID) throws Exception {
        drawChessBoard(gameID);
        String helpString = """
                           help - with possible commands
                           redraw_chessboard - redraws the chess board upon the user’s request
                           leave - remove yourself from the game
                           make_move <START> <FINISH> - make your move!
                           resign - forfeit the game
                           highlight_legal_moves<POSITION> - see what moves are legal
                        """;
        String curr = "[JOINED_GAME] >>> ";

        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(curr);
            String response = scanner.next();
            System.out.println(response);

            switch (response.toLowerCase()) {
                case "help":
                    // print out the help options again (not WS)
                    System.out.println(helpString);
                    break;
                case "redraw_chessboard":
                    // redraws the chessboard (not WS)
                    drawChessBoard(gameID);
                    break;
                case "leave":
                    // leaves the game and goes back to the PostLoginUi (ws)
                    leave(authToken, gameID);
                    break label;
                case "make_move":
                    // calls on make move from the WebsocketHandler (ws)
                    ChessPosition start = parsePosition(scanner.next());
                    ChessPosition end = parsePosition(scanner.next());
                    ChessMove move = new Move(start, end);
                    makeMove(authToken, gameID, move);
                    break;
                case "resign":
                    // forfeits the game (ws)
                    resign(authToken, gameID);
                    break;
                case "highlight_legal_moves":
                    // prints out the legal moves for a certain piece
                    // TODO write all of the code that prints a board with all of the highlighted moves
                    highlightMoves();
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    public static void drawChessBoard(int gameID) throws DataAccessException {
        chess.ChessBoard board = gameDAO.findGame(gameID).getGame().getBoard();
        System.out.println(board.toString());
    }

    public void leave(String authToken, int gameID) throws Exception {
        ws.leave(authToken, gameID);
        PostLoginUI postLoginUI = new PostLoginUI();
        postLoginUI.login();
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws Exception {
        ws.makeMove(authToken, gameID, move);
        drawChessBoard(gameID);
    }

    private ChessPosition parsePosition(String position) {
        position = position.toLowerCase();
        var col = position.charAt(0) - 'a' + 1;
        var row = Character.getNumericValue(position.charAt(1));
        return new Position(row, col);
    }

    public void resign(String authToken, int gameID) throws Exception {
        ws.resign(authToken, gameID);
    }

    public void highlightMoves() {
        // TODO create a method similar to the ones that prints the board, but highlight the valid moves
    }
}
