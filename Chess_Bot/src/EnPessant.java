package Chess_Bot.src;

import Chess_Bot.src.Pieces.Piece;

public class EnPessant extends Move {
    
    Piece pawnTaken;

    public EnPessant(Piece piece, int start_row, int start_column, int end_row, int end_column, Piece pawnTaken){
        super(piece, start_row, start_column, end_row, end_column);
        this.pawnTaken = pawnTaken;
    }

    @Override
    public String toString() {
        // e.g. "WPawn on B3 to B4, en pessant"
        String str = super.toString();
        str += ", en pessant";
        return str;
    }
}
