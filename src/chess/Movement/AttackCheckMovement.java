package chess.Movement;

import chess.Movement.Movement;
import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class AttackCheckMovement extends Movement {

    public AttackCheckMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPieceAttacked){
        super(boardGame, chessPieceMoved, chessPieceAttacked, null, futurePosition, true, false, true, false);
    }
}
