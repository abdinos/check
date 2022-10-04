import chess.board.BoardGame;
import chess.board.Movement;

import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        boardGame.createBoard();
        boardGame.createPlayers();
        boardGame.initChessPieceOnBoard();


        System.out.println(boardGame);

        Collection<Movement> movements = boardGame.getLegalMove();
        Movement movementExecute = null;
        boolean i = false;
        for (Movement movement : movements) {
            if(!i){
                movementExecute = movement;
                i = true;
            }
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }

        boardGame.executeChessPieceMovement(movementExecute);
        System.out.println("\n" + boardGame);

        movements = boardGame.getLegalMove();
        for (Movement movement : movements) {
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }
    }
}