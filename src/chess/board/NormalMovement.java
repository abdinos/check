package chess.board;

import chess.chessPiece.ChessPiece;

public class NormalMovement extends Movement{

    public NormalMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition) {
        super(boardGame, chessPieceMoved, null, null, futurePosition, false, false, false);
    }
}
