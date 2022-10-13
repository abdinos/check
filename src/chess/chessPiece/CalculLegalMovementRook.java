package chess.chessPiece;

import chess.Movement.*;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMovementRook implements InterfaceCalculLegalMovementChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-8, -1, 1, 8};

    public Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack, final ChessPiece chessPiece) {
        final List<Movement> legalMovements = new ArrayList<>();
        PieceColor enemyPieceColor;

        if(chessPiece.getPieceColor().isWhite()){
            enemyPieceColor = PieceColor.BLACK;
        }
        else{
            enemyPieceColor = PieceColor.WHITE;
        }

        for(final int vectorPosition : POSSIBLE_MOVEMENT_POSITION_VECTOR){
            int futurePosition = chessPiece.getPiecePosition();

            while(BoardGame.isValidPosition(futurePosition)){
                if(isFirstColumnExclusionPosition(futurePosition, vectorPosition) ||
                        isEightColumnExclusionPosition(futurePosition, vectorPosition)){
                    break;
                }

                futurePosition += vectorPosition;
                if(BoardGame.isValidPosition(futurePosition)){
                    if (!boardGame.isCaseOccupied(futurePosition)) {
                        NormalMovement normalMovement = new NormalMovement(boardGame, chessPiece, futurePosition);
                        if(verifyCheckAttack) {
                            if (!boardGame.isKingCheckAfterMovement(normalMovement,enemyPieceColor)) {
                                legalMovements.add(normalMovement);
                            }
                        }
                        else{
                            legalMovements.add(normalMovement);
                        }
                    }
                    else{
                        final ChessPiece chessPieceAtFuturePosition = boardGame.getChessPieceAtPosition(futurePosition);
                        if(chessPieceAtFuturePosition != null) {
                            // Find a piece with a different color
                            if (chessPiece.getPieceColor() != chessPieceAtFuturePosition.getPieceColor()) {
                                if(verifyCheckAttack){
                                }
                                if(chessPieceAtFuturePosition instanceof King){
                                    AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame,chessPiece, futurePosition, chessPieceAtFuturePosition);
                                    if(verifyCheckAttack) {
                                        if (!boardGame.isKingCheckAfterMovement(attackCheckMovement,enemyPieceColor)) {
                                            legalMovements.add(attackCheckMovement);
                                        }
                                    }
                                    else{
                                        legalMovements.add(attackCheckMovement);
                                    }
                                }
                                else{
                                    AttackMovement attackMovement = new AttackMovement(boardGame, chessPiece, futurePosition, chessPieceAtFuturePosition);
                                    if(verifyCheckAttack) {
                                        if (!boardGame.isKingCheckAfterMovement(attackMovement,enemyPieceColor)) {
                                            legalMovements.add(attackMovement);
                                        }
                                    }
                                    else{
                                        legalMovements.add(attackMovement);
                                    }
                                }
                            }
                            // Find a piece with the same color
                            else{
                                if(chessPieceAtFuturePosition instanceof King && !chessPieceAtFuturePosition.isPieceMove() && !chessPiece.isPieceMove()){
                                    if(chessPiece.getPiecePosition() > futurePosition){
                                        futurePosition++;
                                    }
                                    else{
                                        futurePosition--;
                                    }
                                    CastleMovement castleMovement = new CastleMovement(boardGame,chessPiece,futurePosition,chessPieceAtFuturePosition);
                                    if(verifyCheckAttack) {
                                        if (!boardGame.isKingCheckAfterMovement(castleMovement,enemyPieceColor)) {
                                            legalMovements.add(castleMovement);
                                        }
                                    }
                                    else{
                                        legalMovements.add(castleMovement);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -1));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (vectorPosition == 1));
    }
}
