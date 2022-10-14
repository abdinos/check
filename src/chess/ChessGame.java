package chess;

import chess.board.BoardGame;
import chess.Movement.Movement;
import chess.board.StandardCalculKingCheck;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.Pawn;
import chess.chessPiece.PieceColor;
import chess.gui.BoardGameGUI;
import chess.gui.ChessGameMainWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ChessGame {

    private int indexCurrentPlayer;

    private List<Player> players;

    private BoardGame boardGame;

    private BoardGameGUI boardGameGUI;

    private ChessGameMainWindow chessGameMainWindow;

    private boolean isEndGame;

    private boolean isDrawn;

    public ChessGame() {
        this.boardGame = new BoardGame(this);
        boardGame.setCalculKingCheck(new StandardCalculKingCheck(boardGame));

        //test
        //MainWindow mainWindow = new MainWindow();
        //mainWindow.setVisible(true);
        /**
        try{
            MainWindow mainWindow = new MainWindow();
            this.boardGameGUI = new BoardGameGUI(boardGame.getBoard(),mainWindow.getJFrame());
        }
        catch (IOException e){
            e.printStackTrace();
        }
         **/
        isEndGame = false;
        isDrawn = false;
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

    public boolean isEndGame(){
        return isEndGame;
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
            else  if(movement.isMoveSpecialPawn() && chessPieceToMove instanceof Pawn) {
                if (!movement.isAttacking()) {
                    boardGame.setChessPieceSpecialMove(chessPieceToMove);
                    ((Pawn)chessPieceToMove).setMoveEnPassantPossible();
                }
                else{
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

            boardGame.findAllActiveChessPieces();
            Player enemyPlayer = players.get((indexCurrentPlayer + 1) % players.size());
            Player currentPlayer = players.get(indexCurrentPlayer);
            boardGame.updateChessPiecesLegalMovements(enemyPlayer.getPlayerColor(), true);
            boardGame.updateChessPiecesLegalMovements(currentPlayer.getPlayerColor(),true);

            if(boardGame.isDraw()){
                chessGameMainWindow.draw();
                isDrawn = true;
            }
            else if (boardGame.isGameEnded(currentPlayer.getPlayerColor(), enemyPlayer)) {
                    chessGameMainWindow.endGame();
                    isEndGame = true;
                }
            else{
                indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
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

    /**
     * interface de test sur console
     * TO DELETE
     */
    public void interfaceTest(){
        Scanner scanner = new Scanner(System.in);
        String response = "";
        int position = -1;
        int tour = 1;

        System.out.println("Début de la partie \n");

        while(!isEndGame && !isDrawn){
            do {
                System.out.println(boardGame + "\n");

                System.out.println("tour " + tour);
                System.out.println("Au tour de " + players.get(indexCurrentPlayer).getName() + "\n");

                System.out.println("choisit la pièce à bouger (position) :");
                response = scanner.next();
                try {
                    position = Integer.parseInt(response);
                }
                catch (Exception e){}
            }while(position < 0 || position > 63);

            // Get chess piece at the position enter
            ChessPiece chessPiece = boardGame.getBoard().get(position);
            if(chessPiece != null) {
                System.out.println("Mouvement de " + chessPiece.chessPieceToString() + " : ");
                int index = 1;
                List<Movement> movements = new ArrayList<>();
                if (boardGame.getChessPieceLegalMovements(chessPiece) != null) {
                    movements = boardGame.getChessPieceLegalMovements(chessPiece).stream().toList();

                    for (Movement movement : movements) {
                        System.out.print(index++ + " : " + movement.getChessPieceMoved().getPiecePosition() + " --> " + movement.getFuturePosition());
                        if (movement.isAttacking()) {
                            System.out.print(" (" + movement.getChessPieceAttacked().chessPieceToString() + ")");
                        }
                        System.out.println(" / " + movement.getClass());
                    }
                    System.out.println();

                    do {
                        System.out.println("choisir le mouvement ou changer de piece (new) \n");
                        response = scanner.next();

                        if (!response.equals("new")) {
                            try {
                                position = Integer.parseInt(response) - 1;
                                if (position >= movements.size() || position < 0) {
                                    System.out.println("valeur incorrect ! \n");
                                } else {
                                    response = "move";
                                }
                            } catch (Exception e) {
                            }
                        }
                    } while (!response.equals("new") && !response.equals("move"));
                } else {
                    System.out.println("Aucune mouvement possible \n");
                    response = "";
                }

                if (response.equals("move")) {
                    Movement movement = movements.get(position);
                    executeChessPieceMovement(movement);

                    Player enemyPlayer = players.get(indexCurrentPlayer);
                    if(enemyPlayer.isKingCheck()){
                        System.out.println(enemyPlayer.getName() + " : check state\n");
                    }

                    if(!isEndGame) {
                        System.out.println("changement de joueur \n");
                        tour++;
                    }
                }
            }
        }
        if(isEndGame) {
            System.out.println("\nVictoire de " + players.get((indexCurrentPlayer + 1) % players.size()).getName());
        }
        if(isDrawn){
            System.out.println("Match nul");
        }
    }

    public void setChessGameMainWindow(ChessGameMainWindow chessGameMainWindow){
        if(chessGameMainWindow != null){
            this.chessGameMainWindow = chessGameMainWindow;
        }
    }

    /**
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame();
        chessGame.createPlayers();
        chessGame.initChessGame();

        chessGame.interfaceTest();
    }
     **/
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChessGame chessGame = new ChessGame();
                chessGame.createPlayers();
                chessGame.initChessGame();

                //chessGame.interfaceTest();

                ChessGameMainWindow chessGameMainWindow = new ChessGameMainWindow(chessGame);
                chessGame.setChessGameMainWindow(chessGameMainWindow);
                chessGameMainWindow.createAndShoPlayMenu();
            }
        });
    }
}