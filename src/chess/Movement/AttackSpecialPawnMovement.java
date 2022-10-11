package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class AttackSpecialPawnMovement extends Movement{

    public AttackSpecialPawnMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPiecePromoted){
        super(boardGame,chessPieceMoved, null, chessPiecePromoted, futurePosition, true, true, false, true);
    }
}
