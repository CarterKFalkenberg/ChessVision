package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Knight extends Piece{

    boolean isWhite;
    int[] square;
    public Knight(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.KNIGHT;
    }
}