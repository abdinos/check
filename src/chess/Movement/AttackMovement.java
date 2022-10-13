package chess.Movement;

import chess.Movement.Movement;
import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class AttackMovement extends Movement {

    /**
     * A chess Piece move by attacking another chess piece without doing any special action
     */
    public AttackMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPieceAttacked) {
        super(boardGame, chessPieceMoved, chessPieceAttacked, null, futurePosition, true, false, false,false, false);
    }
}
