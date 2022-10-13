package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class PromotionNormalMovement extends Movement {

    /**
     * A pawn move without attacking another chess piece and promoting
     */
    public PromotionNormalMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, null, chessPiecePromoted, futurePosition, false, true, false, false,false);
    }
}
