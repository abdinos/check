package chess.board;

import chess.chessPiece.*;

import java.util.*;

public class BoardGame {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    private final Map<Integer, ChessPiece> board;
    private PieceColor nextColorPiece;
    private final Collection<ChessPiece> blackChessPiece;
    private final Collection<ChessPiece> whiteChessPiece;

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

    public BoardGame(){
        board = new HashMap<>();
        createBoard();
        initChessPieceOnBoard();
        blackChessPiece = findActiveChessPieces(PieceColor.BLACK);
        whiteChessPiece = findActiveChessPieces(PieceColor.WHITE);

        final Collection<Movement> whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPiece);
        final Collection<Movement> blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPiece);
    }

    public Collection<Movement> getLegalMove(){
        int index = 0;
        for (Iterator<ChessPiece> it = blackChessPiece.iterator(); it.hasNext(); ) {
            ChessPiece chessPiece = it.next();
            if(index == 4){
                System.out.println("\n" + chessPiece.getName() + "/" + chessPiece.getPieceColor() + "/position : " + chessPiece.getPiecePosition());
                return chessPiece.findLegalMovements(this);
            }
            index ++;
            blackChessPiece.iterator().next();
        }
        return null;
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 64; i++){
            final String text;
            if(board.get(i) == null){
                text = "-";
            }
            else {
                text = board.get(i).toString();
            }
            builder.append(text + "  ");
            if((i + 1) % 8 == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Collection<ChessPiece> findActiveChessPieces(final PieceColor pieceColor){
        Collection<ChessPiece> chessPieces = new ArrayList<>();
        for (Iterator<ChessPiece> it = board.values().iterator(); it.hasNext(); ) {
            ChessPiece chessPiece = it.next();
            if(chessPiece != null) {
                if (chessPiece.getPieceColor() == pieceColor) {
                    chessPieces.add(chessPiece);
                }
            }
        }
        return chessPieces;
    }

    private Collection<Movement> findChessPieceLegalMovements(Collection<ChessPiece> chessPieces){
        List<Movement> chessPieceLegalMovements = new ArrayList<>();

        for(ChessPiece chessPiece : chessPieces){
            //chessPieceLegalMovements.addAll(chessPiece.findLegalMovements(this));
        }

        return chessPieceLegalMovements;
    }

    private void createBoard(){
        for(int i = 0; i < 64; i++){
            board.put(i, null);
        }
    }

    private void initChessPieceOnBoard(){
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
        //board.put(12, new Pawn(12, PieceColor.BLACK));
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
    }

    public boolean isCaseOccupied(int casePosition){
        return board.get(casePosition) != null;
    }

    public ChessPiece getChessPieceAtPosition(int position){
        if(board.containsKey(position)){
            return board.get(position);
        }
        return null;
    }

    public static boolean isValidPosition(int position){
        return (position >= 0 && position < 64);
    }

}
