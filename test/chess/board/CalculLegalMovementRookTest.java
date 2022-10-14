package chess.board;

import chess.ChessGame;
import chess.Movement.AttackCheckMovement;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.board.BoardGame;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculLegalMovementRookTest {

    BoardGame boardGame;
    ChessGame chessGame;

    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(17, new Rook(17, PieceColor.BLACK, new CalculLegalMovementRook()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(17).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(23);
        expectedPositions.add(16);
        expectedPositions.add(19);
        expectedPositions.add(18);
        expectedPositions.add(20);
        expectedPositions.add(21);
        expectedPositions.add(22);
        expectedPositions.add(1);
        expectedPositions.add(9);
        expectedPositions.add(25);
        expectedPositions.add(33);
        expectedPositions.add(41);
        expectedPositions.add(49);
        expectedPositions.add(57);

        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }
    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(25,new Pawn(25,PieceColor.BLACK,new CalculLegalMovementPawn()));
        boardGame.getBoard().put(17, new Rook(17, PieceColor.BLACK, new CalculLegalMovementRook()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(17).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(23);
        expectedPositions.add(16);
        expectedPositions.add(19);
        expectedPositions.add(18);
        expectedPositions.add(20);
        expectedPositions.add(21);
        expectedPositions.add(22);
        expectedPositions.add(1);
        expectedPositions.add(9);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }

    @Test
    void testFirstColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(0, new Rook(0, PieceColor.BLACK, new CalculLegalMovementRook()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(0).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(1);
        expectedPositions.add(2);
        expectedPositions.add(3);
        expectedPositions.add(4);
        expectedPositions.add(5);
        expectedPositions.add(6);
        expectedPositions.add(7);
        expectedPositions.add(8);
        expectedPositions.add(16);
        expectedPositions.add(24);
        expectedPositions.add(32);
        expectedPositions.add(40);
        expectedPositions.add(48);
        expectedPositions.add(56);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }

    @Test
    void testEightColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(0, new Rook(7, PieceColor.BLACK, new CalculLegalMovementRook()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(0).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(1);
        expectedPositions.add(2);
        expectedPositions.add(3);
        expectedPositions.add(4);
        expectedPositions.add(5);
        expectedPositions.add(6);
        expectedPositions.add(15);
        expectedPositions.add(23);
        expectedPositions.add(31);
        expectedPositions.add(39);
        expectedPositions.add(47);
        expectedPositions.add(55);
        expectedPositions.add(63);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }
    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(8, PieceColor.WHITE, new CalculLegalMovementPawn());
        boardGame.getBoard().put(0, new Bishop(0, PieceColor.BLACK, new CalculLegalMovementRook()));
        boardGame.getBoard().put(8, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(0).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 8);
    }
    @Test
    void testLegalNormalEnemyOccupiedPositionMovementWhite() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(55, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(63, new Bishop(63, PieceColor.WHITE, new CalculLegalMovementRook()));
        boardGame.getBoard().put(55, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(63).findLegalMovements(boardGame, false);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 55);
    }
    @Test
    void testLegalCheckmateFreePositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece rook2 = new Rook(57, PieceColor.BLACK, new CalculLegalMovementRook());
        ChessPiece enemyKing = new King(24, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece rook = new Rook(0, PieceColor.BLACK, new CalculLegalMovementRook());
        boardGame.getBoard().put(57, rook2);
        boardGame.getBoard().put(24, enemyKing);
        boardGame.getBoard().put(0, rook);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(24).findLegalMovements(boardGame, true);AttackCheckMovement attackCheckMovement = null;
        assertTrue(actualMovements.isEmpty());
    }
    @Test
    void testLegalCheckmateOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece rook2 = new Rook(57, PieceColor.BLACK, new CalculLegalMovementRook());
        ChessPiece enemyKing = new King(24, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece rook = new Rook(0, PieceColor.BLACK, new CalculLegalMovementRook());
        ChessPiece pawn = new Pawn(49, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(57, rook2);
        boardGame.getBoard().put(24, enemyKing);
        boardGame.getBoard().put(0, rook);
        boardGame.getBoard().put(49, pawn);
        boardGame.moveChessPiece(rook2,pawn,49,true);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(24).findLegalMovements(boardGame, true);AttackCheckMovement attackCheckMovement = null;
        assertTrue(actualMovements.isEmpty());
    }


















































































}
