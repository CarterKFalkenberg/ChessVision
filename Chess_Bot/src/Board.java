package Chess_Bot.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import Chess_Bot.src.Pieces.Bishop;
import Chess_Bot.src.Pieces.King;
import Chess_Bot.src.Pieces.Knight;
import Chess_Bot.src.Pieces.Pawn;
import Chess_Bot.src.Pieces.Piece;
import Chess_Bot.src.Pieces.Queen;
import Chess_Bot.src.Pieces.Rook;


class Board{

    int[][] boardColors;  
    int[][] boardPiecesInt;
    ArrayList<ArrayList<Piece>> boardPiecesObject;
    ArrayList<Piece> whitePieces;
    ArrayList<Piece> blackPieces;
    int[] enPassantSquare;
    boolean whiteToPlay = true;
    boolean check = false;
    boolean whiteShortCastleRights = true;
    boolean whiteLongCastleRights = true; 
    boolean blackShortCastleRights = true;
    boolean blackLongCastleRights = true;
    
   
    Board() throws IOException{
        this.boardColors = new int[8][8];
        this.boardPiecesInt = new int[8][8];
        ArrayList<Piece> dummy_row = new ArrayList<Piece>(Collections.nCopies(8, null));
        this.boardPiecesObject = new ArrayList<ArrayList<Piece>>(Collections.nCopies(8, dummy_row));
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        this.enPassantSquare = new int[2];

        // enPassantSquare is the square that a pawn just moved two pieces to, if that exists
        this.enPassantSquare[0] = -1;
        this.enPassantSquare[1] = -1;

        initBoard();
    }

    void initPiecesByType(int[] rows, int[] cols, int pieceType) throws IOException{
        /*
         * add given piece to this.whitePieces and this.blackPieces at proper indices
         * when creating a piece, make sure to adjust this.boardColors and this.boardPiecesInt as well
         * 
         * rows, cols: each are lists of the indices to use for the pieces.  
         * pieceType: from Constants.java
         */
        for (int row: rows) {
            for (int col: cols){
                int[] square = {row, col};
                Piece piece;
                switch (pieceType) {
                    case Constants.PAWN:
                        piece = new Pawn(row == 6, square);
                        break;
                    case Constants.KNIGHT:
                        piece = new Knight(row == 7, square);
                        break;
                    
                    case Constants.BISHOP:
                        piece = new Bishop(row == 7, square);
                        break;
                    
                    case Constants.ROOK:
                        piece = new Rook(row == 7, square, col == 7);
                        break;

                    case Constants.QUEEN:
                        piece = new Queen(row == 7, square);
                        break;

                    case Constants.KING:
                        piece = new King(row == 7, square);
                        break;
                
                    default:
                        throw new IOException("Invalid piece type: " + pieceType);
                }
                if (row == 6 || row == 7){      
                    this.whitePieces.add(piece);
                } else {
                    this.blackPieces.add(piece);
                }
                this.boardColors[row][col] = (row == 6 || row == 7) ? Constants.WHITE : Constants.BLACK;
                this.boardPiecesInt[row][col] = pieceType;
                this.boardPiecesObject.get(row).set(col, piece);
            }
        }
    }

    void initBoard() throws IOException{
        /*
         * create all white and black pieces, add to this.whitePieces and this.blackPieces
         * when creating a piece, make sure to adjust this.boardColors and this.boardPiecesInt as well
         */

        // PAWNS - 8 each
        int[] row_indices = {1, 6};    
        int[] col_indices = {0, 1, 2, 3, 4, 5, 6, 7}; 
        initPiecesByType(row_indices, col_indices, Constants.PAWN);

        // ROOKS - 2 each
        row_indices = new int[]{0, 7}; 
        col_indices = new int[]{0, 7};
        initPiecesByType(row_indices, col_indices, Constants.ROOK);

        // KNIGHTS - 2 each
        col_indices[0]++;
        col_indices[1]--;
        initPiecesByType(row_indices, col_indices, Constants.KNIGHT);

        // BISHOPS - 2 each 
        col_indices[0]++; 
        col_indices[1]--;
        initPiecesByType(row_indices, col_indices, Constants.BISHOP);

        // QUEENS - 1 each
        col_indices = new int[]{3};
        initPiecesByType(row_indices, col_indices, Constants.QUEEN);

        // KINGS - 1 each
        col_indices[0]++;
        initPiecesByType(row_indices, col_indices, Constants.KING);
    }

    ArrayList<Move> getPossibleMoves(){
        /*
         *  Returns a list of all possible moves in the board's position 
         *  Returns empty list if no possible moves
         *      - if no moves and in check: checkmate
         *      - if no moves and not in check: stalemate
         */
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        for (ArrayList<Piece> row : this.boardPiecesObject){
            for (Piece piece : row){
                // If piece color is the same as color whose turn it is
                // TODO: only add possible moves of a piece if it is not pinned!
                if (piece.isWhitePiece() == this.whiteToPlay){
                    possibleMoves.addAll(piece.getPossibleMoves(this.boardColors, this.enPassantSquare));
                }
            }
        }

        return possibleMoves;
    }

