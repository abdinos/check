package chess.chessPiece;

import chess.board.AttackMovement;
import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION_VECTOR = {-8, -1, 1, 8};

    public Rook(final int piecePosition, final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName() {
        return "Rook";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int futurePosition : POSSIBLE_MOVEMENT_POSITION_VECTOR){
            int possiblePosition = this.getPiecePosition();

            while(BoardGame.isValidPosition(possiblePosition)){
                if(isFirstColumnExclusionPosition(this.getPiecePosition(), futurePosition) ||
                        isEightColumnExclusionPosition(this.getPiecePosition(), futurePosition)){
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

    private static boolean isFirstColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.FIRST_COLUMN[currentPosition] && (possiblePosition == -1));
    }

    private static boolean isEightColumnExclusionPosition(final int currentPosition, final int possiblePosition){
        return (BoardGame.EIGHT_COLUMN[currentPosition] && (possiblePosition == 1));
    }
}
