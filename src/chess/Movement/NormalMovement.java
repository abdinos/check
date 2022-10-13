package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class NormalMovement extends Movement {

    /**
     * A chess Piece move without attacking another chess piece without doing any special action
     */
    public NormalMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition) {
        super(boardGame, chessPieceMoved, null, null, futurePosition, false, false, false, false, false);
    }
}
