package chess.chessPiece;

import chess.Movement.Movement;
import chess.board.BoardGame;

import java.util.Collection;

public interface InterfaceCalculLegalMovementChessPiece {

    Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack, final ChessPiece chessPiece);
}
