package Chess_Bot.src.Pieces;

import java.util.ArrayList;

import Chess_Bot.src.Constants;
import Chess_Bot.src.Move;

public class Piece {

    boolean isWhite;
    int[] square;
    int pieceColor;
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

    public ArrayList<Move> getPossibleMoves(int[][] boardColors, int[] enPassantSquare){
        return null;
    }

    public ArrayList<Move> getStraightPathMoves(int[][] boardColors){
        // starting from our square, check in all 4 directions, repeat until all of them hit either a piece or end of board
        boolean checkForwards = this.square[0] < 7;
        int forwardRow = this.square[0] + 1;

        boolean checkBackwards = this.square[0] > 0;
        int backwardRow = this.square[0] - 1;

        boolean checkRight = this.square[1] < 7;
        int rightRow = this.square[1] + 1;

        boolean checkLeft = this.square[1] > 0;
        int leftRow = this.square[1] - 1;
        
        
        ArrayList<Move> straightPathMoves = new ArrayList<Move>();

        while (checkForwards && checkBackwards && checkLeft && checkRight){
            if (checkForwards){
                if (boardColors[forwardRow][this.square[1]] == pieceColor){
                    checkForwards = false;
                } else {
                    straightPathMoves.add(new Move(this, this.square[0], this.square[1], forwardRow, this.square[1]));
                    if (boardColors[forwardRow][this.square[1]] != Constants.EMPTY){
                        checkForwards = false;
                    } 
                }
                forwardRow++;
            }
            if (checkBackwards){
                if (boardColors[backwardRow][this.square[1]] == pieceColor){
                    checkBackwards = false;
                } else {
                    straightPathMoves.add(new Move(this, this.square[0], this.square[1], backwardRow, this.square[1]));
                    if (boardColors[backwardRow][this.square[1]] != Constants.EMPTY){
                        checkBackwards = false;
                    } 
                }
                backwardRow--;
            }
            if (checkRight){
                if (boardColors[this.square[0]][rightRow] == pieceColor){
                    checkRight = false;
                } else {
                    straightPathMoves.add(new Move(this, this.square[0], this.square[1], this.square[0], rightRow));
                    if (boardColors[this.square[0]][rightRow] != Constants.EMPTY){
                        checkRight = false;
                    } 
                }
                rightRow++;
            }
            if (checkLeft){
                if (boardColors[this.square[0]][leftRow] == pieceColor){
                    checkLeft = false;
                } else {
                    straightPathMoves.add(new Move(this, this.square[0], this.square[1], this.square[0], leftRow));
                    if (boardColors[this.square[0]][leftRow] != Constants.EMPTY){
                        checkLeft = false;
                    } 
                }
                leftRow--;
            }
        }

        return straightPathMoves;
    }

    public ArrayList<Move> getDiagonalMoves(int[][] boardColors){
        // starting from our square, check in all 4 directions, repeat until all of them hit either a piece or end of board
        boolean checkFrontRight = this.square[0] < 7 && this.square[1] < 7;
        int[] frontRightSquare = {this.square[0] + 1, this.square[1] + 1};

        boolean checkFrontLeft = this.square[0] < 7 && this.square[1] > 0;
        int[] frontLeftSquare = {this.square[0] + 1, this.square[1] - 1};

        boolean checkBackRight = this.square[0] > 0 && this.square[1] < 7;
        int[] backRightSquare = {this.square[0] - 1, this.square[1] + 1};
        
        boolean checkBackLeft = this.square[0] > 0 && this.square[1] > 0;
        int[] backLeftSquare = {this.square[0] - 1, this.square[1] - 1};
        
        ArrayList<Move> diagonalPathMoves = new ArrayList<Move>();

        while (checkFrontRight && checkFrontLeft && checkBackRight && checkBackLeft){
            if (checkFrontRight){
                if (boardColors[frontRightSquare[0]][frontRightSquare[1]] == pieceColor){
                    checkFrontRight = false;
                } else {
                    diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], frontRightSquare[0], frontRightSquare[1]));
                    if (boardColors[frontRightSquare[0]][frontRightSquare[1]] != Constants.EMPTY){
                        checkFrontRight = false;
                    } 
                }
                frontRightSquare[0]++;
                frontRightSquare[1]++;
            }
            if (checkFrontLeft){
                if (boardColors[frontLeftSquare[0]][frontLeftSquare[0]] == pieceColor){
                    checkFrontLeft = false;
                } else {
                    diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], frontLeftSquare[0], frontLeftSquare[0]));
                    if (boardColors[frontLeftSquare[0]][frontLeftSquare[0]] != Constants.EMPTY){
                        checkFrontLeft = false;
                    } 
                }
                frontLeftSquare[0]++;
                frontLeftSquare[1]--;
            }
            if (checkBackRight){
                if (boardColors[backRightSquare[0]][backRightSquare[1]] == pieceColor){
                    checkBackRight = false;
                } else {
                    diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], backRightSquare[0], backRightSquare[1]));
                    if (boardColors[backRightSquare[0]][backRightSquare[1]] != Constants.EMPTY){
                        checkBackRight = false;
                    } 
                }
                backRightSquare[0]--;
                backRightSquare[1]++;
            }
            if (checkBackLeft){
                if (boardColors[backLeftSquare[0]][backLeftSquare[1]] == pieceColor){
                    checkBackLeft = false;
                } else {
                    diagonalPathMoves.add(new Move(this, this.square[0], this.square[1], backLeftSquare[0], backLeftSquare[1]));
                    if (boardColors[backLeftSquare[0]][backLeftSquare[1]] != Constants.EMPTY){
                        checkBackLeft = false;
                    } 
                }
                backLeftSquare[0]--;
                backLeftSquare[1]++;
            }
        }

        return diagonalPathMoves;
    }
  


    
}
