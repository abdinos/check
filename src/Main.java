import chess.board.BoardGame;
import chess.board.Movement;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        boardGame.createBoard();
        boardGame.createPlayers();
        boardGame.initChessPieceOnBoard();


        System.out.println(boardGame);

        for (Movement movement : boardGame.getLegalMove()) {
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }

    }
}