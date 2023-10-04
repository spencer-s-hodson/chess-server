package chess;

import java.util.Collection;

public class Pawn implements ChessPiece {

    private ChessGame.TeamColor teamColor;
    public Pawn(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
