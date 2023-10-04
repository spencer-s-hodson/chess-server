package chess;

import java.util.Collection;

public class King implements ChessPiece {
    private ChessGame.TeamColor teamColor;
    public King(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
