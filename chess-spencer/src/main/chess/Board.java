package chess;

import java.util.Arrays;

public class Board implements ChessBoard { // can exist without Board DONE DO NOT TOUCH
    ChessPiece[][] board;

    public Board() {
        board = new ChessPiece[8][8];
    }

    public Board(ChessPiece[][] board) {
        this.board = board;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) { // if i want to remove a piece, just call this method and use null for the ChessPiece argument
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }

        /** Was I supposed to use addPiece here */
        // rooks
        board[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        board[0][7] = new Rook(ChessGame.TeamColor.WHITE);
        board[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        board[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        // knights
        board[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        board[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][6] = new Knight(ChessGame.TeamColor.BLACK);

        // bishops
        board[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        board[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][5] = new Bishop(ChessGame.TeamColor.BLACK);

        // queens
        board[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        board[7][3] = new Queen(ChessGame.TeamColor.BLACK);

        // kings
        board[0][4] = new King(ChessGame.TeamColor.WHITE);
        board[7][4] = new King(ChessGame.TeamColor.BLACK);

        // pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null && board1.board[row][col] == null) continue;
                if (!board[row][col].equals(board1.board[row][col])) return false;
            }
        }
        return true;
    }
}
