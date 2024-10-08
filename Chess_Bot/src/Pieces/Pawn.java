package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

import java.lang.Math;


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

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        // ASSUMES PAWN IS NOT PROMOTED. IN THAT CASE, IT SHOULD HAVE BECAME A QUEEN
        ArrayList<Move> moves = new ArrayList<Move>();
        int opponentColor = -1 * this.pieceColor;
        int newRow;
        int newCol;

        // check en passant
        if (this.square[0] == enPassantSquare[0] && Math.abs(this.square[1] - enPassantSquare[1]) == 1){
            newRow = enPassantSquare[0];
            newRow += opponentColor;    // white moves -1, black moves +1
            moves.add(
                new Move(this, this.square[0], this.square[1], newRow, enPassantSquare[1])
            );
        }

        // check 1 square forward 
        newRow = this.square[0] + opponentColor;   // white moves -1, black moves +1
        if (boardColors[newRow][this.square[1]] == Constants.EMPTY){
            moves.add(
                new Move(this, this.square[0], this.square[1], newRow, this.square[1])
            );
            // if on starting square, check 2 squares forwards
            if (this.square[0] == 1 && this.pieceColor == Constants.BLACK 
            || this.square[0] == 6 && this.pieceColor == Constants.WHITE) {
                newRow += opponentColor;
                if (boardColors[newRow][this.square[1]] == Constants.EMPTY){
                    moves.add(
                        new Move(this, this.square[0], this.square[1], newRow, this.square[1])
                    );
                }
            }
        }

        // check right diagonal (not en passant)
        newRow = this.square[0] + opponentColor;   // white moves -1, black moves +1
        newCol = this.square[1] + 1;
        if (boardColors[newRow][newCol] == opponentColor){
            moves.add(
                new Move(this, this.square[0], this.square[1], newRow, newCol)
        );
        }

        // check left diagonal (not en passant)
        newCol = this.square[1] - 1;
        if (boardColors[newRow][newCol] == opponentColor){
            moves.add(
                new Move(this, this.square[0], this.square[1], newRow, newCol)
        );
        }

        return moves;
    }
}