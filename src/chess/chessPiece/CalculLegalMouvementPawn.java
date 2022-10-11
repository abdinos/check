package chess.chessPiece;

import chess.Movement.*;
import chess.board.AttackCheckMovement;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMouvementPawn implements InterfaceCalculLegalMovementChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION = {7, 8, 9, 16};

    public Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack, final ChessPiece chessPiece) {
        final List<Movement> legalMovements = new ArrayList<>();
        PieceColor enemyPieceColor;

        if(chessPiece.getPieceColor().isWhite()){
            enemyPieceColor = PieceColor.BLACK;
        }
        else{
            enemyPieceColor = PieceColor.WHITE;
        }

        for(final int vectorPosition: POSSIBLE_MOVEMENT_POSITION){
            final int futurePosition = chessPiece.getPiecePosition() + (vectorPosition * chessPiece.getPieceColor().getDirection());

            if(!BoardGame.isValidPosition(futurePosition)){
                continue;
            }
            if(vectorPosition == 8 && !boardGame.isCaseOccupied(futurePosition)){
                createNormalMovement(boardGame, futurePosition, legalMovements, verifyCheckAttack,enemyPieceColor, chessPiece);
            }
            else if(vectorPosition == 16 && !chessPiece.isPieceMove() && ((chessPiece.getPiecePosition() >= 48 && chessPiece.getPieceColor().isWhite())
                    || (chessPiece.getPiecePosition() <= 15 && chessPiece.getPieceColor().isBlack()))){
                final int behindFuturePosition = chessPiece.getPiecePosition() + (chessPiece.getPieceColor().getDirection() * 8);
                if(!boardGame.isCaseOccupied(behindFuturePosition) && !boardGame.isCaseOccupied(futurePosition)){
                    // TODO : coup en passant à faire
                    NormalMovement normalMovement = new NormalMovement(boardGame,chessPiece,futurePosition);
                    if (verifyCheckAttack) {
                        if (!boardGame.isKingCheckAfterMovement(normalMovement,enemyPieceColor)) {
                            legalMovements.add(normalMovement);
                        }
                    } else {
                        legalMovements.add(normalMovement);
                    }
                }
            }
            else if(vectorPosition == 7 && !((BoardGame.EIGHT_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isWhite())
                    || (BoardGame.FIRST_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isBlack()))){
                if(boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPieceAttacked = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPieceAttacked.getPieceColor() != chessPieceAttacked.getPieceColor()){
                        createAttackMovement(boardGame, chessPieceAttacked, futurePosition, legalMovements, verifyCheckAttack,enemyPieceColor, chessPiece);
                    }
                }
            } else if(vectorPosition == 9 && !((BoardGame.EIGHT_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isBlack())
                    || (BoardGame.FIRST_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isWhite()))){
                if(boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPieceAttacked = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPieceAttacked != null && chessPieceAttacked.getPieceColor() != chessPieceAttacked.getPieceColor()){
                        createAttackMovement(boardGame, chessPieceAttacked, futurePosition, legalMovements, verifyCheckAttack,enemyPieceColor, chessPiece);
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
                                      final List<Movement> legalMovements, final boolean verifyCheckAttack,
                                      final PieceColor enemyPieceColor, final ChessPiece chessPiece){
        if((chessPiece.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                (chessPiece.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
            ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMouvementQueen());
            PromotionNormalMovement promotionPawnMovement = new PromotionNormalMovement(boardGame, chessPiece,futurePosition,newChessPiece);
            if (verifyCheckAttack) {
                if (!boardGame.isKingCheckAfterMovement(promotionPawnMovement,enemyPieceColor)) {
                    legalMovements.add(promotionPawnMovement);
                }
            } else {
                legalMovements.add(promotionPawnMovement);
            }
        }
        else {
            NormalMovement normalMovement = new NormalMovement(boardGame, chessPiece, futurePosition);
            if (verifyCheckAttack) {
                if (!boardGame.isKingCheckAfterMovement(normalMovement,enemyPieceColor)) {
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
                                      final boolean verifyCheckAttack, final PieceColor enemyPieceColor,
                                      final ChessPiece chessPiece){
        if(chessPieceAttacked instanceof King){
            if((chessPiece.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (chessPiece.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMouvementQueen());
                PromotionAttackCheckMovement promotionAttackCheckMovement = new PromotionAttackCheckMovement(boardGame,chessPiece,futurePosition,chessPieceAttacked,newChessPiece);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(promotionAttackCheckMovement,enemyPieceColor)) {
                        legalMovements.add(promotionAttackCheckMovement);
                    }
                } else {
                    legalMovements.add(promotionAttackCheckMovement);
                }
            }
            else {
                AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(attackCheckMovement,enemyPieceColor)) {
                        legalMovements.add(attackCheckMovement);
                    }
                } else {
                    legalMovements.add(attackCheckMovement);
                }
            }
        }
        else{
            if((chessPiece.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (chessPiece.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMouvementQueen());
                PromotionAttackMovement promotionAttackMovement = new PromotionAttackMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked, newChessPiece);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(promotionAttackMovement,enemyPieceColor)) {
                        legalMovements.add(promotionAttackMovement);
                    }
                } else {
                    legalMovements.add(promotionAttackMovement);
                }
            }
            else {
                AttackMovement attackMovement = new AttackMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked);
                if (verifyCheckAttack) {
                    if (!boardGame.isKingCheckAfterMovement(attackMovement,enemyPieceColor)) {
                        legalMovements.add(attackMovement);
                    }
                } else {
                    legalMovements.add(attackMovement);
                }
            }
        }
    }
}
