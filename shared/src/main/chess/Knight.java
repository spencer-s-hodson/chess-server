package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Knight implements ChessPiece {
    private final ChessGame.TeamColor teamColor;
    private final PieceType pieceType;

    public Knight(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
        this.pieceType = PieceType.KNIGHT;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves = new HashSet<>();

        int[] rowDirs = { 1, 2, 2, 1, -1, -2, -2, -1 };
        int[] colDirs = { -2, -1, 1, 2, -2, -1, 1, 2 };

        for (int i = 0; i < 8; i++) {
            int newRow = myPosition.getRow() + rowDirs[i];
            int newCol = myPosition.getColumn() + colDirs[i];

            if (isValidSquare(newRow, newCol)) {
                ChessPosition newPosition = new Position(newRow, newCol);
                ChessPiece pieceAtNewPosition = board.getPiece(newPosition);

                if (pieceAtNewPosition == null) {
                    validMoves.add(new Move(myPosition, newPosition));
                } else if (pieceAtNewPosition.getTeamColor() != teamColor) {
                    validMoves.add(new Move(myPosition, newPosition));
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
        Knight knight = (Knight) o;
        return teamColor == knight.teamColor && pieceType == knight.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}
