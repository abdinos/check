package chess.gui;

import chess.ChessGame;
import chess.Movement.Movement;
import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class BoardGameGUI{

    BoardGame boardGame;
    private Map<Integer, ChessPiece> board;


    //============================= Partie GUI ==========================================
    Image white_rook, black_rook;
    Image white_knight, black_knight;
    Image white_bishop, black_bishop;
    Image white_queen, black_queen;
    Image white_king, black_king;
    Image white_pawn, black_pawn;

    private JFrame jFrame;

    public BoardGameGUI(Map<Integer, ChessPiece> board) throws IOException {
        this.board = board;

        //============================= Partie GUI ==========================================
        jFrame = new JFrame();
        jFrame.setTitle("Jeu d'échecs"); // titre de l'interface graphique
        jFrame.setSize(800,1200); // dimension de l'interface 1200x800
        jFrame.setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        //jFrame.setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        jFrame.setLocationRelativeTo(null); // on centre notre fenetre

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


    protected void paintComponent(){
        //Graphics2D graphics2D = (Graphics2D) graphics;

        // les cases
        int CASE_DIMENSION = 60;
        boolean isWhite = true;
        //parcourir les lignes et les colonnes de l'échiquier
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if(isWhite){
                    //graphics2D.setPaint(Color.WHITE);
                }else {
                    //graphics2D.setPaint(Color.GRAY);
                }
                //graphics2D.fill(new Rectangle2D.Double((j+1)*CASE_DIMENSION, (i+1)*CASE_DIMENSION,CASE_DIMENSION,CASE_DIMENSION)); // pour dessiner la case
                isWhite = !isWhite; // pour que la prochaine case soit de la couleur opposée en passant d'une colonne a l'autre
            }
            isWhite = !isWhite;  // pour que la prochaine case soit de la couleur opposée en passant d'une ligne a l'autre
        }
    }
}