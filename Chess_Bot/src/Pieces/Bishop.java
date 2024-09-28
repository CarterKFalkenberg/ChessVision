package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

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

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        return getDiagonalMoves(boardColors);
    }
}