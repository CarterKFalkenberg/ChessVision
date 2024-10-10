package Chess_Bot.src;

import java.util.ArrayList;

import Chess_Bot.src.Pieces.King;
import Chess_Bot.src.Pieces.Piece;

public class Evaluation {

    final static int PAWN_SHIELD_WEIGHT = 2;
    final static int ENEMY_DISTANCE_WEIGHT = 1;
    final static int CENTER_DISTANCE_WEIGHT = 1;

    public static double evaluation(Board board){
        /*
         * Returns the evaluation for white in the board's current position
         * Assumes that checkmate is already checked for (no pun intended)
         * Zero-sum, i.e. eval(black) = -1 * eval(white)
         */

        int pieceDifferential = pieceDifferential(board.whitePieces, board.blackPieces);
        double kingSafetyDifferential = kingSafetyDifferential(board);
        int spaceDifferential = spaceDifferential(board.boardColors, board.boardPiecesInt);

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

    public static double kingSafetyDifferential(Board board){

        Piece whiteKing = null;
        Piece blackKing = null;
        for (Piece piece : board.whitePieces){
            if (piece.getType() == Constants.KING){
                whiteKing = piece;
            }
        }
        for (Piece piece : board.blackPieces){
            if (piece.getType() == Constants.KING){
                blackKing = piece;
            }
        }
        int whiteKingDistanceScore = kingDistanceToCenterScore(whiteKing);
        int blackKingDistanceScore = kingDistanceToCenterScore(blackKing);
        int distanceDifference = whiteKingDistanceScore - blackKingDistanceScore;

        double whiteKingEnemiesScore = weightedSumDistanceToEnemies(whiteKing, board.blackPieces);
        double blackKingEnemiesScore = weightedSumDistanceToEnemies(blackKing, board.whitePieces);
        double enemiesDifference = whiteKingEnemiesScore - blackKingEnemiesScore;

        int whitePawnShieldScore = pawnShieldScore(whiteKing, board.boardColors, board.boardPiecesInt);
        int blackPawnShieldScore = pawnShieldScore(blackKing, board.boardColors, board.boardPiecesInt);
        int pawnShieldDifference = whitePawnShieldScore - blackPawnShieldScore;

        return CENTER_DISTANCE_WEIGHT * distanceDifference 
            + ENEMY_DISTANCE_WEIGHT * enemiesDifference 
            + PAWN_SHIELD_WEIGHT * pawnShieldDifference;
    }

    public static int kingDistanceToCenterScore(Piece king){
        // calculates distance from king to nearest of the 4 center squares 
        int row = king.getSquare()[0];
        int col = king.getSquare()[1];

        int dx = Math.min(Math.abs(3 - row), Math.abs(4 - row));
        int dy = Math.min(Math.abs(3 - col), Math.abs(4 - col));

        int distance = dx + dy;
        
        int score = Math.min(distance * 20, 100);  // score is out of 100

        return score;
    }

    public static double weightedSumDistanceToEnemies(Piece king, ArrayList<Piece> enemies){
        double distance_total = 0.0;

        for (Piece piece : enemies) {
            double weighted_distance = distance(king, piece);
            switch (piece.getType()) {
                // weights are based on the values of each piece. King is considered a little better than pawn in this case, since it can support pieces better
                case Constants.PAWN:
                    weighted_distance *= 0.9;
                    break;
                case Constants.KING:
                    weighted_distance *= 0.75;
                    break;
                case Constants.BISHOP:
                case Constants.KNIGHT:
                    weighted_distance *= 0.3;
                    break;
                case Constants.ROOK:
                    weighted_distance *= 0.18;
                    break;
                case Constants.QUEEN:
                    weighted_distance *= .1;
                    break;
                default:
                    break;
            }
            distance_total += weighted_distance;
        }

        return distance_total;
    }

    public static int pawnShieldScore(Piece king, int[][] boardColors, int[][] boardPiecesInt){
        int kingRow = king.getSquare()[0];
        int kingCol = king.getSquare()[1];

        // for when king is in corner, consider it to be in normal position for ease of checking
        if (kingCol == 0){
            kingCol = 1;
        } else if (kingCol == 7){
            kingCol = 6;
        }

        int forwards = -1 * king.pieceColor; // B forwards is +, W forwards is -

        boolean front = boardColors[kingRow+forwards][kingCol] == king.pieceColor && boardPiecesInt[kingRow+forwards][kingCol] == Constants.PAWN;
        boolean frontForwards = boardColors[kingRow+2*forwards][kingCol] == king.pieceColor && boardPiecesInt[kingRow+2*forwards][kingCol] == Constants.PAWN;
        boolean frontLeft = boardColors[kingRow+forwards][kingCol-1] == king.pieceColor && boardPiecesInt[kingRow+forwards][kingCol-1] == Constants.PAWN;
        boolean frontLeftForwards = boardColors[kingRow+2*forwards][kingCol-1] == king.pieceColor && boardPiecesInt[kingRow+2*forwards][kingCol-1] == Constants.PAWN;
        boolean frontRight = boardColors[kingRow+forwards][kingCol+1] == king.pieceColor && boardPiecesInt[kingRow+forwards][kingCol+1] == Constants.PAWN;
        boolean frontRightForwards = boardColors[kingRow+2*forwards][kingCol+1] == king.pieceColor && boardPiecesInt[kingRow+2*forwards][kingCol+1] == Constants.PAWN;
 
        // 3 pawns
        if (front && frontLeft && frontRight){
            return 100;
        } 
        
        // 2 pawns. Either just 2, or 2 with a 3rd that is only 1 forwards
        else if (front && frontLeft){
            if (frontRightForwards){
                return 90;
            } else {
                return 70;
            }
        }
        else if (front && frontRight){
            if (frontLeftForwards){
                return 90;
            } else {
                return 70;
            }
        }
        else if (frontLeft && frontRight){
            if (frontForwards){
                return 90;
            } else {
                return 70;
            }
        }

        // 1 pawn. Either just 1, or 1 with a 2nd that is 1 forward
        else if (front){
            if (frontRightForwards || frontLeftForwards){
                return 30;
            } else {
                return 10;
            }
        }
        else if (frontLeft){
            if (frontForwards){
                return 30;
            } else {
                return 10;
            }
        }
        else if (frontRight){
            if (frontForwards){
                return 30;
            } else {
                return 10;
            }
        }

        // 0 pawns
        return 0;
    }

    public static int spaceDifferential(int[][] boardColors, int[][] boardPiecesInt){
        int whiteSpace = 0;
        int blackSpace = 0;

        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                if (boardColors[row][col] == Constants.WHITE){
                    whiteSpace += calculateSpace(row, col, boardPiecesInt, Constants.WHITE);
                } else if (boardColors[row][col] == Constants.BLACK){
                    blackSpace += calculateSpace(row, col, boardPiecesInt, Constants.BLACK);
                }
            }
        }

