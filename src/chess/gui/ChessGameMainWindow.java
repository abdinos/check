package chess.gui;

import chess.ChessGame;
import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;

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


    private final int windowHeight = 700;
    private final int windowWeight = 700;
    private final int NUMBER_OF_LINE = 8;
    private final int NUMBER_OF_COLUMN = 8;

    private final String CURRENT_PATH = System.getProperty("user.dir");
    private JLayeredPane layeredPane;
    private JLabel kingCheckState;
    private JLabel labelInformationCurrentPlayer;
    private final ChessGame chessGame;
    private Map<Integer, JLabel> labels;
    private ChessPiece chessPieceSelected;
    private  Collection<Movement> chessPieceSelectedMovements;
    private Map<Integer, Color> caseColorChessPieceSelectedMovements;
    private Map<Integer, Color> caseColorChessPieceAfterMovements;
    private PieceColor currentPieceColor;
    private boolean isGameEnded;

    private Icon white_rook, black_rook;
    private Icon white_knight, black_knight;
    private Icon white_bishop, black_bishop;
    private Icon white_queen, black_queen;
    private Icon white_king, black_king;
    private Icon white_pawn, black_pawn;



    public ChessGameMainWindow(ChessGame chessGame) {
        this.chessGame = chessGame;
        currentPieceColor = chessGame.getPlayers().get(chessGame.getIndexCurrentPlayer()).getPlayerColor();
        labels = new HashMap<>();
        chessPieceSelected = null;
        chessPieceSelectedMovements = null;
        isGameEnded = false;

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
                    color = new Color(102,51,0);
                }
                else{
                    color = new Color(255,204,51);
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

    /**
     * Set the Icon with the image saved
     */
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

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
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

    /**
     * Create and set up a colored label
     */
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

    /**
     * Create the legend for the board
     */
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

    /**
     * Return the Icon associated to the chess piece
     */
    public Icon getImageToChessPiece(ChessPiece chessPiece){
        if(chessPiece == null){
            return null;
        }
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
        labelInformationCurrentPlayer = new JLabel();
        labelInformationCurrentPlayer.setVerticalAlignment(JLabel.CENTER);
        labelInformationCurrentPlayer.setHorizontalAlignment(JLabel.CENTER);

        kingCheckState = new JLabel();
        kingCheckState.setVerticalAlignment(JLabel.CENTER);
        kingCheckState.setHorizontalAlignment(JLabel.CENTER);

        setCurrentPieceColor(currentPieceColor);

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,1));
        controls.add(labelInformationCurrentPlayer);
        controls.add(kingCheckState);
        return controls;
    }

    //Make Duke follow the cursor.
    public void mouseMoved(MouseEvent e) {}

    public void mousePressed(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        if(isGameEnded){
            return;
        }

        int xCase = (e.getX()/80)-1;
        int yCase = (e.getY()/80);

        int chessPiecePosition = xCase+(NUMBER_OF_LINE * yCase);

        ChessPiece chessPiece = chessGame.getChessPieceAtPosition(chessPiecePosition);
        boolean isPieceMoved = verifyAndMoveChessPiece(chessPiecePosition);

        if(!isPieceMoved && caseColorChessPieceSelectedMovements != null){
            resetCaseColor(caseColorChessPieceSelectedMovements, false);
            chessPieceSelected = null;
            chessPieceSelectedMovements = null;
        }

        if (chessPiece != null && chessPiece.getPieceColor() == currentPieceColor) {
            chessPieceSelectedMovements = chessGame.getMovementsForAPiece(chessPiece);
            if (chessPieceSelectedMovements != null) {
                chessPieceSelected = chessPiece;
                caseColorChessPieceSelectedMovements = new HashMap<>();
                for (Movement movement : chessPieceSelectedMovements) {
                    int futurePosition = movement.getFuturePosition();
                    if(labels.containsKey(futurePosition)) {
                        caseColorChessPieceSelectedMovements.put(futurePosition, labels.get(futurePosition).getBackground());
                        labels.get(futurePosition).setBackground(Color.BLUE);
                    }
                }
            }
        }
    }

    public boolean verifyAndMoveChessPiece(int chessPiecePosition){
        if(chessPieceSelected != null && caseColorChessPieceSelectedMovements != null){
            for (Movement movement : chessPieceSelectedMovements) {
                if (movement.getFuturePosition() == chessPiecePosition) { // The chess piece is moved
                    resetCaseColor(caseColorChessPieceSelectedMovements, false);

                    if(caseColorChessPieceAfterMovements != null){
                        resetCaseColor(caseColorChessPieceAfterMovements, true);
                    }

                    int futurePositionChessPieceMoved = movement.getChessPieceMoved().getPiecePosition();

                    caseColorChessPieceAfterMovements = new HashMap<>();
                    caseColorChessPieceAfterMovements.put(movement.getFuturePosition(),labels.get(movement.getFuturePosition()).getBackground());
                    caseColorChessPieceAfterMovements.put(futurePositionChessPieceMoved, labels.get(futurePositionChessPieceMoved).getBackground());

                    labels.get(movement.getFuturePosition()).setBackground(Color.red);
                    labels.get(futurePositionChessPieceMoved).setBackground(Color.red);

                    chessGame.executeChessPieceMovement(movement);
                    update();

                    chessPieceSelected = null;
                    chessPieceSelectedMovements = null;
                    return true;
                }
            }
        }
        return false;
    }

    private void resetCaseColor(Map<Integer, Color> map, boolean resetRedCases){
        for(Map.Entry<Integer, Color> entry : map.entrySet()){
            if(!resetRedCases) {
                if (labels.get(entry.getKey()).getBackground() != Color.red) {
                    labels.get(entry.getKey()).setBackground(entry.getValue());
                }
            }
            else{
                labels.get(entry.getKey()).setBackground(entry.getValue());
            }
        }
    }

    public void mouseReleased(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    private void update(){
        for(int i = 0; i < (NUMBER_OF_COLUMN * NUMBER_OF_LINE); i++){
            ChessPiece chessPiece = chessGame.getChessPieceAtPosition(i);
            Icon icon = getImageToChessPiece(chessPiece);
            labels.get(i).setIcon(icon);
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    /**
     * Create the play menu and show it.
     */
    public void createAndShoPlayMenu() {
        //Create and set up the window.
        JFrame frame = new JFrame("Partie d\'échec");
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

    public void setCurrentPieceColor(PieceColor pieceColor){
        currentPieceColor = pieceColor;
        labelInformationCurrentPlayer.setText("C'est au tour du joueur " + pieceColor);
    }

    public void endGame(){
        isGameEnded = true;
        String playerColor = "";
        if(currentPieceColor.isWhite()){
            playerColor = "Blanc";
        }
        else{
            playerColor = "Noir";
        }
        labelInformationCurrentPlayer.setText("Le joueur " + playerColor + " gagne la partie");
    }

    public void draw(){
        isGameEnded = true;
        labelInformationCurrentPlayer.setText("Egalité !");
    }

    public void kingCheckState(boolean isKingCheckState){
        if(isKingCheckState){
            String playerColor = "";
            if(currentPieceColor.isWhite()){
                playerColor = "Blanc";
            }
            else{
                playerColor = "Noir";
            }
            kingCheckState.setText("Le roi du joueur " + playerColor + " est en échec");
        }
    }
}