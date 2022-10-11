package chess.chessPiece;

import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.Movement.NormalMovement;
import chess.Movement.AttackCheckMovement;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMovementQueen implements InterfaceCalculLegalMovementChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-9, -8, -7, -1, 1, 7, 8, 9};

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
                        if(verifyCheckAttack){
                            if(!boardGame.isKingCheckAfterMovement(normalMovement,enemyPieceColor)){
                                legalMovements.add(normalMovement);
                            }
                        }
                        else {
                            legalMovements.add(normalMovement);
                        }
                    }
                    else{
                        final ChessPiece chessPieceAtFuturePosition = boardGame.getChessPieceAtPosition(futurePosition);
                        if(chessPieceAtFuturePosition != null) {
                            if (chessPiece.getPieceColor() != chessPieceAtFuturePosition.getPieceColor()) {
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
                            break;
                        }
                    }
                }
            }
        }
        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -9 || vectorPosition == 7 || vectorPosition == -1));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (vectorPosition == -7 || vectorPosition == 1 || vectorPosition == 9));
    }
}
