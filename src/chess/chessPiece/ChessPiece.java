package chess.chessPiece;

import chess.board.BoardGame;
import chess.Movement.Movement;

import java.util.Collection;

public abstract class ChessPiece{

    /**
     * The chess piece position on the board
     */
   private int piecePosition;

    /**
     * The chess piece color
     */
   private final PieceColor pieceColor;

    /**
     * Indicate if the chess piece has already moved or not
     */
   private boolean isPieceMove;

   private InterfaceCalculLegalMovementChessPiece interfaceCalculLegalMovementChessPiece;

    public ChessPiece(int piecePosition, final PieceColor pieceColor, final InterfaceCalculLegalMovementChessPiece interfaceCalculLegalMovementChessPiece){
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isPieceMove = false;
        this.interfaceCalculLegalMovementChessPiece = interfaceCalculLegalMovementChessPiece;
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

    /**
     * Find all legal movements for a chess piece
     */
    public Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack) {
        if(getCalculLegalMovementChessPiece() == null){
            System.err.println("Error : InterfaceCalculLegalMovementChessPiece not instanced !");
            System.exit(-1);
        }
        return getCalculLegalMovementChessPiece().findLegalMovements(boardGame, verifyCheckAttack, this);
    }

    public void setPiecePosition(int piecePosition){
        this.piecePosition = piecePosition;
    }

    public void pieceMoved(){
        isPieceMove = true;
    }

    public String chessPieceToString(){
        return getPieceColor().isBlack() ? ("B " + getName()) : ("W " + getName());
    }

    public InterfaceCalculLegalMovementChessPiece getCalculLegalMovementChessPiece(){
        return interfaceCalculLegalMovementChessPiece;
    }

}
