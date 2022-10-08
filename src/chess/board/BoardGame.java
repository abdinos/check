package chess.board;

import chess.ChessGame;
import chess.chessPiece.*;

import java.util.*;

public class BoardGame{

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);


    private ChessGame chessGame;
    private Map<Integer, ChessPiece> board;
    private Collection<ChessPiece> blackChessPieces;
    private Collection<ChessPiece> whiteChessPieces;

    private Map<ChessPiece,Collection<Movement>> whiteChessPieceLegalMovement;

    private Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovement;

    private static boolean[] initColumn(int columnNumber){
        final boolean[] column = new boolean[64];
        /**
        for(int i = 0; i < 64; i++){
            column[i] = false;
        }
         **/
        while(columnNumber < 64){
            column[columnNumber] = true;
            columnNumber += 8;
        }
        return column;
    }

    public BoardGame(ChessGame chessGame){
        this.chessGame = chessGame;
    }

    /**
     * Initialize the map.
     */
    public void createBoard(){
        board = new HashMap<>();
        for(int i = 0; i < 64; i++){
            board.put(i, null);
        }
    }

    /**
     * Create all chess pieces and put them on the board.
     */
    public void initChessPieceOnBoard(){
        if(board == null){
            createBoard();
        }
        // Black chess piece
        board.put(0, new Rook(0, PieceColor.BLACK));
        board.put(1, new Knight(1, PieceColor.BLACK));
        board.put(2, new Bishop(2, PieceColor.BLACK));
        board.put(3, new Queen(3, PieceColor.BLACK));
        board.put(4, new King(4, PieceColor.BLACK));
        board.put(5, new Bishop(5, PieceColor.BLACK));
        board.put(6, new Knight(6, PieceColor.BLACK));
        board.put(7, new Rook(7, PieceColor.BLACK));

        board.put(8, new Pawn(8, PieceColor.BLACK));
        board.put(9, new Pawn(9, PieceColor.BLACK));
        board.put(10, new Pawn(10, PieceColor.BLACK));
        board.put(11, new Pawn(11, PieceColor.BLACK));
        board.put(12, new Pawn(12, PieceColor.BLACK));
        board.put(12, new Pawn(12, PieceColor.BLACK));
        board.put(13, new Pawn(13, PieceColor.BLACK));
        board.put(14, new Pawn(14, PieceColor.BLACK));
        board.put(15, new Pawn(15, PieceColor.BLACK));

        // White chess piece
        board.put(48, new Pawn(48, PieceColor.WHITE));
        board.put(49, new Pawn(49, PieceColor.WHITE));
        board.put(50, new Pawn(50, PieceColor.WHITE));
        board.put(51, new Pawn(51, PieceColor.WHITE));
        board.put(52, new Pawn(52, PieceColor.WHITE));
        board.put(53, new Pawn(53, PieceColor.WHITE));
        board.put(54, new Pawn(54, PieceColor.WHITE));
        board.put(55, new Pawn(55, PieceColor.WHITE));
        board.put(56, new Rook(56, PieceColor.WHITE));
        board.put(57, new Knight(57, PieceColor.WHITE));
        board.put(58, new Bishop(58, PieceColor.WHITE));
        board.put(59, new Queen(59, PieceColor.WHITE));
        board.put(60, new King(60, PieceColor.WHITE));
        board.put(61, new Bishop(61, PieceColor.WHITE));
        board.put(62, new Knight(62, PieceColor.WHITE));
        board.put(63, new Rook(63, PieceColor.WHITE));

        blackChessPieces = findActiveChessPieces(PieceColor.BLACK);
        whiteChessPieces = findActiveChessPieces(PieceColor.WHITE);
        whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPieces, true);
        blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPieces, true);
    }

    @Override
    /**
     * Pour afficher
     * TO DELETE
     */
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 64; i++){
            final String text;
            if(board.get(i) == null){
                text = "-     ";
            }
            else {
                text = board.get(i).printChessPiece();
            }
            builder.append(text + "  ");
            if((i + 1) % 8 == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Find all chess pieces alive for a color piece.
     */
    private Collection<ChessPiece> findActiveChessPieces(final PieceColor pieceColor){
        Collection<ChessPiece> chessPieces = new ArrayList<>();
        for (ChessPiece chessPiece : board.values()) {
            if (chessPiece != null) {
                if (chessPiece.getPieceColor() == pieceColor) {
                    chessPieces.add(chessPiece);
                }
            }
        }
        return chessPieces;
    }

    /**
     * Find all legal movements for a color piece
     */
    private Map<ChessPiece,Collection<Movement>> findChessPieceLegalMovements(Collection<ChessPiece> chessPieces, boolean verifyCheckAttack){
        Map<ChessPiece, Collection<Movement>> chessPieceLegalMovements = new HashMap<>();
        for(ChessPiece chessPiece : chessPieces){
            chessPieceLegalMovements.put(chessPiece, chessPiece.findLegalMovements(this, verifyCheckAttack));
        }
        return chessPieceLegalMovements;
    }

    /**
     * Check if a case is occupied by a chess piece.
     */
    public boolean isCaseOccupied(int casePosition){
        return board.get(casePosition) != null;
    }

    public ChessPiece getChessPieceAtPosition(int position){
        if(board.containsKey(position)){
            return board.get(position);
        }
        return null;
    }

    /**
     * Verify if the case position is in the limit of the map (between 0 and 63).
     */
    public static boolean isValidPosition(int casePosition){
        return (casePosition >= 0 && casePosition < 64);
    }

    /**
     * Verify if an attack on a king is possible.
     */
    public boolean isKingCheckAfterMovement(Movement possibleMovement){
        // Start of the simulation
        int chessPiecePosition = possibleMovement.getChessPieceMoved().getPiecePosition();
        ChessPiece chessPieceSave = board.get(possibleMovement.getFuturePosition());

        board.put(chessPiecePosition, null);
        board.put(possibleMovement.getFuturePosition(), possibleMovement.getChessPieceMoved());
        possibleMovement.getChessPieceMoved().setPiecePosition(possibleMovement.getFuturePosition());

        PieceColor enemyPieceColor;
        if(possibleMovement.getChessPieceMoved().getPieceColor().isWhite()) {
            enemyPieceColor = PieceColor.BLACK;
        }
        else {
            enemyPieceColor = PieceColor.WHITE;
        }

        Collection<ChessPiece> enemyChessPieces = findActiveChessPieces(enemyPieceColor);
        Map<ChessPiece,Collection<Movement>> enemyChessPiecesMovements = findChessPieceLegalMovements(enemyChessPieces, false);

        possibleMovement.getChessPieceMoved().setPiecePosition(chessPiecePosition);
        board.put(chessPiecePosition, possibleMovement.getChessPieceMoved());
        board.put(possibleMovement.getFuturePosition(), chessPieceSave);
        // End of the simulation

        for (Collection<Movement> movements : enemyChessPiecesMovements.values()) {
            if(searchCheckMovements(movements)){
                return true;
            }
        }
        return false;
    }

    /**
     * Move a chess piece on the board and calculate active chess pieces of the enemy player
     */
    public void moveChessPiece(Movement movement, Player currentPlayer){
        ChessPiece chessPiece = movement.getChessPieceMoved();
        board.put(chessPiece.getPiecePosition(), null);
        chessPiece.setPiecePosition(movement.getFuturePosition());
        board.put(chessPiece.getPiecePosition(), chessPiece);
        chessPiece.pieceMoved();

        if(movement.isPromoting()){
            board.put(movement.getFuturePosition(),movement.getChessPiecePromoted());
            if(currentPlayer.getPlayerColor().isWhite()){
                whiteChessPieces = findActiveChessPieces(PieceColor.WHITE);
            }
            else{
                blackChessPieces = findActiveChessPieces(PieceColor.BLACK);
            }
        }

        if (movement.isAttacking()) {
            if (currentPlayer.getPlayerColor().isWhite()) {
                blackChessPieces = findActiveChessPieces(PieceColor.BLACK);
            } else {
                whiteChessPieces = findActiveChessPieces(PieceColor.WHITE);
            }
        }

        if (currentPlayer.getPlayerColor().isBlack()) {
            whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPieces, true);
        } else {
            blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPieces, true);
        }
    }

    /**
     * Verify if there is an attack check movement in the list of movement.
     */
    private boolean searchCheckMovements(Collection<Movement> movements){
        for(Iterator<Movement> it = movements.iterator(); it.hasNext();){
            Movement movement = it.next();
            if(movement.isKingCheck()){
                return true;
            }
        }
        return false;
    }

    public Map<Integer, ChessPiece> getBoard(){
        return board;
    }

    /**
     * Get all legal movements for a pice color
     * à changer !!
     */
    public Collection<Movement> getChessPieceLegalMovements(int position, PieceColor chessPieceColor){
        ChessPiece chessPiece = board.get(position);
        if(chessPiece != null) {
            if (chessPieceColor.isBlack()) {
                return blackChessPieceLegalMovement.get(chessPiece);
            }
            return whiteChessPieceLegalMovement.get(chessPiece);
        }
        return null;
    }
}
