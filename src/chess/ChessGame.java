package chess;

import chess.board.BoardGame;
import chess.Movement.Movement;
import chess.board.StandardCalculKingCheck;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;
import chess.gui.BoardGameGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessGame {

    private int indexCurrentPlayer;

    private List<Player> players;

    private BoardGame boardGame;

    private BoardGameGUI boardGameGUI;

    private boolean isEndGame;

    public ChessGame() throws IOException {
        this.boardGame = new BoardGame(this);
        boardGame.setCalculKingCheck(new StandardCalculKingCheck(boardGame));
        this.boardGameGUI = new BoardGameGUI(boardGame.getBoard());
        isEndGame = false;
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
            boardGame.moveChessPiece(movement);

            if (movement.isPromoting()) {
                boardGame.promotingPawn(movement.getChessPieceMoved(), movement.getChessPiecePromoted(), movement.getFuturePosition());
            }

            boardGame.findAllActiveChessPieces();
            Player enemyPlayer = players.get((indexCurrentPlayer + 1) % players.size());
            Player currentPlayer = players.get(indexCurrentPlayer);
            boardGame.updateChessPiecesLegalMovements(enemyPlayer.getPlayerColor(), true);
            boardGame.updateChessPiecesLegalMovements(currentPlayer.getPlayerColor(),true);

            if(boardGame.isGameEnded(currentPlayer.getPlayerColor(),enemyPlayer)){
                isEndGame = true;
            }
        }

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

        while(!isEndGame){
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
                System.out.println("Mouvement de " + chessPiece.printChessPiece() + " : ");
                int index = 1;
                List<Movement> movements = new ArrayList<>();
                if (boardGame.getChessPieceLegalMovements(position, players.get(indexCurrentPlayer).getPlayerColor()) != null) {
                    movements = boardGame.getChessPieceLegalMovements(position, players.get(indexCurrentPlayer).getPlayerColor()).stream().toList();

                    for (Movement movement : movements) {
                        System.out.print(index++ + " : " + movement.getChessPieceMoved().getPiecePosition() + " --> " + movement.getFuturePosition());
                        if (movement.isAttacking()) {
                            System.out.print(" (" + movement.getChessPieceAttacked().printChessPiece() + ")");
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

                    Player enemyPlayer = players.get((indexCurrentPlayer + 1) % players.size());
                    if(enemyPlayer.isKingCheck()){
                        System.out.println(enemyPlayer.getName() + " : check state\n");
                    }

                    if(!isEndGame) {
                        System.out.println("changement de joueur \n");
                        indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
                        tour++;
                    }
                }
            }
        }
        System.out.println("\nVictoire de " + players.get(indexCurrentPlayer).getName());
    }

    public static void main(String[] args) throws IOException {
        ChessGame chessGame = new ChessGame();
        chessGame.createPlayers();
        chessGame.initChessGame();

        chessGame.interfaceTest();
    }
}