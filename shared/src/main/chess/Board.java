package chess;

import models.ES;

public class Board implements ChessBoard {
    ChessPiece[][] board;

    public Board() {
        board = new ChessPiece[8][8];
    }

    public Board(ChessPiece[][] board) {
        this.board = board;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
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
    public String toString() {
        StringBuilder printedBoard = new StringBuilder();
        for (int i = 0; i < 8; i ++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                String pieceRepresentation = getPieceRepresentation(piece);

                String bgColor = (i + j) % 2 == 0 ? ES.SET_BG_COLOR_LIGHT_GREY : ES.SET_BG_COLOR_DARK_GREY;
                if (piece != null) {
                    if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                        row.append(bgColor).append(pieceRepresentation).append(ES.SET_BG_COLOR_BLACK).append(ES.RESET_BG_COLOR);
                    } else {
                        row.append(bgColor).append(pieceRepresentation).append(ES.RESET_BG_COLOR);
                    }
                } else {
                    row.append(bgColor).append(pieceRepresentation).append(ES.RESET_BG_COLOR);
                }
            }
            printedBoard.append(row).append("\n");
        }
        return printedBoard.toString();
    }

    private String getPieceRepresentation(ChessPiece piece) {
        if (piece == null) {
            return ES.EMPTY;
        }
        return switch (piece.getTeamColor()) {
            case WHITE -> getWhitePieceRepresentation(piece.getPieceType());
            case BLACK -> getBlackPieceRepresentation(piece.getPieceType());
        };
    }

    private String getWhitePieceRepresentation(ChessPiece.PieceType pieceType) {
        return switch (pieceType) {
            case KING -> ES.WHITE_KING;
            case QUEEN -> ES.WHITE_QUEEN;
            case ROOK -> ES.WHITE_ROOK;
            case BISHOP -> ES.WHITE_BISHOP;
            case KNIGHT -> ES.WHITE_KNIGHT;
            case PAWN -> ES.WHITE_PAWN;
        };
    }

    private String getBlackPieceRepresentation(ChessPiece.PieceType pieceType) {
        return switch (pieceType) {
            case KING -> ES.BLACK_KING;
            case QUEEN -> ES.BLACK_QUEEN;
            case ROOK -> ES.BLACK_ROOK;
            case BISHOP -> ES.BLACK_BISHOP;
            case KNIGHT -> ES.BLACK_KNIGHT;
            case PAWN -> ES.BLACK_PAWN;
        };
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
