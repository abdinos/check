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

    private JFrame jFrame = this;;

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

        //chessGame = new ChessGame();
    }



    public void play(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    boardGame = new BoardGame(chessGame);
                    boardGame.createBoard();
                    boardGame.initChessPieceOnBoard();
                    chessWindow = new ChessWindow();
                    dispose();

                    boardGameGUI = new BoardGameGUI(boardGame.getBoard());
                }
                catch (IOException ex) {
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

    public JFrame getJFrame(){
        return this;
    }

    /**
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow();
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                //ChessGame chessGame = null;
                //chessGame = new ChessGame();
                //BoardGame boardGame = new BoardGame(chessGame);
                //chessGame.initChessGame();
                //boardGame.createBoard();
                //boardGame.initChessPieceOnBoard();
                //BoardGameGUI boardGameGUI = null;
                try {
                    boardGameGUI = new BoardGameGUI(boardGame.getBoard(),mainWindow.chessWindow.getJFrame());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mainWindow.setVisible(true);
            }
        });
    }
    **/
}
