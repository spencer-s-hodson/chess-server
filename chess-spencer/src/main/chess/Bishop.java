package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Bishop implements ChessPiece {
    private ChessGame.TeamColor teamColor;
    private PieceType pieceType;

    public Bishop(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
        this.pieceType = PieceType.BISHOP;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves = new HashSet<>();

        int[] rowDirs = { -1, 1, 1, -1 }; // DownLeft, UpRight, RightDown, LeftUp
        int[] colDirs = { -1, 1, -1, 1 };

        for (int i = 0; i < 4; i++) {
            for (int distance = 1; distance < 8; distance++) { // Bishop can move at most 7 squares in any direction
                int newRow = myPosition.getRow() + rowDirs[i] * distance;
                int newCol = myPosition.getColumn() + colDirs[i] * distance;

                if (isValidSquare(newRow, newCol)) {
                    ChessPosition newPosition = new Position(newRow, newCol);
                    ChessPiece pieceAtNewPosition = board.getPiece(newPosition);

                    if (pieceAtNewPosition == null) {
                        validMoves.add(new Move(myPosition, newPosition));
                    } else if (pieceAtNewPosition.getTeamColor() != teamColor) {
                        validMoves.add(new Move(myPosition, newPosition));
                        break; // Bishop can't go beyond an opponent's piece
                    } else {
                        break; // Bishop can't go beyond its own piece
                    }
                } else {
                    break; // Bishop can't go beyond the board
                }
            }
        }
        return validMoves;
    }

    private boolean isValidSquare(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bishop bishop = (Bishop) o;
        return teamColor == bishop.teamColor && pieceType == bishop.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}
