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

 }
