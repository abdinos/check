package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class SpecialPawnMovement extends Movement{

    /**
     * A pawn move without attacking another chess piece and
     * can be attacked by another pawn with the "en passant" special move by the enemy player
     */
    public SpecialPawnMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, null, chessPiecePromoted, futurePosition, false, true, false, true, false);
    }
}
