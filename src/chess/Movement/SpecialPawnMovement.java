package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class SpecialPawnMovement extends Movement{

    public SpecialPawnMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, null, chessPiecePromoted, futurePosition, false, true, false, true, false);
    }
}
