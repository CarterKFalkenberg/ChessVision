package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Pawn extends Piece{

    boolean isWhite;
    int[] square;
    public Pawn(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.PAWN;
    }
}