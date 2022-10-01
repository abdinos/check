package chess.chessPiece;

import chess.board.AttackMovement;
import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends ChessPiece{


    private final static int[] POSSIBLE_MOVEMENT_POSITION = {-17, -15, -10, -6, 10, 15, 17};

    public Knight(int piecePosition, final PieceColor pieceColor){
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName(){
        return "Knight";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame){
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int possiblePosition : POSSIBLE_MOVEMENT_POSITION){
            final int futurePosition = this.getPiecePosition() + possiblePosition;
            if(BoardGame.isValidPosition(futurePosition)) {
                if(isFirstColumnExclusionPosition(this.getPiecePosition(), possiblePosition) ||
                        isSecondColumnExclusionPosition(this.getPiecePosition(), possiblePosition) ||
                        isSeventhColumnExclusionPosition(this.getPiecePosition(), possiblePosition) ||
                        isEightColumnExclusionPosition(this.getPiecePosition(), possiblePosition)){
                    continue;
                }
                if (!boardGame.isCaseOccupied(futurePosition)) {
                    legalMovements.add(new NormalMovement(boardGame, this, futurePosition));
                }
                else{
                    final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(futurePosition);
                    if(chessPiece != null) {
                        if (this.getPieceColor() != chessPiece.getPieceColor()) {
                            legalMovements.add(new AttackMovement(boardGame, this, futurePosition, chessPiece));
                        }
                    }
                }
            }
        }
        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int possiblePosition){
         return (BoardGame.FIRST_COLUMN[currentPosition] && (possiblePosition == -17 || possiblePosition == -10 ||
                 possiblePosition == 6 || possiblePosition == 15));
    }

    private static boolean isSecondColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.SECOND_COLUMN[currentPosition] && (possiblePosition == -10 || possiblePosition == 6));
    }

    private static boolean isSeventhColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.SEVENTH_COLUMN[currentPosition] && (possiblePosition == -6 || possiblePosition == 10));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (possiblePosition == -15 || possiblePosition == -6 ||
                possiblePosition == 10 || possiblePosition == 17));
    }
}
