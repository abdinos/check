package chess.chessPiece;

public enum PieceColor {
    WHITE{
        @Override
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }
    },
    BLACK{
        @Override
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }
    };

    /**
     * Return a value depending on the piece color
     */
    public abstract int getDirection();

    /**
     * Return if the piece color is black or not
     */
    public abstract boolean isBlack();

    /**
     * Return of the piece color is white or not
     */
    public abstract boolean isWhite();

}
