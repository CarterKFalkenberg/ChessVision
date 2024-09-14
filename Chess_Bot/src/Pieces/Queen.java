package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Queen extends Piece{

    boolean isWhite;
    int[] square;
    public Queen(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.QUEEN;
    }
}