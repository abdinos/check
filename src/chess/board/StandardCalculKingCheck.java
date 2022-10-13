package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class StandardCalculKingCheck implements InterfaceCalculKingCheck{

    private BoardGame boardGame;

    public StandardCalculKingCheck(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    /**
     * Verify if an attack on a king is possible.
     */
    public boolean isKingCheckAfterMovement(Movement movement, PieceColor pieceColor){
        // Start of the simulation
        int chessPiecePosition = movement.getChessPieceMoved().getPiecePosition();
        ChessPiece chessPieceSave = boardGame.getBoard().get(movement.getFuturePosition());

        boardGame.moveChessPiece(movement.getChessPieceMoved(), null, movement.getFuturePosition(),true);
        boardGame.findAllActiveChessPieces();
        boardGame.updateChessPiecesLegalMovements(pieceColor, false);
        Map<ChessPiece,Collection<Movement>> enemyChessPiecesMovements;

        if(pieceColor.isBlack()){
            enemyChessPiecesMovements = boardGame.blackChessPieceLegalMovement;
        }
        else{
            enemyChessPiecesMovements = boardGame.whiteChessPieceLegalMovement;
        }
        boardGame.moveChessPiece(movement.getChessPieceMoved(), chessPieceSave, chessPiecePosition,true);
        boardGame.updateChessPiecesLegalMovements(pieceColor, false);
        // End of the simulation

        for (Collection<Movement> movements : enemyChessPiecesMovements.values()) {
            if(searchCheckMovements(movements)){
                return true;
            }
        }
        return false;
    }

    /**
     * Verify if there is an attack check movement in the list of movement.
     */
    private boolean searchCheckMovements(Collection<Movement> movements){
        for(Iterator<Movement> it = movements.iterator(); it.hasNext();){
            Movement movement = it.next();
            if(movement.isCheckKing()){
                return true;
            }
        }
        return false;
    }
}
