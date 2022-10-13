package chess.board;

import chess.ChessGame;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculLegalMovementKnightTest {

    BoardGame boardGame;
    ChessGame chessGame;

    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(1, new Knight(1, PieceColor.BLACK, new CalculLegalMovementKnight()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(1).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(16);
        expectedPositions.add(18);
        expectedPositions.add(11);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }

    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(1, new Knight(1, PieceColor.BLACK, new CalculLegalMovementKnight()));
        boardGame.getBoard().put(16, new Pawn(16, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(1).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(11);
        expectedPositions.add(18);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }

    @Test
    void testFirstColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(40, new Knight(40, PieceColor.WHITE, new CalculLegalMovementKnight()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(40).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(25);
        expectedPositions.add(34);
        expectedPositions.add(50);
        expectedPositions.add(57);
        assertTrue(actualPositions.size() == expectedPositions.size() && actualPositions.containsAll(expectedPositions));

    }
    @Test
    void testSecondColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(41, new Knight(41, PieceColor.WHITE, new CalculLegalMovementKnight()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(41).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(35);
        expectedPositions.add(58);
        expectedPositions.add(51);
        expectedPositions.add(26);
         expectedPositions.add(24);
         expectedPositions.add(56);
        assertTrue(actualPositions.size() == expectedPositions.size() && actualPositions.containsAll(expectedPositions));

    }
    @Test
    void testSeventhColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(46, new Knight(46, PieceColor.WHITE, new CalculLegalMovementKnight()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(46).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(31);
        expectedPositions.add(29);
        expectedPositions.add(61);
        expectedPositions.add(63);
        expectedPositions.add(52);
        expectedPositions.add(36);
        assertTrue(actualPositions.size() == expectedPositions.size() && actualPositions.containsAll(expectedPositions));

    }
    @Test
    void testEighthColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(47, new Knight(47, PieceColor.WHITE, new CalculLegalMovementKnight()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(47).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(30);
        expectedPositions.add(62);
        expectedPositions.add(53);
        expectedPositions.add(37);
        assertTrue(actualPositions.size() == expectedPositions.size() && actualPositions.containsAll(expectedPositions));

    }
    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyBlackPawn = new Pawn(41, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(58, new Knight(58, PieceColor.WHITE, new CalculLegalMovementKnight()));
        boardGame.getBoard().put(41, enemyBlackPawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(58).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            System.out.println(movement.getFuturePosition());
           /* if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
            System.out.println(attackMovement.getFuturePosition());*/
        }
        //assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 41);


    }

}
