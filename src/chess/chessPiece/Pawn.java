package chess.chessPiece;

import chess.Movement.*;
import chess.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends ChessPiece{

    private boolean isMoveEnPassantPossible = true;

    public Pawn(final int piecePosition, final PieceColor pieceColor, final InterfaceCalculLegalMovementChessPiece interfaceCalculLegalMovementChessPiece) {
        super(piecePosition, pieceColor, interfaceCalculLegalMovementChessPiece);
    }

    @Override
    public String getName() {
        return "Pawn";
    }

    public void setMoveEnPassantPossible(){
        this.isMoveEnPassantPossible = false;
    }

    public boolean isMoveEnPassantPossible(){
        return isMoveEnPassantPossible;
    }
}
