import chess.board.BoardGame;
import chess.board.Movement;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        //https://www.youtube.com/watch?v=MM6hU9Wcmfg&list=PLOJzCFLZdG4zk5d-1_ah2B4kqZSeIlWtt&index=16&ab_channel=SoftwareArchitecture%26Design

        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame);

        for (Iterator<Movement> it = boardGame.getLegalMove().iterator(); it.hasNext(); ) {
            Movement movement = it.next();
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }

    }
}