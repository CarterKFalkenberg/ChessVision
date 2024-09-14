package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Piece {

    boolean isWhite;
    int[] square;
    public Piece(boolean isWhite, int[] square) {
        this.isWhite = isWhite;
        this.square = square;
    }

    public int getType(){
        return Constants.EMPTY;
    }
    public boolean isWhitePiece(){
        return this.isWhite;
    }

    public int[] getSquare(){
        return this.square;
    }
    
}