        return whiteSpace - blackSpace;
    }

    public static int calculateSpace(int row, int col, int[][] boardPiecesInt, int color){
        switch (boardPiecesInt[row][col]) {
            case Constants.PAWN:
                return calculatePawnSpace(row, col, boardPiecesInt, color);
            case Constants.KNIGHT:
                return calculateKnightSpace(row, col, boardPiecesInt, color);
            case Constants.BISHOP:
                return calculateDiagonalSpace(row, col, boardPiecesInt, color);
            case Constants.ROOK:
                return calculateStraightSpace(row, col, boardPiecesInt, color);
            case Constants.QUEEN:
                return calculateStraightSpace(row, col, boardPiecesInt, color) + calculateDiagonalSpace(row, col, boardPiecesInt, color); 
            default:
                // ignore king
                break;
        }
        return 0;
    }

    public static int calculatePawnSpace(int row, int col, int[][] boardPiecesInt, int color){
        // pawn space is the diagonals in front of the pawn, only if those squares are in opponent territory
        if (color == Constants.WHITE){
            if (row > 4){
                return 0;
            } 
            if (col == 0 || col == 7){
                return 1;
            }
            return 2;
        } else {
            if (row < 3){
                return 0;
            }
            if (col == 0 || col == 7){
                return 1;
            }
            return 2;
        }
    }

    public static boolean inOpponentTerritory(int row, int pieceColor){
        return (pieceColor == Constants.WHITE && row < 4 && row >= 0) || (pieceColor == Constants.BLACK && row > 3 && row < 8);
    }

    public static int calculateStraightSpace(int row, int col, int[][] boardPiecesInt, int color){
        // starting from our square, check in all 4 directions, repeat until all of them hit either a piece or end of board
        int forwards = -1 * color;
        int forwardRow = row + forwards;
        int backwardRow = row - forwards;
        int rightCol = col + 1;
        int leftCol = col - 1;

        int straightSpace = 0;

        while(forwardRow >= 0 && forwardRow < 8){    
            // if in opponent territory:
            if (inOpponentTerritory(forwardRow, color)){
                straightSpace++;
            }  
            // once we hit a piece, stop checking
            if (boardPiecesInt[forwardRow][col] != Constants.EMPTY){
                break;
            }
            forwardRow += forwards;
        }

        while(backwardRow >= 0 && backwardRow < 8){    
            // if in opponent territory:
            if (inOpponentTerritory(backwardRow, color)){
                straightSpace++;
            }  else {
                break;
            }
            // once we hit a piece, stop checking
            if (boardPiecesInt[backwardRow][col] != Constants.EMPTY){
                break;
            }
            backwardRow -= forwards;
        }
        
        if (!inOpponentTerritory(row, color)){
            return straightSpace;
        }

        while(rightCol < 8){    
            straightSpace++;
            // once we hit a piece, stop checking
            if (boardPiecesInt[row][rightCol] != Constants.EMPTY){
                break;
            }
            
            rightCol++;
        }

        while(leftCol >= 0){    
            straightSpace++;
            // once we hit a piece, stop checking
            if (boardPiecesInt[row][leftCol] != Constants.EMPTY){
                break;
            }
            leftCol--;
        }

        return straightSpace;
    }

    public static int calculateDiagonalSpace(int row, int col, int[][] boardPiecesInt, int color){
        int forwards = -1 * color;
        int[] frontRightSquare = {row + forwards, col + 1};
        int[] frontLeftSquare = {row + forwards, col - 1};
        int[] backRightSquare = {row - forwards, col + 1};
        int[] backLeftSquare = {row - forwards, col - 1};

        int diagonalSpace = 0;

        while (frontRightSquare[0] >= 0 && frontRightSquare[0] < 8 && frontRightSquare[1] < 8){
            if (inOpponentTerritory(frontRightSquare[0], color)){
                diagonalSpace++;
            }
            if (boardPiecesInt[frontRightSquare[0]][frontRightSquare[1]] != Constants.EMPTY){
                break;
            }
            frontRightSquare[0] += forwards;
            frontRightSquare[1]++;
        }

        while (frontLeftSquare[0] >= 0 && frontLeftSquare[0] < 8 && frontLeftSquare[1] >= 0){
            if (inOpponentTerritory(frontLeftSquare[0], color)){
                diagonalSpace++;
            }
            if (boardPiecesInt[frontLeftSquare[0]][frontLeftSquare[1]] != Constants.EMPTY){
                break;
            }
            frontLeftSquare[0] += forwards;
            frontLeftSquare[1]--;
        }

        while (backRightSquare[0] >= 0 && backRightSquare[0] < 8 && backRightSquare[1] < 8){
            if (inOpponentTerritory(backRightSquare[0], color)){
                diagonalSpace++;
            } else {
                break;
            }
            if (boardPiecesInt[backRightSquare[0]][backRightSquare[1]] != Constants.EMPTY){
                break;
            }
            backRightSquare[0] -= forwards;
            backRightSquare[1]++;
        }

        while (backLeftSquare[0] >= 0 && backLeftSquare[0] < 8 && backLeftSquare[1] >= 0){
            if (inOpponentTerritory(backLeftSquare[0], color)){
                diagonalSpace++;
            } else {
                break;
            }
            if (boardPiecesInt[backLeftSquare[0]][backLeftSquare[1]] != Constants.EMPTY){
                break;
            }
            backLeftSquare[0] -= forwards;
            backLeftSquare[1]--;
        }
        
        return diagonalSpace;
    }

    public static int calculateKnightSpace(int row, int col, int[][] boardPiecesInt, int color){
        int forwards = -1 * color;

        int knightSpace = 0;

        // if knight going forwards twice is in your space (also automatically means the knight going forwards once is in your space)
        if (inOpponentTerritory(row + 2 * forwards, color)){
            // if on edge, only 2 possible moves
            if (col == 0 || col == 7){
                knightSpace += 2;
            } else {
                knightSpace += 4;
            }
        }

        // if knight going forwards once is in your space 
        else if (inOpponentTerritory(row + forwards, color)){
            // if on edge, only 1 possible move
            if (col == 0 || col == 7){
                knightSpace++;
            } else {
                knightSpace += 2;
            }
        }

        // if knight going backwards twice is in your space (also automatically means the knight going backwards once is in your space)
        if (inOpponentTerritory(row - 2 * forwards, color)){
            // if on edge, only 2 possible moves
            if (col == 0 || col == 7){
                knightSpace += 2;
            } else {
                knightSpace += 4;
            }
        }

        // if knight going backwards once is in your space
        else if (inOpponentTerritory(row - forwards, color)){
            // if on edge, only 1 possible move
            if (col == 0 || col == 7){
                knightSpace++;
            } else {
                knightSpace += 2;
            }
        }

        return knightSpace;
    }

    public static int distance(Piece piece1, Piece piece2){
        // calculates distance between two squares 
        return distance(piece1.getSquare()[0], piece1.getSquare()[1], piece2.getSquare()[0], piece2.getSquare()[1]);
    }

    public static int distance(int row1, int col1, int row2, int col2){
        // calculates distance between two squares 
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }

}
