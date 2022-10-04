package chess.chessPiece;

import chess.board.BoardGame;
import chess.board.Movement;

import java.util.Collection;

public abstract class ChessPiece{

   private int piecePosition;
   private final PieceColor pieceColor;
   private final boolean isPieceMove;

    public ChessPiece(int piecePosition, final PieceColor pieceColor){
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isPieceMove = false;
    }


    public abstract String getName();

    public PieceColor getPieceColor(){
        return pieceColor;
    }

    public int getPiecePosition(){
        return piecePosition;
    }

    public boolean isPieceMove(){
        return isPieceMove;
    }

    public abstract Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack);

    public void setPiecePosition(int piecePosition){
        this.piecePosition = piecePosition;
    }

    @Override
    public String toString(){
        return getPieceColor().isBlack() ? getName().toLowerCase() : getName().toUpperCase();
    }

}
