package chess;

import chess.board.BoardGame;
import chess.board.Movement;
import chess.board.NormalMovement;
import chess.board.Player;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;
import chess.gui.BoardGameGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChessGame {

    private int indexCurrentPlayer;

    private List<Player> players;

    private BoardGame boardGame;

    private BoardGameGUI boardGameGUI;

    public ChessGame(){
        this.boardGame = new BoardGame(this);
        this.boardGameGUI = new BoardGameGUI(boardGame.getBoard());
    }

    /**
     * Verify if board game is initialized
     */
    public void checkBoardGameInitialized(){
        if(boardGame == null){
            System.err.println("Bord game not initialized !");
            System.exit(-1);
        }
    }

    /**
     * Initialization of the chess game
     */
    public void initChessGame(){
        checkBoardGameInitialized();

        boardGame.createBoard();
        boardGame.initChessPieceOnBoard();
    }

    /**
     * Create all player.
     */
    public void createPlayers(){
        players = new ArrayList<>();
        players.add(new Player("White",PieceColor.WHITE));
        players.add(new Player("Black", PieceColor.BLACK));
        indexCurrentPlayer = 0;
    }

    /**
     * Move a chess piece
     */
    public void executeChessPieceMovement(Movement movement){
        checkBoardGameInitialized();

        if(movement != null) {
            boardGame.moveChessPiece(movement, players.get(indexCurrentPlayer));
            // TODO déplacer image de la pièce
        }
    }

    /**
     * methode de test
     * TO DELETE AFTER
     */
    public void test(){
        System.out.println(boardGame);

        Collection<Movement> movements = boardGame.getLegalMove();
        Movement movementExecute = null;
        boolean i = false;
        for (Movement movement : movements) {
            if(!i){
                movementExecute = movement;
                i = true;
            }
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }

        executeChessPieceMovement(movementExecute);
        System.out.println("\n" + boardGame);

        movements = boardGame.getLegalMove();
        for (Movement movement : movements) {
            System.out.println(movement.getFuturePosition() + " : " + movement.getClass());
        }
    }

    public PieceColor getEnemyColor(){
        if(players.get(indexCurrentPlayer).getPlayerColor().isWhite()){
             return PieceColor.BLACK;
        }
       return PieceColor.WHITE;
    }

    public int getIndexCurrentPlayer(){
        return indexCurrentPlayer;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame();
        chessGame.createPlayers();
        chessGame.initChessGame();

        chessGame.test();
    }
}