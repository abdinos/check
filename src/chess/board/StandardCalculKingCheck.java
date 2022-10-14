package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.*;

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
        boardGame.findAllActiveChessPieces(true);
        boardGame.updateChessPiecesLegalMovements(pieceColor, false, true);
        Map<ChessPiece,Collection<Movement>> enemyChessPiecesMovements;

        if(pieceColor.isBlack()){
            enemyChessPiecesMovements = boardGame.blackChessPieceLegalMovementSimulation;
        }
        else{
            enemyChessPiecesMovements = boardGame.whiteChessPieceLegalMovementSimulation;
        }

        boolean isGood = false;
        for (Collection<Movement> movements : enemyChessPiecesMovements.values()) {
            if(searchCheckMovements(movements)){
                isGood = true;
                break;
            }
        }
        boardGame.moveChessPiece(movement.getChessPieceMoved(), chessPieceSave, chessPiecePosition,true);
        // End of the simulation

        return isGood;
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
