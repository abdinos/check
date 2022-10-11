package chess.chessPiece;

import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.Movement.NormalMovement;
import chess.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends ChessPiece{

    public Rook(final int piecePosition, final PieceColor pieceColor, final InterfaceCalculLegalMovementChessPiece interfaceCalculLegalMovementChessPiece) {
        super(piecePosition, pieceColor, interfaceCalculLegalMovementChessPiece);
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
