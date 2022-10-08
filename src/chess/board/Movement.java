package chess.board;

import chess.chessPiece.ChessPiece;

public abstract class Movement {

    private final BoardGame boardGame;
    private final ChessPiece chessPieceMoved;
    private final ChessPiece chessPieceAttacked;
    private final ChessPiece chessPiecePromoted;
    private final int futurePosition;
    private final boolean isAttacking;
    private final boolean isCheckKing;
    private final boolean isPromoting;

    public Movement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final ChessPiece chessPieceAttacked,
                    ChessPiece chessPiecePromoted, final int futurePosition, boolean isAttacking, boolean isPromoting,
                    boolean isCheckKing){
        this.boardGame = boardGame;
        this.futurePosition = futurePosition;
        this.chessPieceMoved = chessPieceMoved;
        this.chessPieceAttacked = chessPieceAttacked;
        this.chessPiecePromoted = chessPiecePromoted;
        this.isAttacking = isAttacking;
        this.isPromoting = isPromoting;
        this.isCheckKing = isCheckKing;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public ChessPiece getChessPieceMoved() {
        return chessPieceMoved;
    }

    public ChessPiece getChessPieceAttacked() {
        return chessPieceAttacked;
    }

    public ChessPiece getChessPiecePromoted() {
        return chessPiecePromoted;
    }

    public int getFuturePosition() {
        return futurePosition;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isKingCheck() {
        return isCheckKing;
    }

    public boolean isPromoting() {
        return isPromoting;
    }
}
