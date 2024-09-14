package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Rook extends Piece{

    boolean isWhite;
    int[] square;
    public Rook(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.ROOK;
    }
}