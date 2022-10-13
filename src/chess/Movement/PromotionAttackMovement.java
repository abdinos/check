package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class PromotionAttackMovement extends Movement {

    /**
     * A pawn move by attacking another chess piece and promoting
     */
    public PromotionAttackMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition,
                                   final ChessPiece chessPieceAttacked, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, chessPieceAttacked, chessPiecePromoted, futurePosition, true, true, false, false, false);
    }
}
