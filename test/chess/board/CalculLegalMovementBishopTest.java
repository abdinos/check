package chess.board;

import chess.ChessGame;
import chess.Movement.AttackCheckMovement;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
public class CalculLegalMovementBishopTest {
    BoardGame boardGame;
    ChessGame chessGame;

    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(9);
        expectedPositions.add(16);
        expectedPositions.add(11);
        expectedPositions.add(20);
        expectedPositions.add(29);
        expectedPositions.add(38);
        expectedPositions.add(47);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }

    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(9, new Pawn(9, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(11);
        expectedPositions.add(20);
        expectedPositions.add(29);
        expectedPositions.add(38);
        expectedPositions.add(47);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }

    @Test
    void testFirstColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(16, new Bishop(16, PieceColor.BLACK, new CalculLegalMovementBishop()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(7) || actualPositions.contains(23));
    }

    @Test
    void testEighthColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(23, new Bishop(23, PieceColor.BLACK, new CalculLegalMovementBishop()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(23).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(16) || actualPositions.contains(33));
    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(9, PieceColor.WHITE, new CalculLegalMovementPawn());
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(9, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 9);


    }

    @Test
    void testLegalCheckmateFreePositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece pawn = new Pawn(8, PieceColor.BLACK, new CalculLegalMovementPawn());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece knight = new Knight(25, PieceColor.BLACK, new CalculLegalMovementKnight());
        ChessPiece rook = new Rook(17, PieceColor.BLACK, new CalculLegalMovementRook());
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(8, pawn);
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(25, knight);
        boardGame.getBoard().put(17, rook);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, true);
        AttackCheckMovement attackCheckMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackCheckMovement.class)
                attackCheckMovement = (AttackCheckMovement) movement;
        }
        assertTrue(attackCheckMovement != null && attackCheckMovement.getFuturePosition() == 16);
    }

    @Test
    void testLegalCheckmateOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece pawn = new Pawn(8, PieceColor.BLACK, new CalculLegalMovementPawn());
        ChessPiece whitePawn = new Pawn(9, PieceColor.WHITE, new CalculLegalMovementPawn());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece knight = new Knight(25, PieceColor.BLACK, new CalculLegalMovementKnight());
        ChessPiece rook = new Rook(17, PieceColor.BLACK, new CalculLegalMovementRook());
        ChessPiece rook2 = new Rook(1, PieceColor.BLACK, new CalculLegalMovementRook());
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(8, pawn);
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(25, knight);
        boardGame.getBoard().put(17, rook);
        boardGame.getBoard().put(1, rook2);
        boardGame.getBoard().put(9, whitePawn);

        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }

        assertTrue(actualPositions.contains(9));


    }

    @Test
    void testLegalKingOccupatedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();


        ChessPiece enemyKing = new King(9, PieceColor.WHITE, new CalculLegalMovementKing());
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(9, enemyKing);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }

        assertTrue(actualPositions.contains(9));
    }

    @Test
    void testLegalNullPieceOccupatedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();

        ChessPiece enemyKing = null;
        boardGame.getBoard().put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(9, enemyKing);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(2).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }

        assertTrue(actualPositions.contains(9));


    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovementWhite() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(49, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(58, new Bishop(58, PieceColor.WHITE, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(49, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(58).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 49);


    }
}
