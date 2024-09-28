package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

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

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        ArrayList<Move> possibleMoves = getStraightPathMoves(boardColors);
        possibleMoves.addAll(getDiagonalMoves(boardColors));
        return possibleMoves;
    }
}