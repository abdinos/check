package chess.chessPiece;

import chess.Movement.*;
import chess.Movement.AttackCheckMovement;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMovementPawn implements InterfaceCalculLegalMovementChessPiece{

    /**
     * The vector where a pawn can move
     */
    private final static int[] POSSIBLE_MOVEMENT_POSITION = {7, 8, 9, 16};

    /**
     * Find all legal movement for a pawn
     */
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
            // Line move to 1 case
            if(vectorPosition == 8 && !boardGame.isCaseOccupied(futurePosition)){
                createNormalMovement(boardGame, futurePosition, legalMovements, verifyCheckAttack,enemyPieceColor, chessPiece);
            }
            // Line move to 2 cases
            else if(vectorPosition == 16 && !chessPiece.isPieceMove() && ((chessPiece.getPiecePosition() >= 48 && chessPiece.getPieceColor().isWhite())
                    || (chessPiece.getPiecePosition() <= 15 && chessPiece.getPieceColor().isBlack()))){
                final int behindFuturePosition = chessPiece.getPiecePosition() + (chessPiece.getPieceColor().getDirection() * 8);
                if(!boardGame.isCaseOccupied(behindFuturePosition) && !boardGame.isCaseOccupied(futurePosition)){
                    // Move "en passant"
                    if(isEnPassantMovePossible(boardGame,(futurePosition + 1), chessPiece.getPieceColor(), ((Pawn)chessPiece).isMoveEnPassantPossible()) != null ||
                            isEnPassantMovePossible(boardGame, (futurePosition - 1), chessPiece.getPieceColor(), ((Pawn)chessPiece).isMoveEnPassantPossible()) != null){
                        SpecialPawnMovement specialPawnMovement = new SpecialPawnMovement(boardGame, chessPiece, futurePosition, null);
                        if (verifyCheckAttack) {
                            if (!boardGame.isKingCheckAfterMovement(specialPawnMovement, enemyPieceColor)) { // Verify if the king can be in check state after
                                legalMovements.add(specialPawnMovement);
                            }
                        } else {
                            legalMovements.add(specialPawnMovement);
                        }
                    }
                    else {
                        NormalMovement normalMovement = new NormalMovement(boardGame, chessPiece, futurePosition);
                        if (verifyCheckAttack) {
                            if (!boardGame.isKingCheckAfterMovement(normalMovement, enemyPieceColor)) {
                                legalMovements.add(normalMovement);
                            }
                        } else {
                            legalMovements.add(normalMovement);
                        }
                    }
                }
            }
            // Diagonal move
            else if(vectorPosition == 7 && !((BoardGame.EIGHT_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isWhite())
                    || (BoardGame.FIRST_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isBlack()))){
                ChessPiece chessPieceNextToThisPiece = isEnPassantMovePossible(boardGame, (chessPiece.getPiecePosition() + chessPiece.getPieceColor().getDirection()),
                        chessPiece.getPieceColor(), true);
                if (chessPieceNextToThisPiece != null) {
                    createAttackMovement(boardGame, chessPieceNextToThisPiece, futurePosition,
                            legalMovements, verifyCheckAttack, enemyPieceColor, chessPiece);
                }

                if(boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPieceAttacked = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPieceAttacked.getPieceColor() != chessPieceAttacked.getPieceColor()){
                        createAttackMovement(boardGame, chessPieceAttacked, futurePosition, legalMovements, verifyCheckAttack,enemyPieceColor, chessPiece);
                    }
                }

            }
            // Diagonal move
            else if(vectorPosition == 9 && !((BoardGame.EIGHT_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isBlack())
                    || (BoardGame.FIRST_COLUMN[chessPiece.getPiecePosition()] && chessPiece.getPieceColor().isWhite()))){
                ChessPiece chessPieceNextToThisPiece = isEnPassantMovePossible(boardGame, (chessPiece.getPiecePosition() + chessPiece.getPieceColor().getDirection()),
                        chessPiece.getPieceColor(), true);
                if (chessPieceNextToThisPiece != null) {
                    createAttackMovement(boardGame, chessPieceNextToThisPiece, futurePosition,
                            legalMovements, verifyCheckAttack, enemyPieceColor, chessPiece);
                }


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
            ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMovementQueen());
            PromotionNormalMovement promotionPawnMovement = new PromotionNormalMovement(boardGame, chessPiece,futurePosition,newChessPiece);
            addMovement(boardGame, legalMovements, promotionPawnMovement, enemyPieceColor, verifyCheckAttack);
        }
        else {
            NormalMovement normalMovement = new NormalMovement(boardGame, chessPiece, futurePosition);
            addMovement(boardGame, legalMovements, normalMovement, enemyPieceColor, verifyCheckAttack);
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
            // Pawn promotion
            if((chessPiece.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (chessPiece.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMovementQueen());
                PromotionAttackCheckMovement promotionAttackCheckMovement = new PromotionAttackCheckMovement(boardGame,chessPiece,futurePosition,chessPieceAttacked,newChessPiece);
                addMovement(boardGame, legalMovements, promotionAttackCheckMovement, enemyPieceColor, verifyCheckAttack);
            }
            else {
                AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked);
                addMovement(boardGame, legalMovements, attackCheckMovement, enemyPieceColor, verifyCheckAttack);
            }
        }
        else{
            // Pawn promotion
            if((chessPiece.getPieceColor().isWhite() && (futurePosition >= 0 && futurePosition <= 7)) ||
                    (chessPiece.getPieceColor().isBlack() && (futurePosition >= 56 && futurePosition <= 63))){
                ChessPiece newChessPiece = new Queen(futurePosition, chessPiece.getPieceColor(), new CalculLegalMovementQueen());
                PromotionAttackMovement promotionAttackMovement = new PromotionAttackMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked, newChessPiece);
                addMovement(boardGame, legalMovements, promotionAttackMovement, enemyPieceColor, verifyCheckAttack);
            }
            else {
                if (chessPieceAttacked instanceof Pawn && boardGame.getChessPieceSpecialMove() == chessPieceAttacked) {
                    // Chess piece attack pawn after he used special move "en passant"
                    AttackSpecialPawnMovement attackSpecialPawnMovement = new AttackSpecialPawnMovement(boardGame,
                            chessPiece, (chessPiece.getPiecePosition() + 1), chessPieceAttacked);
                    addMovement(boardGame, legalMovements, attackSpecialPawnMovement, enemyPieceColor, verifyCheckAttack);
                }
                AttackMovement attackMovement = new AttackMovement(boardGame, chessPiece, futurePosition, chessPieceAttacked);
                addMovement(boardGame, legalMovements, attackMovement, enemyPieceColor, verifyCheckAttack);
            }
        }
    }

    /**
     * Verify is a pawn special move "en passant" is possible by returning the pawn to attack or null
     */
    private ChessPiece isEnPassantMovePossible(final BoardGame boardGame, final int nextPositionToFuturePosition,
                                            final PieceColor pieceColor, boolean flagMoveSpecial){
        if(BoardGame.isValidPosition(nextPositionToFuturePosition) && boardGame.isCaseOccupied(nextPositionToFuturePosition)){
            ChessPiece chessPieceNextPosition = boardGame.getChessPieceAtPosition(nextPositionToFuturePosition);
            if(pieceColor != chessPieceNextPosition.getPieceColor() && flagMoveSpecial){
                return chessPieceNextPosition;
            }
        }
        return null;
    }

    /**
     * Add a new movement to the list of legal movements
     */
    private void addMovement(BoardGame boardGame,List<Movement> legalMovements, Movement movement, PieceColor pieceColor, boolean verifyCheckAttack){
        if(verifyCheckAttack){
            if(!boardGame.isKingCheckAfterMovement(movement,pieceColor)){ // Verify if the king can be in check state after
                legalMovements.add(movement);
            }
        }
        else {
            legalMovements.add(movement);
        }
    }
}
