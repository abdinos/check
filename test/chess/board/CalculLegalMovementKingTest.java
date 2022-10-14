package chess.board;

import chess.ChessGame;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculLegalMovementKingTest {

    ChessGame chessGame;
    BoardGame boardGame;

    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(3, new King(3, PieceColor.BLACK, new CalculLegalMovementKing()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(3).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(2);
        expectedPositions.add(4);
        expectedPositions.add(10);
        expectedPositions.add(11);
        expectedPositions.add(12);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }

    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(3, new King(3, PieceColor.BLACK, new CalculLegalMovementKing()));
        boardGame.getBoard().put(11, new Pawn(11, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(3).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(2);
        expectedPositions.add(4);
        expectedPositions.add(10);
        expectedPositions.add(12);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }

    @Test
    void testFirstColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(16, new King(16, PieceColor.BLACK, new CalculLegalMovementKing()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(15) || actualPositions.contains(7)|| actualPositions.contains(23));
    }

    @Test
    void testEighthColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(23, new King(23, PieceColor.BLACK, new CalculLegalMovementKing()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(23).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(32) || actualPositions.contains(24) || actualPositions.contains(16));
    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(11, PieceColor.WHITE, new CalculLegalMovementPawn());
        boardGame.getBoard().put(3, new King(3, PieceColor.BLACK, new CalculLegalMovementKing()));
        boardGame.getBoard().put(11, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(3).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 11);


    }

    @Test
    void testLegalCheckmateFreePositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece queen = new Queen(26, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece king = new King(8, PieceColor.BLACK, new CalculLegalMovementKing());
        ChessPiece bishop = new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop());
        boardGame.getBoard().put(2,bishop );
        boardGame.getBoard().put(26, queen);
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(8, king);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        assertTrue(actualMovements.isEmpty());
    }

    @Test
    void testLegalCheckmateOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece king = new King(0, PieceColor.BLACK, new CalculLegalMovementKing());
        ChessPiece queen = new Queen(26, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece pawn = new Pawn(8,PieceColor.WHITE,new CalculLegalMovementPawn());
        ChessPiece bishop = new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop());
        boardGame.getBoard().put(2,bishop );
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(0, king);
        boardGame.getBoard().put(26, queen);
        boardGame.getBoard().put(8,pawn);
        boardGame.moveChessPiece(king,pawn,8,true);

        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        assertTrue(actualMovements.isEmpty());


    }

    @Test
    void testLegalNullPieceOccupatedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();

        ChessPiece enemyKing = null;
        boardGame.getBoard().put(2, new King(2, PieceColor.BLACK, new CalculLegalMovementKing()));
        boardGame.getBoard().put(3, enemyKing);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }

        assertTrue(actualPositions.contains(3));


    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovementWhite() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(50, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(58, new King(58, PieceColor.WHITE, new CalculLegalMovementKing()));
        boardGame.getBoard().put(50, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(58).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 50);


    }

}
