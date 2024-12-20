package Chess_Bot.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    int[] enPassantSquare;  // the square that the opposing color's pawn just advanced 2 pieces to, else [-1, -1]
    int turnColor = Constants.WHITE;
    boolean check = false;  // whether the turnColor player is in check (if white's turn, true iff white is in check)
    boolean whiteShortCastleRights = true;
    boolean whiteLongCastleRights = true; 
    boolean blackShortCastleRights = true;
    boolean blackLongCastleRights = true;
    
   
    Board() throws IOException{
        this.boardColors = new int[8][8];
        this.boardPiecesInt = new int[8][8];
        
        this.boardPiecesObject = new ArrayList<ArrayList<Piece>>();
        for (int i = 0; i < 8; i++){
            ArrayList<Piece> dummy_row = new ArrayList<Piece>(Collections.nCopies(8, null));
            boardPiecesObject.add(dummy_row);
        }

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
                        piece = new King(row == 7, square, true, true);
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
                if (piece == null || piece.pieceColor != this.turnColor){
                    continue;
                }

                // If piece color is the same as color whose turn it is
                possibleMoves.addAll(piece.getPossibleMoves(this.boardColors, this.enPassantSquare));
            }
        }

        // remove cases where you move a pinned piece or move into a check
        removeIllegalMoves(possibleMoves);

        return possibleMoves;
    }

    void removeIllegalMoves(ArrayList<Move> moves) {
        // a theoretically possible move is illegal iff it results in the player who made that move ending up in check
            // EXCEPT for castling, need to check that! 
        
 
        List<Move> toRemove = new ArrayList<Move>();        
        for (Move move : moves) {
            if (inCheck(move)){
                toRemove.add(move);
                continue;
            }

            // if the move is castling, do additional checks
            // remove if you are in check or if the king would be in check by moving 1 square in the castling direction 
            if (move instanceof Castle){
                if (this.inCheck()){
                    toRemove.add(move);
                    continue;
                } 

                int[] square = move.piece.getSquare();
                Castle castle = (Castle) move;
                int direction = castle.shortCastle ? 1 : -1; 
                Move dummyMove = new Move(move.piece, square[0], square[1], square[0], square[1] + direction);
                if (inCheck(dummyMove)){
                    toRemove.add(move);
                }
            }
        }
        moves.removeAll(toRemove);
    }

    void makeMove(Move move) {

        // TODO: implement castling and en passant 

        if (move.piece.getType() == Constants.ROOK){
            System.out.println("rook");
        }

        // start square becomes empty
        this.boardColors[move.start_row][move.start_column] = Constants.EMPTY; 
        this.boardPiecesInt[move.start_row][move.start_column] = Constants.EMPTY;
        this.boardPiecesObject.get(move.start_row).set(move.start_column, null);

        // if piece on end square, it is removed. 
        Piece captured = this.boardPiecesObject.get(move.end_row).get(move.end_column);
        switch (this.boardColors[move.end_row][move.end_column]) {
            case Constants.WHITE:
                this.whitePieces.remove(captured);
                break;
            case Constants.BLACK:
                this.blackPieces.remove(captured);
                break;
            default:
                break;
        }

        
        // end square becomes filled with move.piece
        this.boardColors[move.end_row][move.end_column] = move.piece.pieceColor; 

        // if promotion, piece becomes a queen
        // otherwise, simply move the move.piece to the new position
        if (move.promotion){
            // TODO: add knight promotion if you want 
            Queen promoted = new Queen(move.piece.isWhitePiece(), new int[]{move.end_row, move.end_column});
            if (promoted.isWhitePiece()){
                this.whitePieces.remove(move.piece);
                this.whitePieces.add(promoted);
            } else {
                this.blackPieces.remove(move.piece);
                this.blackPieces.add(promoted);
            }
            this.boardPiecesInt[move.end_row][move.end_column] = promoted.getType();
            this.boardPiecesObject.get(move.end_row).set(move.end_column, promoted);
            // castle rights are not affected via promotion
        } else {
            move.piece.setSquare(move.end_row, move.end_column);
            this.boardPiecesInt[move.end_row][move.end_column] = move.piece.getType();
            this.boardPiecesObject.get(move.end_row).set(move.end_column, move.piece);
            updateCastleRights(move.piece, captured);
        }

        // set en passant square (default [-1, -1], unless pawn just moved 2 squares)
        this.enPassantSquare = move.enPassantSquare;

        // Flip turn color
        this.turnColor *= -1; // Black = -1, White = 1

        // update check status
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
        return this.inCheck(this.boardPiecesInt, this.boardColors, this.turnColor);
    }

    boolean inCheckCastle(Castle castle) {
        int[][] boardPiecesIntCopy = this.deepCopy(this.boardPiecesInt);
        int[][] boardColorsCopy = this.deepCopy(this.boardColors);
        if (castle.piece.isWhitePiece()){
            if (castle.shortCastle){
                boardPiecesIntCopy[7][4] = Constants.EMPTY;
                boardColorsCopy[7][4] = Constants.EMPTY;
                boardPiecesIntCopy[7][5] = Constants.ROOK;
                boardColorsCopy[7][5] = Constants.WHITE;
                boardPiecesIntCopy[7][6] = Constants.KING;
                boardColorsCopy[7][6] = Constants.WHITE;
                boardPiecesIntCopy[7][7] = Constants.EMPTY;
                boardColorsCopy[7][7] = Constants.EMPTY;
            } else {
                boardPiecesIntCopy[7][0] = Constants.EMPTY;
                boardColorsCopy[7][0] = Constants.EMPTY;
                boardPiecesIntCopy[7][2] = Constants.KING;
                boardColorsCopy[7][2] = Constants.WHITE;
                boardPiecesIntCopy[7][3] = Constants.ROOK   ;
                boardColorsCopy[7][3] = Constants.WHITE;
                boardPiecesIntCopy[7][4] = Constants.EMPTY;
                boardColorsCopy[7][4] = Constants.EMPTY;
            }
        } else {
            if (castle.shortCastle){
                boardPiecesIntCopy[0][4] = Constants.EMPTY;
                boardColorsCopy[0][4] = Constants.EMPTY;
                boardPiecesIntCopy[0][5] = Constants.ROOK;
                boardColorsCopy[0][5] = Constants.BLACK;
                boardPiecesIntCopy[0][6] = Constants.KING;
                boardColorsCopy[0][6] = Constants.BLACK;
                boardPiecesIntCopy[0][7] = Constants.EMPTY;
                boardColorsCopy[0][7] = Constants.EMPTY;
            } else {
                boardPiecesIntCopy[0][0] = Constants.EMPTY;
                boardColorsCopy[0][0] = Constants.EMPTY;
                boardPiecesIntCopy[0][2] = Constants.KING;
                boardColorsCopy[0][2] = Constants.BLACK;
                boardPiecesIntCopy[0][3] = Constants.ROOK;
                boardColorsCopy[0][3] = Constants.BLACK;
                boardPiecesIntCopy[0][4] = Constants.EMPTY;
                boardColorsCopy[0][4] = Constants.EMPTY;
            }
        }
        return this.inCheck(boardPiecesIntCopy, boardColorsCopy, castle.piece.pieceColor);
    }

    int[][] deepCopy(int[][] original){
        int[][] copy = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    boolean[][] deepCopy(boolean[][] original){
        boolean[][] copy = new boolean[original.length][];

        for (int i = 0; i < original.length; i++) {
            copy[i] = new boolean[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    boolean inCheck(Move move) {
        /*
         * Check if current turn's player would be in check if they made a certain move
         */

        if (move instanceof Castle){
            return inCheckCastle((Castle) move);
        }
        
        int[][] boardPiecesIntCopy = this.deepCopy(this.boardPiecesInt);
        int[][] boardColorsCopy = this.deepCopy(this.boardColors);
        boardPiecesIntCopy[move.start_row][move.start_column] = Constants.EMPTY; 
        boardColorsCopy[move.start_row][move.start_column] = Constants.EMPTY; 
        if (move.promotion){
            // TODO: Does not currently support promotion to a knight
            boardPiecesIntCopy[move.end_row][move.end_column] = Constants.QUEEN;
        } else {
            boardPiecesIntCopy[move.end_row][move.end_column] = move.piece.getType(); 
        }
        boardColorsCopy[move.end_row][move.end_column] = move.piece.pieceColor; 
        if (move instanceof EnPessant){
            EnPessant enPessant = (EnPessant) move;
            int[] takenSquare = enPessant.pawnTaken.getSquare();
            boardPiecesIntCopy[takenSquare[0]][takenSquare[1]] = Constants.EMPTY;
            boardColorsCopy[takenSquare[0]][takenSquare[1]] = Constants.EMPTY;
        }
        return this.inCheck(boardPiecesIntCopy, boardColorsCopy, move.piece.pieceColor);
    }

    boolean inCheck(int[][] boardPiecesInt, int[][] boardColors, int color) {
        /*
         * Check if color is in check
        */
        
        // find the king's square (of the given color)
        int kingRow = -1;
        int kingCol = -1;
        A:for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardPiecesInt[row][col] == Constants.KING && boardColors[row][col] == color){
                    kingRow = row;
                    kingCol = col;
                    break A;
                }
            }
        }
        if (kingRow == -1 || kingCol == -1) {
            throw new IndexOutOfBoundsException("King is not on board");
        }

        // THE ONLY WAYS YOU CAN BE IN CHECK ARE FROM A KNIGHT, A STRAIGHT PATH PIECE, OR A DIAGONAL PATH PIECE

        // check if in check by a knight
        if (inCheckByKnight(boardPiecesInt, boardColors, color, kingRow, kingCol)){
            return true;
        }

        // check if in check by a straight path piece (Queen/Rook)
        if (inCheckFromStraightPath(boardPiecesInt, boardColors, color, kingRow, kingCol)){
            return true;
        }

        // check if in check by a diagonal path piece (Queen/Bishop/Pawn)
        if (inCheckFromDiagonalPath(boardPiecesInt, boardColors, color, kingRow, kingCol)){
            return true;
        }

        // if not in check from knight, straight path, or diagonal path piece, then you are not in check
        return false;
    }

    boolean inCheckByKnight(int[][] boardPiecesInt, int[][] boardColors, int color, int kingRow, int kingCol){
        // color: The color of the KING potentially in check
        int row, col;
    
        // forward right
        row = kingRow + 2;
        col = kingCol + 1;
        if (row < 8 && col < 8 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // forward left
        row = kingRow + 2;
        col = kingCol - 1;
        if (row < 8 && col >= 0 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // right forward 
        row = kingRow + 1;
        col = kingCol + 2;
        if (row < 8 && col < 8 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // left forward 
        row = kingRow + 1;
        col = kingCol - 2;
        if (row < 8 && col >= 0 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // backward right
        row = kingRow - 2;
        col = kingCol + 1;
        if (row >= 0 && col < 8 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // backward left 
        row = kingRow - 2;
        col = kingCol - 1;
        if (row >= 0 && col >= 0 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }

        // right backward
        row = kingRow - 1;
        col = kingCol + 2;
        if (row >= 0 && col < 8 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        } 

        // left backward
        row = kingRow - 1;
        col = kingCol - 2;
        if (row >= 0 && col >= 0 && boardPiecesInt[row][col] == Constants.KNIGHT && boardColors[row][col] != color){
            return true;
        }  
        return false;
    }

    boolean inCheckFromStraightPath(int[][] boardPiecesInt, int[][] boardColors, int color, int kingRow, int kingCol){

        // forwards
        int row = kingRow + 1; 
        A: while (row < 8) {
            if (boardColors[row][kingCol] == color){
                break A;
            } else if (boardColors[row][kingCol] != Constants.EMPTY){
                if (boardPiecesInt[row][kingCol] == Constants.QUEEN || boardPiecesInt[row][kingCol] == Constants.ROOK){
                    return true;
                }
                return false;
            } 
            row += 1;
        }

        // backwards
        row = kingRow - 1; 
        A: while (row >= 0) {
            if (boardColors[row][kingCol] == color){
                break A;
            } else if (boardColors[row][kingCol] != Constants.EMPTY){
                if (boardPiecesInt[row][kingCol] == Constants.QUEEN || boardPiecesInt[row][kingCol] == Constants.ROOK){
                    return true;
                }
                return false;
            } 
            row -= 1;
        }

        // right
        int col = kingCol + 1; 
        A: while (col < 8) {
            if (boardColors[kingRow][col] == color){
                break A;
            } else if (boardColors[kingRow][col] != Constants.EMPTY){
                if (boardPiecesInt[kingRow][col] == Constants.QUEEN || boardPiecesInt[kingRow][col] == Constants.ROOK){
                    return true;
                }
                return false;
            } 
            col += 1;
        }

        // left
        col = kingCol - 1; 
        A: while (col >= 0) {
            if (boardColors[kingRow][col] == color){
                break A;
            } else if (boardColors[kingRow][col] != Constants.EMPTY){
                if (boardPiecesInt[kingRow][col] == Constants.QUEEN || boardPiecesInt[kingRow][col] == Constants.ROOK){
                    return true;
                }
                return false;
            } 
            col -= 1;
        }

        return false;
    }

    boolean inCheckFromDiagonalPath(int[][] boardPiecesInt, int[][] boardColors, int color, int kingRow, int kingCol){
        
        // forwards right
        int row = kingRow + 1; 
        int col = kingCol + 1;
        boolean firstDiag = true;
        A: while (row < 8 && col < 8) {
            if (boardColors[row][col] == color){
                break A;
            } else if (boardColors[row][col] != Constants.EMPTY){
                if (boardPiecesInt[row][col] == Constants.QUEEN || boardPiecesInt[row][col] == Constants.BISHOP){
                    return true;
                } else if (firstDiag && boardPiecesInt[row][col] == Constants.PAWN && color == Constants.BLACK){
                    // only case with a valid pawn check here is if the king is black (b/c we are checking towards white's direction)
                        // i.e. if we are a white king, we are behind the black pawn so we are not in check. Rare but possible in endgames
                    return true;
                }
                break A;
            } 
            row += 1;
            col += 1;
            firstDiag = false;
        }

        // forwards left
        row = kingRow + 1; 
        col = kingCol - 1;
        firstDiag = true;
        A: while (row < 8 && col >= 0) {
            if (boardColors[row][col] == color){
                break A;
            } else if (boardColors[row][col] != Constants.EMPTY){
                if (boardPiecesInt[row][col] == Constants.QUEEN || boardPiecesInt[row][col] == Constants.BISHOP){
                    return true;
                } else if (firstDiag && boardPiecesInt[row][col] == Constants.PAWN && color == Constants.BLACK){
                    // only case with a valid pawn check here is if the king is black (b/c we are checking towards white's direction)
                        // i.e. if we are a white king, we are behind the black pawn so we are not in check. Rare but possible in endgames
                    return true;
                }
                break A;
            } 
            row += 1;
            col -= 1;
            firstDiag = false;
        }

        // backwards right
        row = kingRow - 1; 
        col = kingCol + 1;
        firstDiag = true;
        A: while (row >= 0 && col < 8) {
            if (boardColors[row][col] == color){
                break A;
            } else if (boardColors[row][col] != Constants.EMPTY){
                if (boardPiecesInt[row][col] == Constants.QUEEN || boardPiecesInt[row][col] == Constants.BISHOP){
                    return true;
                } else if (firstDiag && boardPiecesInt[row][col] == Constants.PAWN && color == Constants.WHITE){
                    // only case with a valid pawn check here is if the king is white (b/c we are checking towards black's direction)
                        // i.e. if we are a black king, we are behind the black pawn so we are not in check. Rare but possible in endgames
                    return true;
                }
                break A;
            } 
            row -= 1;
            col += 1;
            firstDiag = false;
        }

        // backwards left
        row = kingRow - 1; 
        col = kingCol - 1;
        firstDiag = true;
        A: while (row >= 0 && col >= 0) {
            if (boardColors[row][col] == color){
                break A;
            } else if (boardColors[row][col] != Constants.EMPTY){
                if (boardPiecesInt[row][col] == Constants.QUEEN || boardPiecesInt[row][col] == Constants.BISHOP){
                    return true;
                } else if (firstDiag && boardPiecesInt[row][col] == Constants.PAWN && color == Constants.WHITE){
                    // only case with a valid pawn check here is if the king is white (b/c we are checking towards black's direction)
                        // i.e. if we are a black king, we are behind the black pawn so we are not in check. Rare but possible in endgames
                    return true;
                }
                break A;
            } 
            row -= 1;
            col -= 1;
            firstDiag = false;
        }
        
        return false;
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
        
        String returnStr = "----------------------------------------------------------------\n";
        returnStr += "-| A B C D E F G H\n";
        returnStr += "------------------\n";

        // uppercase: white, lowercase: black
        for (int i = 0; i < 8; i++) {
            returnStr += (8-i) + "| ";
            for (int j = 0; j < 8; j++){
                int pieceInt = this.boardPiecesInt[i][j];
                String pieceStr = "";
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
                if (this.boardColors[i][j] == Constants.BLACK){
                    pieceStr = pieceStr.toLowerCase();
                }
                returnStr += pieceStr + " ";
            }
            returnStr += "\n";
        }
        /*
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
        */
        if (this.turnColor == Constants.WHITE){
            returnStr += "White to play\n";
        } else {
            returnStr += "Black to play\n";
        }
        
        returnStr += "Num black pieces in this.blackPieces: " + this.blackPieces.size() + "\n";
        returnStr += "Num white pieces in this.blackPieces: " + this.blackPieces.size() + "\n";

        if (this.enPassantSquare[0] != -1){
            returnStr += "En passant square: (" + this.enPassantSquare[0] + ", " + this.enPassantSquare[1] + ")";
        }
        returnStr += "----------------------------------------------------------------\n";

        return returnStr;

    }

}