package chess.board;

import chess.chessPiece.ChessPiece;

public final class NormalMovement extends Movement{

    public NormalMovement(final BoardGame boardGame, final ChessPiece chessPiece, final int futurePosition) {
        super(boardGame, chessPiece, futurePosition);
    }
}
