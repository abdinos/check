package chess.gui;

import chess.ChessGame;
import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * LayeredPaneDemo2.java requires
 * images/dukeWaveRed.gif.
 */
public class ChessGameMainWindow extends JPanel implements ActionListener, MouseListener {

    private JLayeredPane layeredPane;
    private final int windowHeight = 700;
    private final int windowWeight = 700;
    private final int NUMBER_OF_LINE = 8;
    private final int NUMBER_OF_COLUMN = 8;

    private final String CURRENT_PATH = System.getProperty("user.dir");
    private final ChessGame chessGame;
    private Map<Integer, JLabel> labels;
    private ChessPiece chessPieceSelected;
    private  Collection<Movement> chessPieceSelectedMovements;
    private Map<Integer, Color> caseColorChessPieceSelectedMovements;
    private Icon white_rook, black_rook;
    private Icon white_knight, black_knight;
    private Icon white_bishop, black_bishop;
    private Icon white_queen, black_queen;
    private Icon white_king, black_king;
    private Icon white_pawn, black_pawn;



    public ChessGameMainWindow(ChessGame chessGame) {
        this.chessGame = chessGame;
        labels = new HashMap<>();
        chessPieceSelected = null;
        chessPieceSelectedMovements = null;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Create and set up the layered pane.
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(windowWeight, windowHeight));
        layeredPane.addMouseListener(this);

        setupImageChessPiece();

        //Add several labels to the layered pane.
        boolean isWhite = true;
        layeredPane.setLayout(new GridLayout(NUMBER_OF_LINE + 1,NUMBER_OF_COLUMN + 1));
        int index = 0;
        for (int i = 0; i < NUMBER_OF_LINE; i++) {
            JLabel jLabel = createLegendLabel("" + (NUMBER_OF_LINE - i));
            layeredPane.add(jLabel);
            for(int j = 0; j < NUMBER_OF_COLUMN; j++){
                Color color;
                if(isWhite){
                    color = Color.ORANGE;
                }
                else{
                    color = Color.lightGray;
                }
                JLabel label = createColoredLabel(color,index);
                labels.put(index,label);
                layeredPane.add(label);
                isWhite = !isWhite;
                index ++;
            }
            isWhite = !isWhite;
        }

        String[] legend = {"","A","B","C","D","E","F","G","H"};
        for(int i = 0; i < legend.length ; i++){
            JLabel jLabel = createLegendLabel(legend[i]);
            layeredPane.add(jLabel);
        }

