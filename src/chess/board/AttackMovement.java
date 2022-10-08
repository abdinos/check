package chess.board;

import chess.chessPiece.ChessPiece;

public class AttackMovement extends Movement{

    public AttackMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPieceAttacked) {
        super(boardGame, chessPieceMoved, chessPieceAttacked, null, futurePosition, true, false, false);
    }
}
