package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;

import java.util.Collection;
import java.util.Map;

public interface InterfaceDraw {

    boolean isDraw(Map<ChessPiece, Collection<Movement>> whiteChessPieceLegalMovement,
                   Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovement);
}
