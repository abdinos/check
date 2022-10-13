package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class PromotionAttackCheckMovement extends Movement {

    /**
     * A pawn move by attacking another chess piece, make the enemy king in check state and promoting
     */
    public PromotionAttackCheckMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition,
                                        final ChessPiece chessPieceAttacked, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, chessPieceAttacked, chessPiecePromoted, futurePosition, true, true, true, false, false);
    }
}
