package chess.board;

import chess.ChessGame;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.board.BoardGame;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculLegalMovementQueenTest {
    ChessGame chessGame;
    BoardGame boardGame;

    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(2, new Queen(2, PieceColor.BLACK, new CalculLegalMovementQueen()));
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
        expectedPositions.add(0);
        expectedPositions.add(1);
        expectedPositions.add(7);
        expectedPositions.add(3);
        expectedPositions.add(4);
        expectedPositions.add(5);
        expectedPositions.add(6);
        expectedPositions.add(18);
        expectedPositions.add(10);
        expectedPositions.add(26);
        expectedPositions.add(34);
        expectedPositions.add(42);
        expectedPositions.add(58);
        expectedPositions.add(50);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }

    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(2, new Queen(2, PieceColor.BLACK, new CalculLegalMovementQueen()));
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
        expectedPositions.add(0);
        expectedPositions.add(1);
        expectedPositions.add(7);
        expectedPositions.add(3);
        expectedPositions.add(4);
        expectedPositions.add(5);
        expectedPositions.add(6);
        expectedPositions.add(18);
        expectedPositions.add(10);
        expectedPositions.add(26);
        expectedPositions.add(34);
        expectedPositions.add(42);
        expectedPositions.add(58);
        expectedPositions.add(50);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }

    @Test
    void testFirstColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(16, new Queen(16, PieceColor.BLACK, new CalculLegalMovementQueen()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(15) || actualPositions.contains(7)|| actualPositions.contains(68));
    }

    @Test
    void testEighthColumnExclusionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(23, new Queen(23, PieceColor.BLACK, new CalculLegalMovementQueen()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(23).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        assertFalse(actualPositions.contains(32) || actualPositions.contains(24));
    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(9, PieceColor.WHITE, new CalculLegalMovementPawn());
        boardGame.getBoard().put(2, new Queen(2, PieceColor.BLACK, new CalculLegalMovementQueen()));
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
        ChessPiece queen = new Queen(26, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece knight = new Knight(18, PieceColor.BLACK, new CalculLegalMovementKnight());
        ChessPiece rook = new Rook(57, PieceColor.BLACK, new CalculLegalMovementRook());
        boardGame.getBoard().put(11, new Bishop(11, PieceColor.BLACK, new CalculLegalMovementBishop()));
        boardGame.getBoard().put(26, queen);
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(18, knight);
        boardGame.getBoard().put(57, rook);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        assertTrue(actualMovements.isEmpty());
    }

    @Test
    void testLegalCheckmateOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece bishop2 = new Bishop(1, PieceColor.BLACK, new CalculLegalMovementBishop());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece rook = new Rook(33, PieceColor.BLACK, new CalculLegalMovementRook());
        ChessPiece queen = new Queen(40, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece pawn = new Pawn(32,PieceColor.WHITE,new CalculLegalMovementPawn());
        ChessPiece bishop = new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop());
        boardGame.getBoard().put(2,bishop );
        boardGame.getBoard().put(1, bishop2);
        boardGame.getBoard().put(16, enemyKing);
        boardGame.getBoard().put(33, rook);
        boardGame.getBoard().put(40, queen);
        boardGame.getBoard().put(32,pawn);
        boardGame.moveChessPiece(queen,pawn,32,true);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        assertTrue(actualMovements.isEmpty());


    }

    @Test
    void testLegalNullPieceOccupatedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();

        ChessPiece enemyKing = null;
        boardGame.getBoard().put(2, new Queen(2, PieceColor.BLACK, new CalculLegalMovementQueen()));
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
        boardGame.getBoard().put(58, new Queen(58, PieceColor.WHITE, new CalculLegalMovementQueen()));
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
