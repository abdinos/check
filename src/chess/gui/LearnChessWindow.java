package chess.gui;

import chess.ChessGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LearnChessWindow extends JFrame {

    public LearnChessWindow(){
        setTitle("Apprendre les échecs"); // titre de l'interface graphique
        setSize(800,1200); // dimension de l'interface 1200x800
        setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        setLocationRelativeTo(null); // on centre notre fenetre

        //bg
        JLabel background;
        ImageIcon imageIcon = new ImageIcon("images/bg.jpg");
        background = new JLabel("",imageIcon,JLabel.HORIZONTAL);
        background.setBounds(0,0,800,900);
        add(background);


        // btn Mettre en place le plateau
        //setLayout( new GridBagLayout() );
        JButton btn1 = new JButton("Mettre en place le plateau");
        btn1.setBounds(250,650, 300,40);
        learn1(btn1);
        add(btn1);

        // btn Faire une partie
        JButton btn2 = new JButton("Faire une partie");
        btn2.setBounds(250,700,300,40);
        learn2(btn2);
        add(btn2);

        // btn Effectuer des coups spéciaux
        JButton btn3 = new JButton("Effectuer des coups spéciaux");
        btn3.setBounds(250,750,300,40);
        learn3(btn3);
        add(btn3);

        // btn Employer une stratégie gagnante
        JButton btn4 = new JButton("Employer une stratégie gagnante");
        btn4.setBounds(250,800,300,40);
        learn4(btn4);
        add(btn4);



    }

    public void learn1(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                try {
                    new FirstLearningWindow().setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void learn2(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    new SecondLearningWindow().setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void learn3(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                try {
                    new ThirdLearningWindow().setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void learn4(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    new FourthLearningWindow().setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}