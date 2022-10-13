package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class AttackSpecialPawnMovement extends Movement{

    /**
     * A pawn move by attacking another pawn with the special move "en passant"
     */
    public AttackSpecialPawnMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPieceAttacked){
        super(boardGame,chessPieceMoved, chessPieceAttacked, null, futurePosition, true, true, false, true, false);
    }
}
