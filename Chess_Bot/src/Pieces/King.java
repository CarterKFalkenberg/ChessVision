package Chess_Bot.src.Pieces;
import java.util.ArrayList;

import Chess_Bot.src.Castle;
import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

public class King extends Piece{

    boolean shortCastleRights;
    boolean longCastleRights;

    public King(boolean isWhite, int[] square, boolean shortCastleRights, boolean longCastleRights) {
        super(isWhite, square);
        this.shortCastleRights = shortCastleRights;
        this.longCastleRights = longCastleRights;
    }

    @Override
    public int getType() {
       return Constants.KING;
    }

    @Override
    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        // returns are theoretically possible moves
        // DOES NOT TAKE INTO ACCOUNT CHECKS OR ILLEGAL MOVING INTO A CHECK! DO THAT DURING GAME LOGIC
        
        ArrayList<Move> moves = new ArrayList<Move>();

        // first, check for castling. Castling is possible if you have rights and there are no pieces in the way

        // castle short
        if (this.shortCastleRights){
            boolean safe = true;

            // make sure there are no pieces between the king and the rook
            if (this.isWhite){
                if (boardColors[7][5] != Constants.EMPTY || boardColors[7][6] != Constants.EMPTY){
                    safe = false;
                }
            } else {
                if (boardColors[0][5] != Constants.EMPTY || boardColors[0][6] != Constants.EMPTY){
                    safe = false;
                }
            }

            if (safe){
                moves.add(
                    new Castle(this, true)
                );
            }
        }

        // castle long
        if (this.longCastleRights){
            boolean safe = true;

            // make sure there are no pieces between the king and the rook
            if (this.isWhite){
                if (boardColors[7][1] != Constants.EMPTY || boardColors[7][2] != Constants.EMPTY || boardColors[7][3] != Constants.EMPTY){
                    safe = false;
                }
            } else {
                if (boardColors[0][1] != Constants.EMPTY || boardColors[0][2] != Constants.EMPTY || boardColors[0][3] != Constants.EMPTY){
                    safe = false;
                }
            }

            if (safe){
                moves.add(
                    new Castle(this, false)
                );
            }
        }


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