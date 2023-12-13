package ui;

import server.ServerFacade;
import websockets.WebSocketFacade;

public class GameplayUI {
    public static final ServerFacade server = new ServerFacade();
    public static final WebSocketFacade ws;

    static {
        try {
            ws = new WebSocketFacade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawChessBoardWithWhiteOnTop() {
        String[][] board = initializeChessBoard(true); // true for white on top
        printBoard(board);
    }

    public static void drawChessBoardWithBlackOnTop() {
        String[][] board = initializeChessBoard(false); // false for black on top
        printBoard(board);
    }

    private static String[][] initializeChessBoard(boolean isWhiteOnTop) {
        String[][] board = new String[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    String color = (isWhiteOnTop == (i == 0)) ? EscapeSequences.SET_TEXT_COLOR_WHITE : EscapeSequences.SET_TEXT_COLOR_BLACK;
                    board[i][j] = getPiece(j, color);
                } else if (i == 1 || i == 6) {
                    String color = (isWhiteOnTop == (i == 1)) ? EscapeSequences.SET_TEXT_COLOR_WHITE : EscapeSequences.SET_TEXT_COLOR_BLACK;
                    board[i][j] = color + EscapeSequences.WHITE_PAWN;
                } else {
                    board[i][j] = EscapeSequences.EMPTY;
                }
            }
        }
        return board;
    }

    private static String getPiece(int column, String color) {
        switch (column) {
            case 0:
            case 7:
                return color + EscapeSequences.WHITE_ROOK;
            case 1:
            case 6:
                return color + EscapeSequences.WHITE_KNIGHT;
            case 2:
            case 5:
                return color + EscapeSequences.WHITE_BISHOP;
            case 3:
                return color + EscapeSequences.WHITE_QUEEN;
            case 4:
                return color + EscapeSequences.WHITE_KING;
            default:
                return EscapeSequences.EMPTY;
        }
    }

    private static void printBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String bgColor = (i + j) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY : EscapeSequences.SET_BG_COLOR_DARK_GREY;
                System.out.print(bgColor + board[i][j] + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println();
        }
        System.out.println(); // Add a blank line between the two boards
    }

    public static void main(String[] args) {
        drawChessBoardWithWhiteOnTop();
        drawChessBoardWithBlackOnTop();
    }
}