        //Add control pane and layered pane to this JPanel.
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(createControlPanel());
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(layeredPane);
    }

    private void setupImageChessPiece(){
        try {
            white_rook = createImageIcon("/images/white_rook.png");
            black_rook = createImageIcon("/images/black_rook.png");

            white_knight = createImageIcon("/images/white_knight.png");
            black_knight = createImageIcon("/images/black_knight.png");

            white_bishop = createImageIcon("/images/white_bishop.png");
            black_bishop = createImageIcon("/images/black_bishop.png");

            white_queen = createImageIcon("/images/white_queen.png");
            black_queen = createImageIcon("/images/black_queen.png");

            white_king = createImageIcon("/images/white_king.png");
            black_king = createImageIcon("/images/black_king.png");

            white_pawn = createImageIcon("/images/white_pawn.png");
            black_pawn = createImageIcon("/images/black_pawn.png");
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private Icon createImageIcon(String path) throws IOException {
        ImageIcon imageIcon = new ImageIcon(CURRENT_PATH + path);

        if (imageIcon.getImage() != null) {
            return imageIcon;
        } else {
            System.err.println("Couldn't find file: " + path);
            System.exit(-1);
            return null;
        }
    }

    //Create and set up a colored label.
    private JLabel createColoredLabel(Color color, int position) {
        JLabel label;
        ChessPiece chessPiece = chessGame.getChessPieceAtPosition(position);
        if(chessPiece != null){
            Icon icon = getImageToChessPiece(chessPiece);
            if(icon != null){
                label = new JLabel(icon);
            }
            else {
                label = new JLabel();
            }
        }
        else {
            label = new JLabel();
        }
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.black);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(0, 0));
        return label;
    }

    private JLabel createLegendLabel(String text){
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        //label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        //label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        label.setPreferredSize(new Dimension(0, 0));
        return label;
    }

    public Icon getImageToChessPiece(ChessPiece chessPiece){
        switch (chessPiece.getName()){
            case "King":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_king;
                }
                return black_king;
            case "Queen":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_queen;
                }
                return black_queen;
            case "Pawn":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_pawn;
                }
                return black_pawn;
            case "Bishop":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_bishop;
                }
                return black_bishop;
            case "Knight":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_knight;
                }
                return black_knight;
            case "Rook":
                if(chessPiece.getPieceColor().isWhite()){
                    return white_rook;
                }
                return black_rook;
            default: return null;
        }
    }

    //Create the control pane for the top of the frame.
    private JPanel createControlPanel() {
        JLabel jLabel = new JLabel("Début de la partie");

        JPanel controls = new JPanel();
        controls.add(jLabel);
        return controls;
    }

    //Make Duke follow the cursor.
    public void mouseMoved(MouseEvent e) {
        /**
        dukeLabel.setLocation(e.getX()-dukeLabel.getWidth()/2,
                e.getY()-dukeLabel.getHeight()/2);
         **/
    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){
        int xCase = (e.getX()/80)-1;
        int yCase = (e.getY()/80);

        int chessPiecePosition = xCase+(8*yCase);

        ChessPiece chessPiece = chessGame.getChessPieceAtPosition(chessPiecePosition);
        boolean isPieceMoved = false;

        if(chessPieceSelected != null){
            if(chessPieceSelected != chessPiece) {
                for (Movement movement : chessPieceSelectedMovements) {
                    if (movement.getFuturePosition() == chessPiecePosition) {
                        updateChessPiece(movement.getFuturePosition(), chessPieceSelected.getPiecePosition(), chessPieceSelected);
                        chessGame.executeChessPieceMovement(movement);
                        chessPieceSelected = null;
                        chessPieceSelectedMovements = null;
                        isPieceMoved = true;
                        break;
                    }
                }
            }
            else{
                System.out.println("same piece");
            }
        }

        if(!isPieceMoved && caseColorChessPieceSelectedMovements != null){
            System.out.println("enlever couleur bleu");
            for(Map.Entry<Integer, Color> entry : caseColorChessPieceSelectedMovements.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
                labels.get(entry.getKey()).setBackground(entry.getValue());
            }
            chessPieceSelected = null;
            chessPieceSelectedMovements = null;
        }
        else {
            if (chessPiece != null) {
                chessPieceSelectedMovements = chessGame.getMovementsForAPiece(chessPiecePosition, chessPiece.getPieceColor());
                if (chessPieceSelectedMovements != null) {
                    chessPieceSelected = chessPiece;
                    caseColorChessPieceSelectedMovements = new HashMap<>();
                    for (Movement movement : chessPieceSelectedMovements) {
                        int futurePosition = movement.getFuturePosition();
                        System.out.println("move : " + movement.getClass() + " vers " + movement.getFuturePosition());
                        caseColorChessPieceSelectedMovements.put(futurePosition, labels.get(futurePosition).getBackground());
                        labels.get(futurePosition).setBackground(Color.BLUE);
                    }
                }
            }
        }

    }

    public void mouseReleased(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    public void mouseDragged(MouseEvent e) {} //do nothing

    public void updateChessPiece(int newPosition, int oldPosition, ChessPiece chessPiece){
        Icon icon = getImageToChessPiece(chessPiece);
        if(icon != null){
            labels.get(newPosition).setIcon(icon);
            labels.get(oldPosition).setIcon(null);
        }
    }

    //Handle user interaction with the check box and combo box.
    public void actionPerformed(ActionEvent e) {
        /**
        String cmd = e.getActionCommand();

        if (ON_TOP_COMMAND.equals(cmd)) {
            if (onTop.isSelected())
                layeredPane.moveToFront(dukeLabel);
            else
                layeredPane.moveToBack(dukeLabel);

        } else if (LAYER_COMMAND.equals(cmd)) {
            int position = onTop.isSelected() ? 0 : 1;
            layeredPane.setLayer(dukeLabel,
                    layerList.getSelectedIndex(),
                    position);
        }
         **/
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("LayeredPaneDemo2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = this;
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}