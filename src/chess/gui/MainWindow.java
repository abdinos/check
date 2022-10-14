package chess.gui;

import chess.ChessGame;
import chess.board.BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {

    private ChessGame chessGame = null;
    private BoardGame boardGame;

    private ChessWindow chessWindow;
    private BoardGameGUI boardGameGUI = null;

    private JFrame jFrame = this;
    ;

    public MainWindow() {
        setTitle("Jeu d'échecs"); // titre de l'interface graphique
        setSize(800, 1200); // dimension de l'interface 1200x800
        setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        setLocationRelativeTo(null); // on centre notre fenetre

        // btn JOUER
        //setLayout( new GridBagLayout() );
        JButton playBtn = new JButton("JOUER");
        playBtn.setBounds(300, 700, 200, 40);
        play(playBtn);
        add(playBtn);

        // btn COMMENT JOUER
        JButton howBtn = new JButton("COMMENT JOUER");
        howBtn.setBounds(300, 750, 200, 40);
        howToPlay(howBtn);
        add(howBtn);

        // btn QUITTER
        JButton leaveBtn = new JButton("QUITTER");
        leaveBtn.setBounds(300, 800, 200, 40);
        leaveTheGame(leaveBtn);
        add(leaveBtn);

        //bg
        JLabel background;
        ImageIcon imageIcon = new ImageIcon("images/bg.jpg");
        background = new JLabel("", imageIcon, JLabel.HORIZONTAL);
        background.setBounds(0, 0, 800, 900);
        add(background);

        //chessGame = new ChessGame();
    }


    public void play(JButton jButton) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChessGame chessGame = new ChessGame();
                chessGame.createPlayers();
                chessGame.initChessGame();
                ChessGameMainWindow chessGameMainWindow = new ChessGameMainWindow(chessGame);
                chessGame.setChessGameMainWindow(chessGameMainWindow);
                chessGameMainWindow.createAndShoPlayMenu();
                dispose();
            }
        });
    }

    public void howToPlay(JButton jButton) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LearnChessWindow().setVisible(true);
                dispose();
            }
        });
    }

    public void leaveTheGame(JButton jButton) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public JFrame getJFrame() {
        return this;
    }
}
