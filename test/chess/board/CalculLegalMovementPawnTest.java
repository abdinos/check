package chess.board;

import chess.ChessGame;
import chess.Movement.AttackMovement;
import chess.Movement.Movement;
import chess.Movement.SpecialPawnMovement;
import chess.board.BoardGame;
import chess.chessPiece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculLegalMovementPawnTest {

    ChessGame chessGame;
    BoardGame boardGame;

    @Test
    void testLegalNormalFreeMovement1Case() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(16, new Pawn(16, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(16).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(24);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }
    @Test
    void testLegalNormalFreeMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(3, new Pawn(3, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(3).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(11);
        expectedPositions.add(19);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));


    }
    @Test
    void testLegalMovementEnPassant() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(35, new Pawn(35, PieceColor.BLACK, new CalculLegalMovementPawn()));
        boardGame.getBoard().put(52, new Pawn(52, PieceColor.WHITE, new CalculLegalMovementPawn()));
        boardGame.moveChessPiece(boardGame.getChessPieceAtPosition(52),null,36,false);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(35).findLegalMovements(boardGame, false);
        AttackMovement specialPawnMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() ==  AttackMovement.class)
                specialPawnMovement = (AttackMovement) movement;
        }
        assertTrue(specialPawnMovement!= null && specialPawnMovement.getFuturePosition()==44);


    }

    @Test
    void testLegalNormalFriendlyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        boardGame.getBoard().put(3, new Pawn(3, PieceColor.BLACK, new CalculLegalMovementPawn()));
        boardGame.getBoard().put(19, new Pawn(19, PieceColor.BLACK, new CalculLegalMovementPawn()));
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(3).findLegalMovements(boardGame, true);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }
        ArrayList<Integer> expectedPositions = new ArrayList<>();
        expectedPositions.add(11);
        assertTrue(expectedPositions.size() == actualPositions.size() && actualPositions.containsAll(expectedPositions));
    }



    @Test
    void testLegalNormalEnemyOccupiedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyWhitePawn = new Pawn(20, PieceColor.WHITE, new CalculLegalMovementPawn());
        boardGame.getBoard().put(11, new Pawn(11, PieceColor.BLACK, new CalculLegalMovementPawn()));
        boardGame.getBoard().put(20, enemyWhitePawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(11).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 20);


    }

    @Test
    void testLegalCheckmateFreePositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece queen = new Queen(26, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece enemyKing = new King(16, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece pawn = new Pawn(9, PieceColor.BLACK, new CalculLegalMovementPawn());
        ChessPiece bishop = new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop());
        boardGame.getBoard().put(9,pawn);
        boardGame.getBoard().put(2,bishop);
        boardGame.getBoard().put(26, queen);
        boardGame.getBoard().put(16, enemyKing);
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
        ChessPiece enemyKing = new King(24, PieceColor.WHITE, new CalculLegalMovementKing());
        ChessPiece pawnenemy = new King(17, PieceColor.BLACK, new CalculLegalMovementPawn());
        ChessPiece queen = new Queen(34, PieceColor.BLACK, new CalculLegalMovementQueen());
        ChessPiece pawn = new Pawn(10,PieceColor.WHITE,new CalculLegalMovementPawn());
        ChessPiece bishop = new Bishop(3, PieceColor.BLACK, new CalculLegalMovementBishop());
        boardGame.getBoard().put(3,bishop );
        boardGame.getBoard().put(24, enemyKing);
        boardGame.getBoard().put(17,pawnenemy);
        boardGame.getBoard().put(34, queen);
        boardGame.getBoard().put(10,pawn);
        boardGame.moveChessPiece(pawn,pawnenemy,17,false);
        boardGame.findAllActiveChessPieces(false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.BLACK,true,false);
        boardGame.updateChessPiecesLegalMovements(PieceColor.WHITE,true,false);

        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(24).findLegalMovements(boardGame, true);
        assertTrue(actualMovements.isEmpty());


    }

    @Test
    void testLegalNullPieceOccupatedPositionMovement() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();

        ChessPiece enemyKing = null;
        boardGame.getBoard().put(11, new Pawn(11, PieceColor.BLACK, new CalculLegalMovementPawn()));
        boardGame.getBoard().put(19, enemyKing);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(11).findLegalMovements(boardGame, false);
        ArrayList<Integer> actualPositions = new ArrayList<>();
        for (Movement movement : actualMovements) {
            actualPositions.add(movement.getFuturePosition());
        }

        assertTrue(actualPositions.contains(19));


    }

    @Test
    void testLegalNormalEnemyOccupiedPositionMovementWhite() {
        boardGame = new BoardGame(chessGame);
        boardGame.createBoard();
        ChessPiece enemyPawn = new Pawn(33, PieceColor.BLACK, new CalculLegalMovementPawn());
        boardGame.getBoard().put(42, new Pawn(42, PieceColor.WHITE, new CalculLegalMovementPawn()));
        boardGame.getBoard().put(33, enemyPawn);
        ArrayList<Movement> actualMovements = (ArrayList<Movement>) boardGame.getChessPieceAtPosition(42).findLegalMovements(boardGame, true);
        AttackMovement attackMovement = null;
        for (Movement movement : actualMovements) {
            if (movement.getClass() == AttackMovement.class)
                attackMovement = (AttackMovement) movement;
        }
        assertTrue(attackMovement != null && attackMovement.getFuturePosition() == 33);


    }
}
