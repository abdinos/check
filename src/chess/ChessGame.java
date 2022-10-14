package chess;

import chess.board.BoardGame;
import chess.Movement.Movement;
import chess.board.StandardCalculKingCheck;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.Pawn;
import chess.chessPiece.PieceColor;
import chess.gui.BoardGameGUI;
import chess.gui.ChessGameMainWindow;
import chess.gui.MainWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ChessGame {

    private int indexCurrentPlayer;

    private List<Player> players;

    private BoardGame boardGame;

    private ChessGameMainWindow chessGameMainWindow;

    public ChessGame() {
        this.boardGame = new BoardGame(this);
        boardGame.setCalculKingCheck(new StandardCalculKingCheck(boardGame));
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

    public int getIndexCurrentPlayer(){
        return indexCurrentPlayer;
    }

    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Move a chess piece on the board and calculate active chess pieces of the enemy player
     */
    public void executeChessPieceMovement(Movement movement){
        if(movement != null) {
            ChessPiece chessPieceToMove = movement.getChessPieceMoved();
            int futurePosition = movement.getFuturePosition();
            boardGame.moveChessPiece(chessPieceToMove,null, futurePosition,false);

            if (movement.isPromoting()) {
                boardGame.promotingPawn(chessPieceToMove, movement.getChessPiecePromoted(), futurePosition);
            }
            else if(movement.isMoveSpecialPawn() && chessPieceToMove instanceof Pawn) {
                if (!movement.isAttacking()) {
                    boardGame.setChessPieceSpecialMove(chessPieceToMove);
                    ((Pawn)chessPieceToMove).setMoveEnPassantPossible();
                }
                else{
                    boardGame.killPawnAfterSpecialMove(movement);
                    boardGame.setChessPieceSpecialMove(null);
                }
            }
            else if(movement.isCastling()){
                ChessPiece chessPieceToExchange = movement.getChessPieceAttacked();
                int futurePositionToPieceExchanged = chessPieceToMove.getPiecePosition();
                if(chessPieceToExchange.getPiecePosition() < futurePositionToPieceExchanged){
                    futurePositionToPieceExchanged++ ;
                }
                else{
                    futurePositionToPieceExchanged--;
                }
                boardGame.moveChessPiece(chessPieceToExchange,null, futurePositionToPieceExchanged,false);
            }

            boardGame.findAllActiveChessPieces(false);
            Player enemyPlayer = players.get((indexCurrentPlayer + 1) % players.size());
            Player currentPlayer = players.get(indexCurrentPlayer);
            boardGame.updateChessPiecesLegalMovements(enemyPlayer.getPlayerColor(), true, false);
            boardGame.updateChessPiecesLegalMovements(currentPlayer.getPlayerColor(),true,false);

            if(boardGame.isDraw()){
                chessGameMainWindow.draw();
            }
            else if (boardGame.isGameEnded(currentPlayer.getPlayerColor(), enemyPlayer)) {
                    chessGameMainWindow.endGame();
                }
            else{
                indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
                chessGameMainWindow.kingCheckState(players.get(indexCurrentPlayer).isKingCheck());
                chessGameMainWindow.setCurrentPieceColor(players.get(indexCurrentPlayer).getPlayerColor());
            }
        }
    }

    public ChessPiece getChessPieceAtPosition(int position){
        return boardGame.getChessPieceAtPosition(position);
    }

    public Collection<Movement> getMovementsForAPiece(ChessPiece chessPiece){
        return boardGame.getChessPieceLegalMovements(chessPiece);
    }

    public void setChessGameMainWindow(ChessGameMainWindow chessGameMainWindow){
        if(chessGameMainWindow != null){
            this.chessGameMainWindow = chessGameMainWindow;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}