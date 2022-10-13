package chess.board;

import chess.ChessGame;
import chess.chessPiece.ChessPiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
    ChessGame  chessGame = new ChessGame();
    BoardGame boardGame = new BoardGame(chessGame);

    @Test
    void isCaseOccupied() {
        boardGame.createBoard();
        boardGame.initChessPieceOnBoard();
        assertTrue(boardGame.isCaseOccupied(48));
        assertFalse(boardGame.isCaseOccupied(16));

    }

    @Test
    void getChessPieceAtPosition() {
        boardGame.createBoard();
        boardGame.initChessPieceOnBoard();
        ChessPiece chessPiece = boardGame.getChessPieceAtPosition(4);
        ChessPiece chessPiece1 = boardGame.getChessPieceAtPosition(17);
        assertTrue(chessPiece!= null);
        assertTrue(chessPiece1==null);
    }

    @Test
     void isValidPosition() {
        assertTrue(BoardGame.isValidPosition(2));
    }
}