package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Rook implements ChessPiece {
    private ChessGame.TeamColor teamColor;
    public Rook(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves = new HashSet<>();

        int[] rowDirs = { -1, 1, 0, 0 }; // Up, Down, Left, Right
        int[] colDirs = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            for (int distance = 1; distance <= 7; distance++) { // Rook can move at most 7 squares in any direction
                int newRow = myPosition.getRow() + rowDirs[i] * distance;
                int newCol = myPosition.getColumn() + colDirs[i] * distance;

                if (isValidSquare(newRow, newCol)) {
                    ChessPosition newPosition = new Position(newRow, newCol);
                    ChessPiece pieceAtNewPosition = board.getPiece(newPosition);

                    if (pieceAtNewPosition == null) {
                        validMoves.add(new Move(myPosition, newPosition));
                    } else if (pieceAtNewPosition.getTeamColor() != teamColor) {
                        validMoves.add(new Move(myPosition, newPosition));
                        break; // Rook can't go beyond an opponent's piece
                    } else {
                        break; // Rook can't go beyond its own piece
                    }
                } else {
                    break; // Rook can't go beyond the board
                }
            }
        }
        return validMoves;
    }

    private boolean isValidSquare(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
}


