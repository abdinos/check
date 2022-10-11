package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class PromotionAttackMovement extends Movement {

    public PromotionAttackMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition,
                                   final ChessPiece chessPieceAttacked, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, chessPieceAttacked, chessPiecePromoted, futurePosition, true, true, false, false);
    }
}
