package chess.board;

import chess.chessPiece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class BoardGame extends JComponent {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    private Map<Integer, ChessPiece> board;
    private PieceColor nextColorPiece;
    private Collection<ChessPiece> blackChessPiece;
    private Collection<ChessPiece> whiteChessPiece;

    //============================= Partie GUI ==========================================
    ImageIcon white_rook, black_rook;
    ImageIcon white_knight, black_knight;
    ImageIcon white_bishop, black_bishop;
    ImageIcon white_queen, black_queen;
    ImageIcon white_king, black_king;
    ImageIcon white_pawn, black_pawn;


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
        //============================= Partie GUI ==========================================
        white_rook = new ImageIcon("images/white_rook.png");
        black_rook = new ImageIcon("images/black_rook.png");

        white_knight = new ImageIcon("images/white_knight.png");
        black_knight = new ImageIcon("images/black_knight.png");

        white_bishop = new ImageIcon("images/white_bishop.png");
        black_bishop  = new ImageIcon("images/black_bishop.png");

        white_queen = new ImageIcon("images/white_queen.png");
        black_queen = new ImageIcon("images/black_queen.png");

        white_king = new ImageIcon("images/white_king.png");
        black_king = new ImageIcon("images/black_king.png");

        white_pawn = new ImageIcon("images/white_pawn.png");
        black_pawn = new ImageIcon("images/black_pawn.png");





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

    public void createBoard(){
        board = new HashMap<>();
        for(int i = 0; i < 64; i++){
            board.put(i, null);
        }
    }

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
        //board.put(12, new Pawn(12, PieceColor.BLACK));
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

        blackChessPiece = findActiveChessPieces(PieceColor.BLACK);
        whiteChessPiece = findActiveChessPieces(PieceColor.WHITE);

        final Collection<Movement> whiteChessPieceLegalMovement = findChessPieceLegalMovements(whiteChessPiece);
        final Collection<Movement> blackChessPieceLegalMovement = findChessPieceLegalMovements(blackChessPiece);

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

    //============================= Partie GUI ==========================================

    @Override
    protected void paintComponent(Graphics graphics){

        Graphics2D graphics2D = (Graphics2D) graphics;

        // les cases
        int CASE_DIMENSION = 60;
        boolean isWhite = true;
        //parcourir les lignes et les colonnes de l'échiquier
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if(isWhite){
                    graphics2D.setPaint(Color.WHITE);
                }else {
                    graphics2D.setPaint(Color.GRAY);
                }
                graphics2D.fill(new Rectangle2D.Double((j+1)*CASE_DIMENSION, (i+1)*CASE_DIMENSION,CASE_DIMENSION,CASE_DIMENSION)); // pour dessiner la case
                isWhite = !isWhite; // pour que la prochaine case soit de la couleur opposée en passant d'une colonne a l'autre
            }
            isWhite = !isWhite;  // pour que la prochaine case soit de la couleur opposée en passant d'une ligne a l'autre
        }
        // le cadre
        graphics2D.setPaint(Color.black);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.draw(new Rectangle2D.Double(CASE_DIMENSION, CASE_DIMENSION, CASE_DIMENSION*8, CASE_DIMENSION*8));

        // les légendes
        int c;
        for (int i=0; i<8; i++){
            c=i+1+'0';
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(""+(char) c, 2.0f/3*CASE_DIMENSION, (i+1.5f)*CASE_DIMENSION+6);

        }
        for (int i=0; i<8; i++){
            c='A'+i;
            graphics2D.drawString(""+(char) c,(i+1.5f)*CASE_DIMENSION-5, 2.0f/3*CASE_DIMENSION+6);
        }
        //placer les pieces
        ImageIcon imageIcon =null;
        int i=0;
        int j=0;
        System.out.println(board.entrySet().size());
        for (Map.Entry<Integer, ChessPiece> entry : board.entrySet()){
            String pieceName = String.valueOf(entry.getValue());
            if(entry.getKey() <=15 || entry.getKey() >=48){
                System.out.println(pieceName+"-"+ entry.getKey());
                if(Character.isLowerCase(pieceName.charAt(0))){//noir
                    if (pieceName.equals("rook")){
                        imageIcon = black_rook;
                    }
                    else if (pieceName.equals("knight")){
                        imageIcon = black_knight;
                    }
                    else if (pieceName.equals("bishop")){
                        imageIcon = black_bishop;
                    }
                    else if (pieceName.equals("queen")){
                        imageIcon = black_queen;
                    }
                    else if (pieceName.equals("king")){
                        imageIcon = black_king;
                    }else if (pieceName.equals("pawn")) {
                        imageIcon = black_pawn;
                    }
                }else{//blanche
                    if (pieceName.equals("ROOK")){
                        imageIcon = white_rook;
                    }
                    else if (pieceName.equals("KNIGHT")){
                        imageIcon = white_knight;
                    }
                    else if (pieceName.equals("BISHOP")){
                        imageIcon = white_bishop;
                    }
                    else if (pieceName.equals("QUEEN")){
                        imageIcon = white_queen;
                    }
                    else if (pieceName.equals("KING")){
                        imageIcon = white_king;
                    }else if (pieceName.equals("PAWN")){
                        imageIcon = white_pawn;
                    }
                }
                imageIcon.paintIcon(null,graphics2D, (j+1)*CASE_DIMENSION, (i+1)*CASE_DIMENSION);
            }
            j++;
            if(j>=8){
                j = 0;
                i++;
            }
            if(i>=8){
                break;
            }
        }
        graphics2D.dispose();
    }
}
