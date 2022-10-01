package chess.chessPiece;

import chess.board.AttackMovement;
import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-9, -7, 7, 9};

    public Bishop(int piecePosition, final PieceColor pieceColor){
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName(){
        return "Bishop";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int futurePosition : POSSIBLE_MOVEMENT_POSITION_VECTOR){
            int possiblePosition = this.getPiecePosition();

            while(BoardGame.isValidPosition(possiblePosition)){
                if(isFirstColumnExclusionPosition(possiblePosition, futurePosition) ||
                    isEightColumnExclusionPosition(possiblePosition, futurePosition)){
                    break;
                }

                possiblePosition += futurePosition;
                if(BoardGame.isValidPosition(possiblePosition)){
                    if (!boardGame.isCaseOccupied(possiblePosition)) {
                        legalMovements.add(new NormalMovement(boardGame, this, possiblePosition));
                    }
                    else{
                        final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(possiblePosition);
                        if(chessPiece != null) {
                            if (this.getPieceColor() != chessPiece.getPieceColor()) {
                                legalMovements.add(new AttackMovement(boardGame, this, possiblePosition, chessPiece));
                            }
                        }
                        break;
                    }
                }
            }
        }
        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int futurePosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (futurePosition == -9 || futurePosition == 7));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int futurePosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (futurePosition == -7 || futurePosition == 9));
    }
}
