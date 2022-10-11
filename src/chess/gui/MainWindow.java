package chess.gui;

import chess.ChessGame;
import chess.board.BoardGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Jeu d'échecs"); // titre de l'interface graphique
        setSize(800,1200); // dimension de l'interface 1200x800
        setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        setLocationRelativeTo(null); // on centre notre fenetre

        // btn JOUER
        //setLayout( new GridBagLayout() );
        JButton playBtn = new JButton("JOUER");
        playBtn.setBounds(320,100, 200,40);
        play(playBtn);
        add(playBtn);

        // btn COMMENT JOUER
        JButton howBtn = new JButton("COMMENT JOUER");
        howBtn.setBounds(320,150,200,40);
        howToPlay(howBtn);
        add(howBtn);

        // btn QUITTER
        JButton leaveBtn = new JButton("QUITTER");
        leaveBtn.setBounds(320,200,200,40);
        leaveTheGame(leaveBtn);
        add(leaveBtn);

        //bg
        JLabel background;
        ImageIcon imageIcon = new ImageIcon("images/mainbg.jpg");
        background = new JLabel("",imageIcon,JLabel.HORIZONTAL);
        background.setBounds(0,0,800,900);
        add(background);



    }


    public void play(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    ChessGame chessGame = new ChessGame();
                    BoardGame boardGame = new BoardGame(chessGame);
                    boardGame.createBoard();
                    boardGame.initChessPieceOnBoard();
                    new ChessWindow(new BoardGameGUI(boardGame.getBoard())).setVisible(true);
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void howToPlay(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //TODO : une fenetre qui montre comment jouer
            }
        });
    }

    public void leaveTheGame(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                ChessGame chessGame = null;
                try {
                    chessGame = new ChessGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BoardGame boardGame = new BoardGame(chessGame);
                //chessGame.initChessGame();
                boardGame.createBoard();
                //boardGame.initChessPieceOnBoard();
                BoardGameGUI boardGameGUI = null;
                try {
                    boardGameGUI = new BoardGameGUI(boardGame.getBoard());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new MainWindow().setVisible(true);
            }
        });
    }
}
