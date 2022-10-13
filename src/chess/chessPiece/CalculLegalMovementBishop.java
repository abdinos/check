package chess.chessPiece;

import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.Movement.NormalMovement;
import chess.Movement.AttackCheckMovement;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMovementBishop implements InterfaceCalculLegalMovementChessPiece{

    /**
     * The vector where a bishop can move
     */
    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-9, -7, 7, 9};

    /**
     * Find all legal movement for a bishop
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
                        addMovement(boardGame, legalMovements, normalMovement, enemyPieceColor, verifyCheckAttack);
                    }
                    else{ // Another piece has been found
                        final ChessPiece chessPieceAtFuturePosition = boardGame.getChessPieceAtPosition(futurePosition);
                        if(chessPieceAtFuturePosition != null) {
                            if (chessPiece.getPieceColor() != chessPieceAtFuturePosition.getPieceColor()) {
                                if(chessPieceAtFuturePosition instanceof King){
                                    AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame,chessPiece, futurePosition, chessPieceAtFuturePosition);
                                    addMovement(boardGame, legalMovements, attackCheckMovement, enemyPieceColor, verifyCheckAttack);
                                }
                                else {
                                    AttackMovement attackMovement = new AttackMovement(boardGame, chessPiece, futurePosition, chessPieceAtFuturePosition);
                                    addMovement(boardGame, legalMovements, attackMovement, enemyPieceColor, verifyCheckAttack);
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

    /**
     * Verify if the future position is not an exclusion position in the first column
     */
    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -9 || vectorPosition == 7));
    }

    /**
     * Verify if the future position is not an exclusion position in the eight column
     */
    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int futurePosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (futurePosition == -7 || futurePosition == 9));
    }
}
