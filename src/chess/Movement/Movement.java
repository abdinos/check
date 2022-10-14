package chess.Movement;

import chess.board.BoardGame;
import chess.chessPiece.ChessPiece;

public abstract class Movement {

    /**
     * The board game
     */
    private final BoardGame boardGame;

    /**
     * The chess piece moved in the movement
     */
    private final ChessPiece chessPieceMoved;

    /**
     * The chess piece attack in the movement, it can ben null
     */
    private final ChessPiece chessPieceAttacked;

    /**
     * The new piece after a promotion by a pawn, it can be null
     */
    private final ChessPiece chessPiecePromoted;

    /**
     * The future position of the chess piece after the movement
     */
    private final int futurePosition;

    /**
     * Indicate if the chess piece in the movement is attacking another chess piece
     */
    private final boolean isAttacking;

    /**
     * Indicate if the chess piece in the movement is making the enemy king is check state
     */
    private final boolean isCheckKing;

    /**
     * Indicate if the pawn in the movement is promoting
     */
    private final boolean isPromoting;

    /**
     * Indicate if the movement is the "en passant" move
     */
    private final boolean isMoveSpecialPawn;

    /**
     * Indicate if the movement is the castle move
     */
    private final boolean isCastling;



    public Movement(final BoardGame boardGame, final ChessPiece chessPieceMoved, final ChessPiece chessPieceAttacked,
                    ChessPiece chessPiecePromoted, final int futurePosition, boolean isAttacking, boolean isPromoting,
                    boolean isCheckKing, boolean isMoveSpecialPawn, final boolean isCastling){
        this.boardGame = boardGame;
        this.futurePosition = futurePosition;
        this.chessPieceMoved = chessPieceMoved;
        this.chessPieceAttacked = chessPieceAttacked;
        this.chessPiecePromoted = chessPiecePromoted;
        this.isAttacking = isAttacking;
        this.isPromoting = isPromoting;
        this.isCheckKing = isCheckKing;
        this.isMoveSpecialPawn = isMoveSpecialPawn;
        this.isCastling = isCastling;
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

    public boolean isCheckKing() {
        return isCheckKing;
    }

    public boolean isPromoting() {
        return isPromoting;
    }

    public boolean isMoveSpecialPawn(){ return isMoveSpecialPawn; }

    public boolean isCastling(){ return isCastling; }
}
