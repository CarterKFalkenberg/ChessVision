package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

public class Piece {

    boolean isWhite;
    int[] square;
    public int pieceColor;
    public Piece(boolean isWhite, int[] square) {
        this.isWhite = isWhite;
        this.pieceColor = isWhite ? Constants.WHITE : Constants.BLACK;
        this.square = square;
    }

    public int getType(){
        return Constants.EMPTY;
    }
    public boolean isWhitePiece(){
        return this.isWhite;
    }

    public int[] getSquare(){
        return this.square;
    }

    public void setSquare(int row, int col){
        this.square[0] = row;
        this.square[1] = col;
    }

    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        return null;
    }

    public ArrayList<Move> getStraightPathMoves(int[][] boardColors){
        // starting from our square, check in all 4 directions, repeat until all of them hit either a piece or end of board
        int forwardRow = this.square[0] + 1;
        int backwardRow = this.square[0] - 1;
        int rightCol = this.square[1] + 1;
        int leftCol = this.square[1] - 1;
        
        ArrayList<Move> straightPathMoves = new ArrayList<Move>();

        while(forwardRow < 8 && boardColors[forwardRow][this.square[1]] != this.pieceColor){    
            straightPathMoves.add(new Move(this, this.square[0], this.square[1], forwardRow, this.square[1]));
            // forwardRow is our opponent's color -> stop checking
            if (boardColors[forwardRow][this.square[1]] != Constants.EMPTY){
                break; 
            } 
            // forwardRow is empty -> keep checking diagonal
            forwardRow++;
        }
        while(backwardRow >= 0 && boardColors[backwardRow][this.square[1]] != this.pieceColor){    
            straightPathMoves.add(new Move(this, this.square[0], this.square[1], backwardRow, this.square[1]));
            // backwardRow is our opponent's color -> stop checking
            if (boardColors[backwardRow][this.square[1]] != Constants.EMPTY){
                break; 
            } 
            // backwardRow is empty -> keep checking diagonal
            backwardRow--;
        }
        while(rightCol < 8 && boardColors[this.square[0]][rightCol] != this.pieceColor){    
            straightPathMoves.add(new Move(this, this.square[0], this.square[1], this.square[0], rightCol));
            // rightCol is our opponent's color -> stop checking
            if (boardColors[this.square[0]][rightCol] != Constants.EMPTY){
                break; 
            } 
            // rightCol is empty -> keep checking diagonal
            rightCol++;
        }
        while(leftCol >= 0 && boardColors[this.square[0]][leftCol] != this.pieceColor){    
            straightPathMoves.add(new Move(this, this.square[0], this.square[1], this.square[0], leftCol));
            // leftCol is our opponent's color -> stop checking
            if (boardColors[this.square[0]][leftCol] != Constants.EMPTY){
                break; 
            } 
            // leftCol is empty -> keep checking diagonal
            leftCol--;
        }

        return straightPathMoves;
    }

    public ArrayList<Move> getDiagonalMoves(int[][] boardColors){
        // starting from our square, check in all 4 directions, repeat until all of them hit either a piece or end of board
        int[] frontRightSquare = {this.square[0] + 1, this.square[1] + 1};
        int[] frontLeftSquare = {this.square[0] + 1, this.square[1] - 1};
        int[] backRightSquare = {this.square[0] - 1, this.square[1] + 1};
        int[] backLeftSquare = {this.square[0] - 1, this.square[1] - 1};
        
        ArrayList<Move> diagonalPathMoves = new ArrayList<Move>();

        while(frontRightSquare[0] < 8 
                && frontRightSquare[1] < 8 
                && boardColors[frontRightSquare[0]][frontRightSquare[1]] != this.pieceColor){    
            diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], frontRightSquare[0], frontRightSquare[1]));
            // frontRightSquare is our opponent's color -> stop checking
            if (boardColors[frontRightSquare[0]][frontRightSquare[1]] != Constants.EMPTY){
                break; 
            } 
            // frontRightSquare is empty -> keep checking diagonal
            frontRightSquare[0]++;
            frontRightSquare[1]++;
        }
        while(frontLeftSquare[0] < 8 
                && frontLeftSquare[1] >= 0 
                && boardColors[frontLeftSquare[0]][frontLeftSquare[1]] != this.pieceColor){    
            diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], frontLeftSquare[0], frontLeftSquare[1]));
            // frontLeftSquare is our opponent's color -> stop checking
            if (boardColors[frontLeftSquare[0]][frontLeftSquare[1]] != Constants.EMPTY){
                break; 
            } 
            // frontLeftSquare is empty -> keep checking diagonal
            frontLeftSquare[0]++;
            frontLeftSquare[1]--;
        }
        while(backRightSquare[0] >= 0
                && backRightSquare[1] < 8 
                && boardColors[backRightSquare[0]][backRightSquare[1]] != this.pieceColor){    
            diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], backRightSquare[0], backRightSquare[1]));
            // backRightSquare is our opponent's color -> stop checking
            if (boardColors[backRightSquare[0]][backRightSquare[1]] != Constants.EMPTY){
                break; 
            } 
            // frontRightSquare is empty -> keep checking diagonal
            backRightSquare[0]--;
            backRightSquare[1]++;
        }
        while(backLeftSquare[0] >= 0
                && backLeftSquare[1] >= 0 
                && boardColors[backLeftSquare[0]][backLeftSquare[1]] != this.pieceColor){    
            diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], backLeftSquare[0], backLeftSquare[1]));
            // backLeftSquare is our opponent's color -> stop checking
            if (boardColors[backLeftSquare[0]][backLeftSquare[1]] != Constants.EMPTY){
                break; 
            } 
            // backLeftSquare is empty -> keep checking diagonal
            backLeftSquare[0]--;
            backLeftSquare[1]--;
        }

        return diagonalPathMoves;
    }

}
