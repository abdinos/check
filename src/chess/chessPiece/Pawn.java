package chess.chessPiece;

import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends ChessPiece{

    private final static int[] POSSIBLE_MOVEMENT_POSITION = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public String getName() {
        return "Pawn";
    }

    @Override
    public Collection<Movement> findLegalMovements(final BoardGame boardGame) {
        final List<Movement> legalMovements = new ArrayList<>();

        for(final int futurePosition: POSSIBLE_MOVEMENT_POSITION){
            final int possiblePosition = this.getPiecePosition() + (futurePosition * this.getPieceColor().getDirection());

            if(!BoardGame.isValidPosition(possiblePosition)){
                continue;
            }
            if(futurePosition == 8 && !boardGame.isCaseOccupied(possiblePosition)){
                // TODO
                legalMovements.add(new NormalMovement(boardGame,this,possiblePosition));
            }else if(futurePosition == 16 && !this.isPieceMove() && ((this.getPiecePosition() >= 48 && this.getPieceColor().isWhite())
                    || (this.getPiecePosition() <= 15 && this.getPieceColor().isBlack()))){
                final int behindFuturePosition = this.getPiecePosition() + (this.getPieceColor().getDirection() * 8);
                if(!boardGame.isCaseOccupied(behindFuturePosition) && !boardGame.isCaseOccupied(possiblePosition)){
                    legalMovements.add(new NormalMovement(boardGame,this,possiblePosition));
                }
            }
            else if(futurePosition == 7 && !((BoardGame.EIGHT_COLUMN[this.getPiecePosition()] && this.getPieceColor().isWhite())
                    || (BoardGame.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceColor().isBlack()))){
                if(!boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(futurePosition);
                    if(this.getPieceColor() != chessPiece.getPieceColor()){
                        // TODO
                        legalMovements.add(new NormalMovement(boardGame, this, possiblePosition));
                    }
                }
            } else if(futurePosition == 9 && !((BoardGame.EIGHT_COLUMN[this.getPiecePosition()] && this.getPieceColor().isBlack())
                    || (BoardGame.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceColor().isWhite()))){
                if(!boardGame.isCaseOccupied(futurePosition)){
                    final ChessPiece chessPiece = boardGame.getChessPieceAtPosition(futurePosition);
                    if(this.getPieceColor() != chessPiece.getPieceColor()){
                        // TODO
                        legalMovements.add(new NormalMovement(boardGame, this, possiblePosition));
                    }
                }
            }
        }
        return legalMovements;
    }
}
