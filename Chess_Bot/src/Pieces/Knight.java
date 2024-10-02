package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

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

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        // check all 8 possible moves
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        int row, col;
    
        // forward right
        row = this.square[0] + 2;
        col = this.square[1] + 1;
        if (row < 8 && col < 8 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // forward left
        row = this.square[0] + 2;
        col = this.square[1] - 1;
        if (row < 8 && col >= 0 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // right forward 
        row = this.square[0] + 1;
        col = this.square[1] + 2;
        if (row < 8 && col < 8 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // left forward 
        row = this.square[0] + 1;
        col = this.square[1] - 2;
        if (row < 8 && col >= 0 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // backward right
        row = this.square[0] - 2;
        col = this.square[1] + 1;
        if (row >= 0 && col < 8 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // backward left 
        row = this.square[0] - 2;
        col = this.square[1] - 1;
        if (row >= 0 && col >= 0 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }

        // right backward
        row = this.square[0] - 1;
        col = this.square[1] + 2;
        if (row >= 0 && col < 8 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        } 

        // left backward
        row = this.square[0] - 1;
        col = this.square[1] - 2;
        if (row >= 0 && col >= 0 && boardColors[row][col] != this.pieceColor){
            possibleMoves.add(
                new Move(this, this.square[0], this.square[1], row, col)
            );
        }  

        return possibleMoves;
    }
}