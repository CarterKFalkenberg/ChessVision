package Chess_Bot.src;

import Chess_Bot.src.Pieces.Piece;

public class Move {
    
    Piece piece; 
    int start_row;
    int start_column;
    int end_row;
    int end_column;
    boolean promotion;
    int[] enPassantSquare;

    public Move(Piece piece, int start_row, int start_column, int end_row, int end_column){
        this.piece = piece;
        this.start_row = start_row;
        this.start_column = start_column;
        this.end_row = end_row;
        this.end_column = end_column;
        this.promotion = piece.getType() == Constants.PAWN && end_row == 0 || end_row == 7;
        if (piece.getType() == Constants.PAWN && Math.abs(start_row - end_row) == 2){
            this.enPassantSquare = new int[]{end_row, end_column};
        } else {
            this.enPassantSquare = new int[]{-1, -1};
        }
    }

    @Override
    public String toString() {
        // e.g. "WPawn on B3 to B4"
        
        String str = "";
        if (piece.pieceColor == 1){
            str += "W";
        } else {
            str += "B";
        }
        str += Constants.pieceIntToStr.get(this.piece.getType());
        str += " on ";
        str += Constants.columnIntToChar.get(this.start_column);
        str += 8 - this.start_row;
        str += " to ";
        str += Constants.columnIntToChar.get(this.end_column);
        str += 8 - this.end_row;
        
        return str;
    }
}
