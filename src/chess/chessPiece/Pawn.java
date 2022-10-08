package chess.chessPiece;

import chess.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName() {
        return "Pawn";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int vectorPosition: POSSIBLE_MOVEMENT_POSITION){
            final int futurePosition = this.getPiecePosition() + (vectorPosition * this.getPieceColor().getDirection());

            if(!BoardGame.isValidPosition(futurePosition)){
                continue;
            }
            if(vectorPosition == 8 && !boardGame.isCaseOccupied(futurePosition)){
                createNormalMovement(boardGame, futurePosition, legalMovements, verifyCheckAttack);
            }
            else if(vectorPosition == 16 && !this.isPieceMove() && ((this.getPiecePosition() >= 48 && this.getPieceColor().isWhite())
                    || (this.getPiecePosition() <= 15 && this.getPieceColor().isBlack()))){
                final int behindFuturePosition = this.getPiecePosition() + (this.getPieceColor().getDirection() * 8);
                if(!boardGame.isCaseOccupied(behindFuturePosition) && !boardGame.isCaseOccupied(futurePosition)){
                    // TODO : coup en passant à faire
                    NormalMovement normalMovement = new NormalMovement(boardGame,this,futurePosition);
                    if (verifyCheckAttack) {
                        if (!boardGame.isKingCheckAfterMovement(normalMovement)) {
                            legalMovements.add(normalMovement);
                        }
                    } else {
                        legalMovements.add(normalMovement);
                    }
                }
            }
            else if(vectorPosition == 7 && !((BoardGame.EIGHT_COLUMN[this.getPiecePosition()] && this.getPieceColor().isWhite())
                    || (BoardGame.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceColor().isBlack()))){
                if(boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPieceAttacked = boardGame.getChessPieceAtPosition(futurePosition);
                    if(this.getPieceColor() != chessPieceAttacked.getPieceColor()){
                        createAttackMovement(boardGame, chessPieceAttacked, futurePosition, legalMovements, verifyCheckAttack);
                    }
                }
            } else if(vectorPosition == 9 && !((BoardGame.EIGHT_COLUMN[this.getPiecePosition()] && this.getPieceColor().isBlack())
                    || (BoardGame.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceColor().isWhite()))){
                if(boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPieceAttacked = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPieceAttacked != null && this.getPieceColor() != chessPieceAttacked.getPieceColor()){
                        createAttackMovement(boardGame, chessPieceAttacked, futurePosition, legalMovements, verifyCheckAttack);
                    }
                }
            }
        }
        return legalMovements;
    }

    /**
     * Create a normal movement for a pawn
     */
    private void createNormalMovement(final BoardGame boardGame, final int futurePosition,
                                     final List<Movement> legalMovements, final boolean verifyCheckAttack){
        if((this.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                (this.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
            ChessPiece newChessPiece = new Queen(futurePosition, this.getPieceColor());
            PromotionNormalMovement promotionPawnMovement = new PromotionNormalMovement(boardGame, this,futurePosition,newChessPiece);
            if (verifyCheckAttack) {
                if (!boardGame.isKingCheckAfterMovement(promotionPawnMovement)) {
                    legalMovements.add(promotionPawnMovement);
                }
            } else {
                legalMovements.add(promotionPawnMovement);
            }
        }
        else {
            NormalMovement normalMovement = new NormalMovement(boardGame, this, futurePosition);
            if (verifyCheckAttack) {
                if (!boardGame.isKingCheckAfterMovement(normalMovement)) {
                    legalMovements.add(normalMovement);
                }
            } else {
                legalMovements.add(normalMovement);
            }
        }
    }

    /**
     * Create an attack movement for a pawn
     */
    private void createAttackMovement(final BoardGame boardGame, final ChessPiece chessPieceAttacked,
                                     final int futurePosition, final List<Movement> legalMovements,
                                     final boolean verifyCheckAttack){
        if(chessPieceAttacked instanceof King){
            if((this.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (this.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, this.getPieceColor());
                PromotionAttackCheckMovement promotionAttackCheckMovement = new PromotionAttackCheckMovement(boardGame,this,futurePosition,chessPieceAttacked,newChessPiece);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(promotionAttackCheckMovement)) {
                        legalMovements.add(promotionAttackCheckMovement);
                    }
                } else {
                    legalMovements.add(promotionAttackCheckMovement);
                }
            }
            else {
                AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame, this, futurePosition, chessPieceAttacked);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(attackCheckMovement)) {
                        legalMovements.add(attackCheckMovement);
                    }
                } else {
                    legalMovements.add(attackCheckMovement);
                }
            }
        }
        else{
            if((this.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (this.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, this.getPieceColor());
                PromotionAttackMovement promotionAttackMovement = new PromotionAttackMovement(boardGame, this, futurePosition, chessPieceAttacked, newChessPiece);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(promotionAttackMovement)) {
                        legalMovements.add(promotionAttackMovement);
                    }
                } else {
                    legalMovements.add(promotionAttackMovement);
                }
            }
            else {
                AttackMovement attackMovement = new AttackMovement(boardGame, this, futurePosition, chessPieceAttacked);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(attackMovement)) {
                        legalMovements.add(attackMovement);
                    }
                } else {
                    legalMovements.add(attackMovement);
                }
            }
        }
    }
}
