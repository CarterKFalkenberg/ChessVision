package Chess_Bot.src;

import Chess_Bot.src.Pieces.King;

public class Castle extends Move {
    
    boolean shortCastle;

    public Castle(King king, boolean shortCastle){
        super();
        this.piece = king;
        this.promotion = false;
        this.enPassantSquare = new int[]{-1, -1};
        this.shortCastle = shortCastle;
    }

    @Override
    public String toString() {
        // e.g. "WPawn on B3 to B4, en pessant"
        String str = "";
        if (piece.isWhitePiece()){
            str += "White ";
        } else {
            str += "Black ";
        }
        if (shortCastle){
            str += "castles short";            
        } else {
            str += "castles long";
        }
        return str;
    }
}
