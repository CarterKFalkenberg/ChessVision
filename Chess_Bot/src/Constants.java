package Chess_Bot.src;

import java.util.HashMap;

public final class Constants {
    public static final int BLACK = -1;
    public static final int WHITE = 1;
    public static final int EMPTY = 0;
    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;

    public static HashMap<Integer, Integer> pieceToValue = initPieceToValue();
    public static HashMap<Integer, String> pieceIntToStr = initPieceIntToStr();
    public static HashMap<Integer, Character> columnIntToChar = initColumnIntToChar();


    private Constants() {
    // Prevent instantiation
    }

    private static HashMap<Integer, Integer> initPieceToValue(){
        HashMap<Integer, Integer> pieceToValue = new HashMap<Integer, Integer>();
        pieceToValue.put(PAWN, 1);
        pieceToValue.put(KNIGHT, 3);
        pieceToValue.put(BISHOP, 3);
        pieceToValue.put(ROOK, 5);
        pieceToValue.put(QUEEN, 9);
        pieceToValue.put(KING, 0);
        
        return pieceToValue;
    }

    private static HashMap<Integer, String> initPieceIntToStr(){
        HashMap<Integer, String> pieceIntToStr = new HashMap<Integer, String>();
        pieceIntToStr.put(1, "Pawn");
        pieceIntToStr.put(2, "Knight");
        pieceIntToStr.put(3, "Bishop");
        pieceIntToStr.put(4, "Rook");
        pieceIntToStr.put(5, "Queen");
        pieceIntToStr.put(6, "King");
        
        return pieceIntToStr;
    }

    private static HashMap<Integer, Character> initColumnIntToChar(){
        HashMap<Integer, Character> columnIntToChar = new HashMap<Integer, Character>();
        columnIntToChar.put(0, 'A');
        columnIntToChar.put(1, 'B');
        columnIntToChar.put(2, 'C');
        columnIntToChar.put(3, 'D');
        columnIntToChar.put(4, 'E');
        columnIntToChar.put(5, 'F');
        columnIntToChar.put(6, 'G');
        columnIntToChar.put(7, 'H');
        
        return columnIntToChar;
    }

 }
