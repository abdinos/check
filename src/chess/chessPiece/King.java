package chess.chessPiece;

import chess.Movement.Movement;
import chess.board.*;

import java.util.Collection;

public class King extends ChessPiece{


    public King(final int piecePosition, final PieceColor pieceColor, final InterfaceCalculLegalMovementChessPiece interfaceCalculLegalMovementChessPiece) {
        super(piecePosition, pieceColor, interfaceCalculLegalMovementChessPiece);
    }

    @Override
    public String getName() {
        return "King";
    }
}
