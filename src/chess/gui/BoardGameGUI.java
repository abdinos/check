package chess.gui;

import chess.chessPiece.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class BoardGameGUI extends JComponent {

    private Map<Integer, ChessPiece> board;

    //============================= Partie GUI ==========================================
    ImageIcon white_rook, black_rook;
    ImageIcon white_knight, black_knight;
    ImageIcon white_bishop, black_bishop;
    ImageIcon white_queen, black_queen;
    ImageIcon white_king, black_king;
    ImageIcon white_pawn, black_pawn;

    public BoardGameGUI(Map<Integer, ChessPiece> board){
        this.board = board;

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
