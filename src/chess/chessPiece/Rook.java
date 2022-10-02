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

        for(final int vectorPosition : POSSIBLE_MOVEMENT_POSITION_VECTOR){
            int futurePosition = this.getPiecePosition();

            while(BoardGame.isValidPosition(futurePosition)){
                if(isFirstColumnExclusionPosition(this.getPiecePosition(), vectorPosition) ||
                        isEightColumnExclusionPosition(this.getPiecePosition(), vectorPosition)){
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
