package chess.chessPiece;

import chess.Movement.*;
import chess.board.BoardGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculLegalMovementKing implements InterfaceCalculLegalMovementChessPiece {

    /**
     * The vector where a king can move
     */
    private final static int[] POSSIBLE_MOVEMENT_POSITION = {-9, -8, -7, -1, 1, 7, 8 ,9};

    /**
     * Find all legal movement for a king
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

        for(final int vectorPosition : POSSIBLE_MOVEMENT_POSITION){
            int futurePosition = chessPiece.getPiecePosition() + vectorPosition;

            if(BoardGame.isValidPosition(futurePosition)){
                if(isFirstColumnExclusionPosition(chessPiece.getPiecePosition(), vectorPosition) ||
                        isHeightColumnExclusionPosition(chessPiece.getPiecePosition(), vectorPosition)){
                    continue;
                }
                if (!boardGame.isCaseOccupied(futurePosition)) {
                    NormalMovement normalMovement = new NormalMovement(boardGame, chessPiece, futurePosition);
                    addMovement(boardGame, legalMovements, normalMovement, enemyPieceColor, verifyCheckAttack);

                    if(!chessPiece.isPieceMove() && (vectorPosition == 1 || vectorPosition == -1)) { // Search if castling with a rook is possible
                        futurePosition = chessPiece.getPiecePosition();
                        while(BoardGame.isValidPosition(futurePosition)){
                            futurePosition += vectorPosition;

                            if(boardGame.isCaseOccupied(futurePosition)){
                                ChessPiece chessPieceAtFuturePosition = boardGame.getChessPieceAtPosition(futurePosition);
                                if(chessPieceAtFuturePosition instanceof Rook && !chessPieceAtFuturePosition.isPieceMove()){
                                    if(chessPiece.getPiecePosition() < chessPieceAtFuturePosition.getPiecePosition()){
                                        futurePosition = chessPiece.getPiecePosition() + 2;
                                    }
                                    else{
                                        futurePosition = chessPiece.getPiecePosition() - 2;
                                    }
                                    CastleMovement castleMovement = new CastleMovement(boardGame, chessPiece, futurePosition, chessPieceAtFuturePosition);
                                    addMovement(boardGame, legalMovements, castleMovement, enemyPieceColor, verifyCheckAttack);
                                    break;
                                }
                                else{
                                    break;
                                }
                            }
                            if(isFirstColumnExclusionPosition(futurePosition, vectorPosition) ||
                                    isHeightColumnExclusionPosition(futurePosition, vectorPosition)){
                                break;
                            }
                        }
                    }

                }
                else{ // Another piece has been found
                    final ChessPiece chessPieceAtFuturePosition = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPieceAtFuturePosition != null) {
                        if (chessPiece.getPieceColor() != chessPieceAtFuturePosition.getPieceColor()) {
                            if(chessPieceAtFuturePosition instanceof King){
                                AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame, chessPiece, futurePosition, chessPieceAtFuturePosition);
                                addMovement(boardGame, legalMovements, attackCheckMovement, enemyPieceColor, verifyCheckAttack);
                            }
                            else{
                                AttackMovement attackMovement = new AttackMovement(boardGame, chessPiece, futurePosition, chessPieceAtFuturePosition);
                                addMovement(boardGame, legalMovements, attackMovement, enemyPieceColor, verifyCheckAttack);
                            }
                        }
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
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -9 || vectorPosition == -1 ||
                vectorPosition == 7));
    }

    /**
     * Verify if the future position is not an exclusion position in the height column
     */
    private static boolean isHeightColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && ((vectorPosition == -7 || vectorPosition == 1) ||
                vectorPosition == 9));
    }
}
