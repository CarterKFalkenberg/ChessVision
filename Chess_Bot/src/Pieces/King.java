package Chess_Bot.src.Pieces;
import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

public class King extends Piece{

    public King(boolean isWhite, int[] square) {
        super(isWhite, square);
    }

    @Override
    public int getType() {
       return Constants.KING;
    }

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        // DOES NOT TAKE INTO ACCOUNT CHECKS OR ILLEGAL MOVING INTO A CHECK! DO THAT DURING GAME LOGIC
        ArrayList<Move> moves = new ArrayList<Move>();

        // forward
        if (this.square[0] < 7 && boardColors[this.square[0]+1][this.square[1]] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] + 1, this.square[1])
            );
        }

        // forward right
        if (this.square[0] < 7 && this.square[1] < 7
            && boardColors[this.square[0]+1][this.square[1]+1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] + 1, this.square[1] + 1)
            );
        }

        // forward left
        if (this.square[0] < 7 && this.square[1] > 0
            && boardColors[this.square[0]+1][this.square[1]-1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] + 1, this.square[1] - 1)
            );
        }

        // backwards
        if (this.square[0] > 0 && boardColors[this.square[0]-1][this.square[1]] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] - 1, this.square[1])
            );
        }

        // backwards right
        if (this.square[0] > 0 && this.square[1] < 7
            && boardColors[this.square[0]-1][this.square[1]+1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] - 1, this.square[1] + 1)
            );
        }

        // backwards left
        if (this.square[0] > 0 && this.square[1] > 0
        && boardColors[this.square[0]-1][this.square[1]-1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0] - 1, this.square[1] - 1)
            );
        }

        // right
        if (this.square[1] < 7 && boardColors[this.square[0]][this.square[1]+1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0], this.square[1] + 1)
            );
        }

        // left
        if (this.square[1] > 0 && boardColors[this.square[0]][this.square[1]-1] != this.pieceColor) {
            moves.add(
                new Move(this, this.square[0], this.square[1], this.square[0], this.square[1] - 1)
            );
        }

        return moves;

    }

}