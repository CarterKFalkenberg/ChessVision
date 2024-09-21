package Chess_Bot.src;

import Chess_Bot.src.Pieces.Piece;

public class Move {
    
    Piece piece; 
    int start_row;
    int start_column;
    int end_row;
    int end_column;

    public Move(Piece piece, int start_row, int start_column, int end_row, int end_column){
        this.piece = piece;
        this.start_row = start_row;
        this.start_column = start_column;
        this.end_row = end_row;
        this.end_column = end_column;
    }
}
