package chess.chessPiece;

import chess.board.AttackMovement;
import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final int piecePosition, final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName() {
        return "Queen";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int vectorPosition : POSSIBLE_MOVEMENT_POSITION_VECTOR){
            int futurePosition = this.getPiecePosition();

            while(BoardGame.isValidPosition(futurePosition)){
                if(isFirstColumnExclusionPosition(futurePosition, vectorPosition) ||
                        isEightColumnExclusionPosition(futurePosition, vectorPosition)){
                    break;
                }

                futurePosition += vectorPosition;
                if(BoardGame.isValidPosition(futurePosition)){
                    if (!boardGame.isCaseOccupied(futurePosition)) {
                        legalMovements.add(new NormalMovement(boardGame, this, futurePosition));
                    }
                    else{
                        final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(futurePosition);
                        if(chessPiece != null) {
                            if (this.getPieceColor() != chessPiece.getPieceColor()) {
                                legalMovements.add(new AttackMovement(boardGame, this, futurePosition, chessPiece));
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
        return (BoardGame.FIRST_COLUMN[currentPosition] && (vectorPosition == -9 || vectorPosition == -7 || vectorPosition == -1));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int vectorPosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (vectorPosition == -7 || vectorPosition == 1 || vectorPosition == 9));
    }
}
