package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public class CastleMovement extends Movement{

    public CastleMovement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final int futurePosition, final ChessPiece chessPieceExchanged) {
        super(boardGame, chessPieceMoved, chessPieceExchanged, null, futurePosition, false, false, false,false, true);
    }
}
