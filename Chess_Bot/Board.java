package Chess_Bot;

import java.util.ArrayList;

class Board{

    int[][] boardColors;  
    int[][] boardPieces;
    Piece[] whitePieces;
    Piece[] blackPieces;
    int[] enPassantSquare;
    boolean whiteToPlay = true;
    boolean check = false;
    boolean whiteShortCastleRights = true;
    boolean whiteLongCastleRights = true; 
    boolean blackShortCastleRights = true;
    boolean blackLongCastleRights = true;
    
   
    Board(){
        this.boardColors = new int[8][8];
        this.boardPieces = new int[8][8];
        this.whitePieces = new Piece[16];
        this.blackPieces = new Piece[16];
        this.enPassantSquare = new int[2];

        // enPassantSquare is the square that a pawn just moved two pieces to, if that exists
        this.enPassantSquare[0] = -1;
        this.enPassantSquare[1] = -1;
    }

    ArrayList<Move> getPossibleMoves(){
        /*
         *  Returns a list of all possible moves in the board's position 
         *  Returns empty list if no possible moves
         *      - if no moves and in check: checkmate
         *      - if no moves and not in check: stalemate
         */
        return null;
    }

    void makeMove(Piece piece, int row, int col){
        /*
         * Moves piece to (row, col)
         * Assumes move is valid. Should only be called from a move returned in possible_moves
         */

         // Set board[start_square] = 'X'
         // Set board[end_square] = piece
         // Set enPassantSquare to square or None
         // Set check 
         // Set castle_rights for current color 
         // Flip self.white_to_play 

    }

    double heuristic(){
        /*
         * Returns the evaluation for the board's current position
         * Zero-sum: i.e. eval for white = -(Eval for black)
         */

         // 1. Material difference is just material white - material black, don't adjust that
         // the following will all increment the above slightly  
             // the amount that they move the above score may need to be learned 
             // NOTE: You must take into account BOTH SIDES for all of the following, this is zero-sum!
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