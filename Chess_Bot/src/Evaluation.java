package Chess_Bot.src;

import java.util.ArrayList;


import Chess_Bot.src.Pieces.Piece;

public class Evaluation {
    public static double evaluation(Board board){
        /*
         * Returns the evaluation for white in the board's current position
         * Assumes that checkmate is already checked for (no pun intended)
         * Zero-sum, i.e. eval(black) = -1 * eval(white)
         */

        // 1. Material difference is just material white - material black, don't adjust that
        int pieceDifferential = pieceDifferential(board.whitePieces, board.blackPieces);

        // REMEMBER ZERO SUM FOR THE FOLLOWING:
        // 2. King safety -> based on how safe the king is, + or - a certain amount 
            // ideally, king is behind 3 pawns, all pawns on the same rank
            // king is weaker in the center, when on a diagonal of a bishop or queen, or on the file of a rook or queen
            // king is stronger with pieces near it 
        // 3. Pawn structure 
            // isolated pawns are bad
            // doubled pawns are bad 
        // 4. Space
            // differential between the number of squares you are attacking on your opponent's side of the board and the number of squares they are attacking on yours.
        // 5. Bishop pair 
        // 6. Passed pawn

        return 0.0;
    }

    public static int pieceDifferential(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces){
        /*
         * Returns white piece value total minus black piece value total
         * Pawn -> 1
         * Bishop, Knight -> 3
         * Rook -> 5
         * Queen -> 9
         * King -> 0    (both players always have a king)
         */
        int whiteValue = 0;
        int blackValue = 0;
        
        for (Piece piece : whitePieces){
            whiteValue += Constants.pieceToValue.get(piece.getType());
        }
        for (Piece piece : blackPieces){
            blackValue += Constants.pieceToValue.get(piece.getType());
        }

        return whiteValue - blackValue;
    }

    public static int kingSafetyDifferential(Board board){

        return 0;
    }

}
