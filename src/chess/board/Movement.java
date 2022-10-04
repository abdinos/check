package chess.board;

import chess.chessPiece.ChessPiece;
import chess.chessPiece.PieceColor;

public abstract class Movement {

    private final BoardGame boardGame;
    private final ChessPiece chessPiece;
    private final int futurePosition;

    public Movement(final BoardGame boardGame, final ChessPiece chessPiece, final int futurePosition){
        this.boardGame = boardGame;
        this.chessPiece = chessPiece;
        this.futurePosition = futurePosition;
    }

    public int getFuturePosition(){ return futurePosition; }

    public ChessPiece getChessPiece(){
        return chessPiece;
    }


}
