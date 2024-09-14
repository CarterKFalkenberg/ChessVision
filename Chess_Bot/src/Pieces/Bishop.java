package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Bishop extends Piece{

    boolean isWhite;
    int[] square;
    public Bishop(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.BISHOP;
    }
}