    void makeMove(Piece piece, int row, int col) throws Exception{
        /*
         * Moves piece to (row, col)
         * Assumes move is valid. Should only be called from a move returned in possible_moves
         */

         /*
            int[][] boardColors;  
            int[][] boardPiecesInt;
            ArrayList<Piece> whitePieces;
            ArrayList<Piece> blackPieces;
            int[] enPassantSquare;
            boolean whiteToPlay = true;
            boolean check = false;
            boolean whiteShortCastleRights = true;
            boolean whiteLongCastleRights = true; 
            boolean blackShortCastleRights = true;
            boolean blackLongCastleRights = true;
          */

        int[] start_square = piece.getSquare();

        // boardColors: original square becomes empty, new square becomes color of piece 
        this.boardColors[start_square[0]][start_square[1]] = Constants.EMPTY;

        // boardPiecesInt: original square becomes empty, new square becomes int of piece
        this.boardPiecesInt[start_square[0]][start_square[1]] = Constants.EMPTY;

        // boardPiecesObject: original square becomes empty, new square becomes int of piece. store captured piece
        Piece captured = this.boardPiecesObject.get(start_square[0]).get(start_square[1]);
        // TODO: pawn promotion
        this.boardPiecesObject.get(row).set(col, piece);
        
        
        // whitePieces/blackPiece: remove a piece if it is captured (ie if it starts on [row,col]
        // if you captured a rook, update castle rights for opponent if neccessary
        if (captured != null){
            if (captured.isWhitePiece()){
                this.whitePieces.remove(captured);
            } else {    
                this.blackPieces.remove(captured);
            }
        } 
        
        // enPassantSquare automatically becomes [-1, -1] if not already
        // TODO: enPassantSquare should not become [-1, -1] if pawn moves 2 squares forwards
        this.enPassantSquare[0] = -1;
        this.enPassantSquare[1] = -1;

        // update castle rights if rook or king moves OR if you capture a rook (opponents castle rights lost)
        updateCastleRights(piece, captured);

        // whiteToPlay = not whiteToPlay 
        this.whiteToPlay = !(this.whiteToPlay);
        
        // check if opponent is in check 
        this.check = inCheck();
    }

    void updateCastleRights(Piece piece, Piece captured){
        /*
         * Update castle rights given the original piece and the captured piece
         */
        // check if king or rook moved
        if (piece.getType() == Constants.KING) {
            if (piece.isWhitePiece()){
                this.whiteShortCastleRights = false;
                this.whiteLongCastleRights = false;
            } else {
                this.blackShortCastleRights = false;
                this.blackLongCastleRights = false;
            }
        } else if (piece.getType() == Constants.ROOK) {
            Rook pieceRook = (Rook) piece;
            if (pieceRook.isWhitePiece()){
                if (pieceRook.kingsRook){
                    this.whiteShortCastleRights = false;
                } else {
                    this.whiteLongCastleRights = false;
                }
            } else {
                if (pieceRook.kingsRook){
                    this.blackShortCastleRights = false;
                } else {
                    this.blackLongCastleRights = false;
                }
            }
        }

        // check if a rook was captured 
        if (captured != null && captured.getType() == Constants.ROOK) {
            Rook capturedRook = (Rook) captured;
            if (capturedRook.isWhitePiece()){
                if (capturedRook.kingsRook){
                    this.whiteShortCastleRights = false;
                } else {
                    this.whiteLongCastleRights = false;
                }
            } else {
                if (capturedRook.kingsRook){
                    this.blackShortCastleRights = false;
                } else {
                    this.blackLongCastleRights = false;
                }
            }
        }

    }

    boolean inCheck() {
        /*
         * Check if current turn's player is in check
         */
        return false;
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

    @Override
    public String toString() {
        /*
            this.boardColors = new int[8][8];
            this.boardPiecesInt = new int[8][8];
            this.whitePieces = new ArrayList<Piece>();
            this.blackPieces = new ArrayList<Piece>();
            this.enPassantSquare = new int[2];
         */
        
        String returnStr = "";

        // board colors
        for (int[] row : this.boardColors) {
            for (int colorInt : row) {
                String colorStr;
                if (colorInt == Constants.EMPTY){
                    colorStr = "X";
                } else if (colorInt == Constants.WHITE){
                    colorStr = "W"; 
                } else {
                    colorStr = "B";
                }
                returnStr += colorStr + " ";
            }
            returnStr += "\n";
        }

        returnStr += "\n";
        // board pieces 
        for (int[] row : this.boardPiecesInt) {
            for (int pieceInt : row) {
                String pieceStr;
                switch (pieceInt) {
                    case Constants.PAWN:
                        pieceStr = "P";
                        break;
                    case Constants.KNIGHT:
                        pieceStr = "N";
                        break;
                    case Constants.BISHOP:
                        pieceStr = "B";
                        break;
                    case Constants.ROOK:
                        pieceStr = "R";
                        break;
                    case Constants.QUEEN:
                        pieceStr = "Q";
                        break;
                    case Constants.KING:
                        pieceStr = "K";
                        break;
                    default:
                        pieceStr = "X";
                }
                returnStr += pieceStr + " ";
            }
            returnStr += "\n";
        }

        returnStr += "Num black pieces in this.blackPieces: " + this.blackPieces.size() + "\n";
        returnStr += "Num white pieces in this.blackPieces: " + this.blackPieces.size() + "\n";

        returnStr += "En passant square: (" + this.enPassantSquare[0] + ", " + this.enPassantSquare[1] + ")";

        return returnStr;

    }

}