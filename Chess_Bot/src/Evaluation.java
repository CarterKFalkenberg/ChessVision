package Chess_Bot.src;

public class Evaluation {
    public static int evaluation(){
        /*
         * Returns the evaluation for the board's current position
         * Assumes that checkmate is already checked for (no pun intended)
         * Zero-sum: i.e. eval for white = -(Eval for black)
         */

        // 1. Material difference is just material white - material black, don't adjust that
        // the following will all increment the above slightly  
            // the amount that they move the above score may need to be learned 
            // NOTE: You must take into account BOTH SIDES for all of the following, this is zero-sum!

        // REMEMBER ZERO SUM FOR THE FOLLOWING:
        // 2. King safety -> based on how safe the king is, + or - a certain amount 
            // ideally, king is behind 3 pawns, all pawns on the same rank
            // king is weaker in the center, when on a diagonal of a bishop or queen, or on the file of a rook or queen
            // king is stronger with pieces near it 
        // 3. Piece activity 
            // piece controls more squares, especially those in the center or the opponent's territory (preferable towards king)
        // 4. Pawn structure 
            // isolated pawns are bad
            // doubled pawns are bad 
        // 5. Space
            // differential between the number of squares you are attacking on your opponent's side of the board and the number of squares they are attacking on yours.
        // 6. Bishop pair 
        // 7. Passed pawn

        return 0.0;
    }
}
