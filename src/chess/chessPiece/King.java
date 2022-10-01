package chess.chessPiece;

import chess.board.AttackMovement;
import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

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
    public Collection<Movement> findLegalMovements(final BoardGame boardGame) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int futurePosition : POSSIBLE_MOVEMENT_POSITION){
            final int possiblePosition = this.getPiecePosition() + futurePosition;

            if(BoardGame.isValidPosition(possiblePosition)){
                if(isFirstColumnExclusionPosition(possiblePosition, futurePosition) ||
                        isHeightColumnExclusionPosition(possiblePosition, futurePosition)){
                    continue;
                }
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
                }
            }
        }

        return legalMovements;
    }

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int futurePosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (futurePosition == -9 || futurePosition == -1 ||
                futurePosition == 7));
    }

    private static boolean isHeightColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (possiblePosition == -7 || possiblePosition == 1) ||
                possiblePosition == 9);
    }
}
