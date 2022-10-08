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
import java.util.Scanner;

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

    public int getIndexCurrentPlayer(){
        return indexCurrentPlayer;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void interfaceTest(){
        System.out.println("Début de la partie \n");
        System.out.println(boardGame + "\n");

        System.out.println("Au tour de " + players.get(indexCurrentPlayer).getName() + "\n");

        boolean end = false;
        Scanner scanner = new Scanner(System.in);
        String response = "";
        int position = -1;

        while(!end){
            do {
                System.out.println("choisit la pièce à bouger (position) :");
                response = scanner.next();
                try {
                    position = Integer.parseInt(response);
                }
                catch (Exception e){}
            }while(position < 0 || position > 63);

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
                    boardGame.moveChessPiece(movement, players.get(indexCurrentPlayer));

                    System.out.println(boardGame + "\n");

                    indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
                    System.out.println("Au tour de " + players.get(indexCurrentPlayer).getName() + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame();
        chessGame.createPlayers();
        chessGame.initChessGame();

        chessGame.interfaceTest();
    }
}