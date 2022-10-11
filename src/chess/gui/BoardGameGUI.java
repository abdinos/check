package chess.gui;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BoardGameGUI extends JComponent {

    private Map<Integer, ChessPiece> board;

    //============================= Partie GUI ==========================================
    Image white_rook, black_rook;
    Image white_knight, black_knight;
    Image white_bishop, black_bishop;
    Image white_queen, black_queen;
    Image white_king, black_king;
    Image white_pawn, black_pawn;

    public BoardGameGUI(Map<Integer, ChessPiece> board) throws IOException {
        this.board = board;

        //============================= Partie GUI ==========================================
        white_rook = ImageIO.read(new File("images/white_rook.png"));
        black_rook = ImageIO.read(new File("images/black_rook.png"));

        white_knight = ImageIO.read(new File("images/white_knight.png"));
        black_knight = ImageIO.read(new File("images/black_knight.png"));

        white_bishop = ImageIO.read(new File("images/white_bishop.png"));
        black_bishop  = ImageIO.read(new File("images/black_bishop.png"));

        white_queen = ImageIO.read(new File("images/white_queen.png"));
        black_queen = ImageIO.read(new File("images/black_queen.png"));

        white_king = ImageIO.read(new File("images/white_king.png"));
        black_king =ImageIO.read(new File("images/black_king.png"));

        white_pawn =ImageIO.read(new File("images/white_pawn.png"));
        black_pawn = ImageIO.read(new File("images/black_pawn.png"));
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
        Image imageIcon = null;
        int i=0;
        int j=0;
        System.out.println(board.entrySet().size());
        for (Map.Entry<Integer, ChessPiece> entry : board.entrySet()){
            ChessPiece chessPiece = entry.getValue();
            //String pieceName = String.valueOf(entry.getValue());
            if(entry.getKey() <=15 || entry.getKey() >=48){
                System.out.println(chessPiece.printChessPiece()+"-"+ entry.getKey());
                String color = String.valueOf(chessPiece.printChessPiece().charAt(0));
                String pieceName = chessPiece.printChessPiece().substring(2);
                if(color.equals("B")){//noir
                    if (pieceName.equals("Rook")){
                        imageIcon = black_rook;
                    }
                    else if (pieceName.equals("Knight")){
                        imageIcon = black_knight;
                    }
                    else if (pieceName.equals("Bishop")){
                        imageIcon = black_bishop;
                    }
                    else if (pieceName.equals("Queen")){
                        imageIcon = black_queen;
                    }
                    else if (pieceName.equals("King")){
                        imageIcon = black_king;
                    }else if (pieceName.equals("Pawn")) {
                        imageIcon = black_pawn;
                    }
                }else{//blanche
                    //pieceName = pieceName.toUpperCase();
                    if (pieceName.equals("Rook")){
                        imageIcon = white_rook;
                    }
                    else if (pieceName.equals("Knight")){
                        imageIcon = white_knight;
                    }
                    else if (pieceName.equals("Bishop")){
                        imageIcon = white_bishop;
                    }
                    else if (pieceName.equals("Queen")){
                        imageIcon = white_queen;
                    }
                    else if (pieceName.equals("King")){
                        imageIcon = white_king;
                    }else if (pieceName.equals("Pawn")){
                        imageIcon = white_pawn;
                    }
                }
                //imageIcon.paintIcon(null, graphics2D, (j + 1) * CASE_DIMENSION, (i + 1) * CASE_DIMENSION);
                super.paintComponent(graphics);
                graphics.drawImage(imageIcon,(j + 1) * CASE_DIMENSION, (i + 1) * CASE_DIMENSION,null);
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

        int xEnHaut = 60;
        int yEnhaut = 60;
        int xEnBas = 540;
        int yEnBas = 540;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pos_x = e.getX();
                int pos_y = e.getY();
                int xcase = (pos_x/60)-1;
                int ycase = (pos_y/60)-1;
                System.out.println(e.getX()+"-"+e.getY());
                if(pos_x<xEnHaut || pos_x>xEnBas || pos_y<yEnhaut || pos_y>yEnBas){
                    System.out.println("vous n etes pas dans chessBoard ");
                  //System.out.println("vous etes dans la case"+pos_x/60)+1);
                }else{
                    System.out.println("vous etes dans la case " + ((pos_x/60)));
                    System.out.println("vous etes dans la case " + ((pos_y/60)));
                    ChessPiece chessPiece;
                    if((pos_y/60)>1) {
                        chessPiece = board.get((xcase+(8*ycase)));
                    }else{
                        chessPiece = board.get(((pos_x / 60) - 1));
                    }
                    if(chessPiece !=null){
                        System.out.println(chessPiece.getName());
                        if(chessPiece.getPieceColor().isWhite()){
                            //TOdo
                            //BoardGame boardGame = new BoardGame()
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        graphics2D.dispose();
    }
}
