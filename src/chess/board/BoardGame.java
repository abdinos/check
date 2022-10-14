package chess.board;

import chess.ChessGame;
import chess.Movement.Movement;
import chess.Player;
import chess.chessPiece.*;

import java.util.*;

public class BoardGame{

    /**
     * A table to indicate the case in the first column of the board
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0);

    /**
     * A table to indicate the case in the second column of the board
     */
    public static final boolean[] SECOND_COLUMN = initColumn(1);

    /**
     * A table to indicate the case in the seventh column of the board
     */
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);

    /**
     * A table to indicate the case in the eight column of the board
     */
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    public static final int NUMBER_TOTAL_OF_CASES = 64;

    public static final int NUMBER_OF_CASE_BY_COLUMN = 8;

    /**
     * The main class of the chess game
     */
    private ChessGame chessGame;

    /**
     * The map with all cases representing the board
     */
    private Map<Integer, ChessPiece> board;

    /**
     * The collection of black chess pieces alive
     */
    private Collection<ChessPiece> blackChessPiecesAlive;

    private Collection<ChessPiece> blackChessPiecesAliveSimulation;

    /**
     * The collection of white chess pieces alive
     */
    private Collection<ChessPiece> whiteChessPiecesAlive;

    private Collection<ChessPiece> whiteChessPiecesAliveSimulation;

    /**
     * The Map with all legal movements for all white chess pieces alive
     */
    protected Map<ChessPiece,Collection<Movement>> whiteChessPieceLegalMovement;
    protected Map<ChessPiece,Collection<Movement>> whiteChessPieceLegalMovementSimulation;


    /**
     * The Map with all legal movements for all black chess pieces alive
     */
    protected Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovement;

    protected Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovementSimulation;

    /**
     * The interface to calculate if the king is in check state after a movement by another chess piece
     */
    private InterfaceCalculKingCheck calculKingCheck;

    /**
     * The interface to calculate if it's a draw or not
     */
    private InterfaceDraw interfaceDraw;

    /**
     * The pawn save after the special move "en passant" used
     */
    private ChessPiece chessPieceSpecialMove = null;


    /**
     * Create and return a table to indicate all case in a column given in parameter
     */
    private static boolean[] initColumn(int columnNumber){
        final boolean[] column = new boolean[NUMBER_TOTAL_OF_CASES];
        while(columnNumber < NUMBER_TOTAL_OF_CASES){
            column[columnNumber] = true;
            columnNumber += NUMBER_OF_CASE_BY_COLUMN;
        }
        return column;
    }

    public BoardGame(ChessGame chessGame){
        this.chessGame = chessGame;
        calculKingCheck = new StandardCalculKingCheck(this);
        interfaceDraw = new StandardDraw();
    }

    /**
     * Initialize the map board.
     */
    public void createBoard(){
        board = new HashMap<>();
        for(int i = 0; i < NUMBER_TOTAL_OF_CASES; i++){
            board.put(i, null);
        }
    }

    /**
     * Create all chess pieces and put them on the map board.
     */
    public void initChessPieceOnBoard(){
        if(board == null){
            createBoard();
        }
        // Black chess piece
        board.put(0, new Rook(0, PieceColor.BLACK, new CalculLegalMovementRook()));
        board.put(1, new Knight(1, PieceColor.BLACK, new CalculLegalMovementKnight()));
        board.put(2, new Bishop(2, PieceColor.BLACK, new CalculLegalMovementBishop()));
        board.put(3, new King(3, PieceColor.BLACK, new CalculLegalMovementKing()));
        board.put(4, new Queen(4, PieceColor.BLACK, new CalculLegalMovementQueen()));
        board.put(5, new Bishop(5, PieceColor.BLACK, new CalculLegalMovementBishop()));
        board.put(6, new Knight(6, PieceColor.BLACK, new CalculLegalMovementKnight()));
        board.put(7, new Rook(7, PieceColor.BLACK, new CalculLegalMovementRook()));
        board.put(8, new Pawn(8, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(9, new Pawn(9, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(10, new Pawn(10, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(11, new Pawn(11, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(12, new Pawn(12, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(13, new Pawn(13, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(14, new Pawn(14, PieceColor.BLACK, new CalculLegalMovementPawn()));
        board.put(15, new Pawn(15, PieceColor.BLACK, new CalculLegalMovementPawn()));

        // White chess piece
        board.put(48, new Pawn(48, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(49, new Pawn(49, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(50, new Pawn(50, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(51, new Pawn(51, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(52, new Pawn(52, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(53, new Pawn(53, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(54, new Pawn(54, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(55, new Pawn(55, PieceColor.WHITE, new CalculLegalMovementPawn()));
        board.put(56, new Rook(56, PieceColor.WHITE, new CalculLegalMovementRook()));
        board.put(57, new Knight(57, PieceColor.WHITE, new CalculLegalMovementKnight()));
        board.put(58, new Bishop(58, PieceColor.WHITE, new CalculLegalMovementBishop()));
        board.put(59, new Queen(59, PieceColor.WHITE, new CalculLegalMovementQueen()));
        board.put(60, new King(60, PieceColor.WHITE, new CalculLegalMovementKing()));
        board.put(61, new Bishop(61, PieceColor.WHITE, new CalculLegalMovementBishop()));
        board.put(62, new Knight(62, PieceColor.WHITE, new CalculLegalMovementKnight()));
        board.put(63, new Rook(63, PieceColor.WHITE, new CalculLegalMovementRook()));

        findAllActiveChessPieces(false);
        whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPiecesAlive, true);
        blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPiecesAlive, true);
    }

    @Override
    /**
     * Pour afficher
     * TO DELETE
     */
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < NUMBER_TOTAL_OF_CASES; i++){
            final String text;
            if(board.get(i) == null){
                text = "-     ";
            }
            else {
                text = board.get(i).chessPieceToString();
            }
            builder.append(text + "  ");
            if((i + 1) % NUMBER_OF_CASE_BY_COLUMN == 0){
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

    /**
     * Return the chess piece in a position given, it can be null
     */
    public ChessPiece getChessPieceAtPosition(int position){
        if(board.containsKey(position)){
            return board.get(position);
        }
        return null;
    }

    /**
     * Verify if the position given is in the limit of the map (between 0 and 63).
     */
    public static boolean isValidPosition(int casePosition){
        return (casePosition >= 0 && casePosition < NUMBER_TOTAL_OF_CASES);
    }

    /**
     * Verify if an attack on a king is possible.
     */
    public boolean isKingCheckAfterMovement(Movement movement, PieceColor pieceColor){
        if(calculKingCheck == null){
            System.err.println("interface calcul king check not instanced !");
            System.exit(-1);
        }
        return calculKingCheck.isKingCheckAfterMovement(movement,pieceColor);
    }

    /**
     * Move a chess piece on the board.
     */
    public void moveChessPiece(ChessPiece chessPieceToMove, ChessPiece chessPieceAtFuturePosition, int futurePosition, boolean isSimulate){
        board.put(chessPieceToMove.getPiecePosition(),  chessPieceAtFuturePosition);
        chessPieceToMove.setPiecePosition(futurePosition);
        board.put(futurePosition, chessPieceToMove);

        if(!isSimulate) {
            chessPieceToMove.pieceMoved();
        }
    }

    /**
     * Kill a pawn after the use of the special move "en passant"
     */
    public void killPawnAfterSpecialMove(Movement movement){
        if(movement.isMoveSpecialPawn() && movement.isAttacking() && movement.getChessPieceAttacked() != null){
            board.put(movement.getChessPieceAttacked().getPiecePosition(), null);
        }
    }

    /**
     * Verify if the game is ended
     */
    public boolean isGameEnded(PieceColor currentColor, Player enemyPlayer){
        if(currentColor != null){
            Map<ChessPiece, Collection<Movement>> mapChessPieceLegalMovements;
            if (currentColor.isWhite()) {
                mapChessPieceLegalMovements = blackChessPieceLegalMovement;
            } else {
                mapChessPieceLegalMovements = whiteChessPieceLegalMovement;
            }

            boolean isCheckState = false;
            boolean isGameEnded = true;

            for (Collection<Movement> movements : mapChessPieceLegalMovements.values()) {
                if(!isGameEnded && isCheckState){
                    break;
                }
                for (Movement movement : movements) {
                    if (!isKingCheckAfterMovement(movement,currentColor)) {
                        isGameEnded = false;
                        if(isCheckState){
                            break;
                        }
                    }
                    else{
                        isCheckState = true;
                    }
                }
            }
            if(isCheckState){
                enemyPlayer.setKingCheck(true);
            }
            else {
                enemyPlayer.setKingCheck(false);
            }
            return isGameEnded;
        }

        enemyPlayer.setKingCheck(false);
        return false;
    }

    /**
     * Verify if it's a draw
     */
    public boolean isDraw(){
        return interfaceDraw.isDraw(whiteChessPieceLegalMovement,blackChessPieceLegalMovement);
    }

    /**
     * Promote a pawn to a new chess piece
     */
    public void promotingPawn(ChessPiece pawn,ChessPiece newChessPiece, int futurePosition){
        if(newChessPiece != null && isValidPosition(futurePosition) && pawn instanceof Pawn) {
            board.put(futurePosition, newChessPiece);
        }
    }

    /**
     * Find all active chess pieces
     */
    public void findAllActiveChessPieces(boolean isSimulating){
        if(!isSimulating) {
            blackChessPiecesAlive = findActiveChessPieces(PieceColor.BLACK);
            whiteChessPiecesAlive = findActiveChessPieces(PieceColor.WHITE);
        }
        else{
            blackChessPiecesAliveSimulation = findActiveChessPieces(PieceColor.BLACK);
            whiteChessPiecesAliveSimulation = findActiveChessPieces(PieceColor.WHITE);
        }
    }

    /**
     * Update chess pieces legal movements for a piece color
     */
    public void updateChessPiecesLegalMovements(PieceColor pieceColor, boolean isCheckKing, boolean isSimulating){
        if(pieceColor.isWhite()){
            if(isSimulating){
                whiteChessPieceLegalMovementSimulation = findChessPieceLegalMovements(whiteChessPiecesAlive, isCheckKing);
            }
            else {
                whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPiecesAlive, isCheckKing);
            }
        }
        else{
            if(isSimulating){
                blackChessPieceLegalMovementSimulation = findChessPieceLegalMovements(blackChessPiecesAlive, isCheckKing);
            }
            else {
                blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPiecesAlive, isCheckKing);
            }
        }
    }

    /**
     * Return the map of the board
     */
    public Map<Integer, ChessPiece> getBoard(){
        return board;
    }

    /**
     * Get all legal movements for a piece color
     */
    public Collection<Movement> getChessPieceLegalMovements(ChessPiece chessPiece){
        if(chessPiece != null) {
            if (chessPiece.getPieceColor().isBlack()) {
                return blackChessPieceLegalMovement.get(chessPiece);
            }
            return whiteChessPieceLegalMovement.get(chessPiece);
        }
        return null;
    }

    public void setCalculKingCheck(InterfaceCalculKingCheck interfaceCalculKingCheck){
        calculKingCheck = interfaceCalculKingCheck;
    }

    public ChessPiece getChessPieceSpecialMove(){
        return chessPieceSpecialMove;
    }

    public void setChessPieceSpecialMove(ChessPiece chessPiece){
        this.chessPieceSpecialMove = chessPiece;
    }


}
