package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

public class Rook extends Piece{

    public boolean kingsRook;
    public Rook(boolean isWhite, int[] square, boolean kingsRook) {
        super(isWhite, square);
        this.kingsRook = kingsRook;
    }

    @Override
    public int getType() {
       return Constants.ROOK;
    }

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        return getStraightPathMoves(boardColors);
    }
    
}