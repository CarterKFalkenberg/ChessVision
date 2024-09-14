package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Rook extends Piece{

    boolean isWhite;
    int[] square;
    public boolean kingsRook;
    public Rook(boolean isWhite, int[] square, boolean kingsRook) {
        super(isWhite, square);
        this.kingsRook = kingsRook;
    }

    @Override
    public int getType() {
       return Constants.ROOK;
    }
}