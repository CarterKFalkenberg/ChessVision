package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class King extends Piece{

    boolean isWhite;
    int[] square;
    public King(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.KING;
    }
}