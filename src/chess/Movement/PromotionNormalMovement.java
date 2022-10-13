package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class PromotionNormalMovement extends Movement {

    public PromotionNormalMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, null, chessPiecePromoted, futurePosition, false, true, false, false,false);
    }
}
