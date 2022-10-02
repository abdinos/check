import chess.board.BoardGame;
import chess.board.Movement;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        boardGame.createBoard();
        boardGame.initChessPieceOnBoard();
        boardGame.createPlayers();

        System.out.println(boardGame);

        for (Iterator<Movement> it = boardGame.getLegalMove().iterator(); it.hasNext(); ) {
            Movement movement = it.next();
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }

    }
}