package chess.board;

import chess.chessPiece.ChessPiece;

public class AttackCheckMovement extends AttackMovement{

    public AttackCheckMovement(final BoardGame boardGame, final ChessPiece chessPiece, final int futurePosition, final ChessPiece attackedPiece){
        super(boardGame, chessPiece, futurePosition, attackedPiece);
    }
}
