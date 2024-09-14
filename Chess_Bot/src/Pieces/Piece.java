package Chess_Bot.src.Pieces;

import Chess_Bot.src.Constants;

public class Piece {

    boolean isWhite;
    int[] square;
    public Piece(boolean isWhite, int[] square) {
        this.isWhite = isWhite;
        this.square = square;
    }

    int getType(){
        return Constants.EMPTY;
    }
    boolean isWhitePiece(){
        return this.isWhite;
    }
    int[] getSquare(){
        return this.square;
    }
    
}
