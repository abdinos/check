package chess.chessPiece;

import chess.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION = {-9, -8, -7, -1, 1, 7, 8 ,9};

    public King(final int piecePosition, final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName() {
        return "King";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame, final boolean verifyCheckAttack) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int vectorPosition : POSSIBLE_MOVEMENT_POSITION){
            final int futurePosition = this.getPiecePosition() + vectorPosition;

            if(BoardGame.isValidPosition(futurePosition)){
                if(isFirstColumnExclusionPosition(futurePosition, vectorPosition) ||
                        isHeightColumnExclusionPosition(futurePosition, vectorPosition)){
                    continue;
                }
                if (!boardGame.isCaseOccupied(futurePosition)) {
                    NormalMovement normalMovement = new NormalMovement(boardGame, this, futurePosition);
                    if(verifyCheckAttack) {
                        if (!boardGame.isKingCheckAfterMovement(normalMovement)) {
                            legalMovements.add(normalMovement);
                        }
                    }
                    else{
                        legalMovements.add(normalMovement);
                    }
                }
                else{
                    final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPiece != null) {
                        if (this.getPieceColor() != chessPiece.getPieceColor()) {
                            if(chessPiece instanceof King){
                                AttackCheckMovement attackCheckMovement = new AttackCheckMovement(boardGame, this, futurePosition, chessPiece);
                                if(verifyCheckAttack) {
                                    if (!boardGame.isKingCheckAfterMovement(attackCheckMovement)) {
                                        legalMovements.add(attackCheckMovement);
                                    }
                                }
                                else{
                                    legalMovements.add(attackCheckMovement);
                                }
                            }
                            else{
                                AttackMovement attackMovement = new AttackMovement(boardGame, this, futurePosition, chessPiece);
                                if(verifyCheckAttack) {
                                    if (!boardGame.isKingCheckAfterMovement(attackMovement)) {
                                        legalMovements.add(attackMovement);
                                    }
                                }
                                else{
                                    legalMovements.add(attackMovement);
                                }
                            }
                        }
                    }
                }
            }
        }
        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -9 || vectorPosition == -1 ||
                vectorPosition == 7));
    }

    private static boolean isHeightColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (vectorPosition == -7 || vectorPosition == 1) ||
                vectorPosition == 9);
    }
}
