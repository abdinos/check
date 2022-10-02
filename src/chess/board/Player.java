package chess.board;

import chess.chessPiece.PieceColor;

public class Player {

    private String name;
    private PieceColor playerColor;
    private boolean isKingCheck = false;

    public Player(String name, PieceColor playerColor){
        this.name = name;
        this.playerColor = playerColor;
    }

    public String getName(){
        return name;
    }

    public PieceColor getPlayerColor(){
        return playerColor;
    }

    public boolean isKingCheck(){
        return isKingCheck;
    }

    public void setKingCheck(boolean isKingCheck){
        this.isKingCheck = isKingCheck;
    }
}
