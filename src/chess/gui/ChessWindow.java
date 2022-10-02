package chess.gui;

import chess.board.BoardGame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChessWindow extends JFrame {

    public ChessWindow() throws IOException {
        setTitle("Jeu d'échecs"); // titre de l'interface graphique
        setSize(800,1200); // dimension de l'interface 1200x800
        setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        setLocationRelativeTo(null); // on centre notre fenetre


        //case de depart
        JLabel origine = new JLabel("Origine");
        origine.setForeground(new Color(255,255,255));
        origine.setFont(new Font(Font.DIALOG, Font.ITALIC, 10));
        origine.setBounds(610,60,50,15);
        add(origine);
        JTextField tfCaseDepart = new JTextField();
        tfCaseDepart.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        tfCaseDepart.setBounds(600,80,60,35);
        add(tfCaseDepart);

        // btn 'vers'
        JButton btnDeplacement = new JButton();
        btnDeplacement.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        btnDeplacement.setText("vers");
        btnDeplacement.setBounds(650,80,55,35);
        add(btnDeplacement);

        //case d'arrivée
        JLabel destination = new JLabel("Destination");
        destination.setForeground(new Color(255,255,255));
        destination.setFont(new Font(Font.DIALOG, Font.ITALIC, 10));
        destination.setBounds(700,60,80,15);
        add(destination);
        JTextField tfCaseArrivée = new JTextField();
        tfCaseArrivée.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        tfCaseArrivée.setBounds(700,80,60,35);
        add(tfCaseArrivée);

        //Echiquier
        BoardGame boardGame = new BoardGame();
        boardGame.createBoard();
        boardGame.initChessPieceOnBoard();
        boardGame.setBounds(50,20,800,1200);
        add(boardGame);

        //bg
        JLabel background;
        ImageIcon imageIcon = new ImageIcon("images/ppp.jpg");
        background = new JLabel("",imageIcon,JLabel.CENTER);
        background.setBounds(0,0,850,1300);
        add(background);

    }
}
