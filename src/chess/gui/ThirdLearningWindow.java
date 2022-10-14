package chess.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThirdLearningWindow extends JFrame {
    static List<ImageIcon> images = new ArrayList<>();
    JLabel jlabel = null;
    public ThirdLearningWindow() throws IOException {
        setTitle("Effectuer des coups spéciaux"); // titre de l'interface graphique;

        int nbrOfImages = findAllFilesInFolder("images/LearningChess/learn3");

        for (int i=0; i<nbrOfImages; i++){
            images.add(new ImageIcon("images/LearningChess/learn3/"+(i+1)+".png"));
        }
        jlabel = new JLabel(images.get(0), JLabel.CENTER);
        jlabel.setBounds(50, 0, 1200,800);
        add(jlabel);

        JButton suivant = new JButton("Suivant");
        suivant.setBounds(500,800,200,40);
        next(suivant);
        add(suivant);
        JButton precedent = new JButton("Précédent");
        precedent.setBounds(500,850,200,40);
        prev(precedent);
        add(precedent);

        setSize(1200,1200); // dimension de l'interface 1200x800
        setResizable(false); // la fenetre ne sera pas redimensionnable par l'utilisateur
        setDefaultCloseOperation(EXIT_ON_CLOSE); // fermeture de l'interface une fois que l'utilisateur click sur (X)
        setLayout(null); // pour chacun des éléments graphiques il faudra donner les coord. (x,y) & la largeur et la hauteur
        setLocationRelativeTo(null); // on centre notre fenetre

    }
    public static int findAllFilesInFolder(String folder) {
        int c = 0;
        File dir  = new File(folder);
        File[] liste = dir.listFiles();
        for(File item : liste){
            if(item.isFile())
            {
                System.out.format("Nom du fichier: %s%n", item.getName());
                c++;
            }
        }
        return c;
    }

    int c=0;
    public void next(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c++;
                if(c >= images.size()){
                    c = 0;
                }
                jlabel.hide();
                jlabel = new JLabel(images.get(c), JLabel.CENTER);
                jlabel.setBounds(50, 0, 1200,800);
                add(jlabel);
                System.out.println(c);
            }
        });
    }
    public void prev(JButton jButton){
        jButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c--;
                if(c < 0){
                    c = images.size() -1;
                }
                jlabel.hide();
                jlabel = new JLabel(images.get(c), JLabel.CENTER);
                jlabel.setBounds(50, 0, 1200,800);
                add(jlabel);
                System.out.println(c);

            }
        });
    }


}
