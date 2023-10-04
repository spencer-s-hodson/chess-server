package chess;

import java.util.Collection;

public class Knight implements ChessPiece {
    private ChessGame.TeamColor teamColor;
    public Knight(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
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
        return null;
    }
}
