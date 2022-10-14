package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.King;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class StandardDraw implements InterfaceDraw{

    /**
     * Verify if the game is a draw
     */
    public boolean isDraw(Map<ChessPiece, Collection<Movement>> whiteChessPieceLegalMovement,
                          Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovement){

        return isDeadPosition(whiteChessPieceLegalMovement, blackChessPieceLegalMovement);
    }

    /**
     * Verify if the chess piece of both player are in a dead position or not
     */
    private boolean isDeadPosition(Map<ChessPiece, Collection<Movement>> whiteChessPieceLegalMovements,
                                   Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovements){
        boolean drawWhite = true, drawBlack = true;

        for (Map.Entry<ChessPiece, Collection<Movement>> entry : whiteChessPieceLegalMovements.entrySet()) {
            if(itExistALegalMoveForAChessPieceExceptTheKing(entry.getValue(),entry.getKey())){
                drawWhite = false;
                break;
            }
        }

        for (Map.Entry<ChessPiece, Collection<Movement>> entry : blackChessPieceLegalMovements.entrySet()) {
            if(itExistALegalMoveForAChessPieceExceptTheKing(entry.getValue(),entry.getKey())){
                drawBlack = false;
                break;
            }
        }

        if(drawWhite && drawBlack){
            return true;
        }
        return false;
    }

    /**
     * Verify if it's exist un legal move for a chess piece except the king
     */
    private boolean itExistALegalMoveForAChessPieceExceptTheKing(Collection<Movement> movements, ChessPiece chessPiece){
        if(movements.size() > 0 && !(chessPiece instanceof King)){
          return true;
        }
        return false;
    }
}
