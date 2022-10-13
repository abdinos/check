package chess.board;

import chess.Movement.Movement;
import chess.chessPiece.ChessPiece;
import chess.chessPiece.King;

import java.util.Collection;
import java.util.Map;

public class StandardDraw implements InterfaceDraw{

    public boolean isDraw(Map<ChessPiece, Collection<Movement>> whiteChessPieceLegalMovement,
                          Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovement){

        return isDeadPosition(whiteChessPieceLegalMovement, blackChessPieceLegalMovement);
    }

    private boolean isDeadPosition(Map<ChessPiece, Collection<Movement>> whiteChessPieceLegalMovements,
                                   Map<ChessPiece,Collection<Movement>> blackChessPieceLegalMovements){
        boolean drawWhite = false, drawBlack = false;

        for(Collection<Movement> movements : whiteChessPieceLegalMovements.values()){
            if(!itExistALegalMoveForAChessPieceExceptTheKing(movements)){
                drawWhite = true;
                break;
            }
        }

        for(Collection<Movement> movements : blackChessPieceLegalMovements.values()){
            if(!itExistALegalMoveForAChessPieceExceptTheKing(movements)){
                drawBlack = true;
                break;
            }
        }

        if(drawWhite && drawBlack){
            return true;
        }
        return false;
    }

    private boolean itExistALegalMoveForAChessPieceExceptTheKing(Collection<Movement> movements){
        if(movements.size() > 0){
            Movement movement = movements.iterator().next();
            if(!(movement.getChessPieceMoved() instanceof King)){
                return true;
            }
        }
        return false;
    }
}
