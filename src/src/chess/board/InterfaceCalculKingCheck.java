package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;

import java.util.Collection;
import java.util.Map;

public interface InterfaceCalculKingCheck {

    boolean isKingCheckAfterMovement(Movement movement, PieceColor pieceColor);
}
