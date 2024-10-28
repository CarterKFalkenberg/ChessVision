package Chess_Bot.src;

import java.io.IOException;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) throws IOException{
        Board board = new Board();
        board.turnColor = -1;
        ArrayList<Move> moves = board.getPossibleMoves();
        for (Move move : moves){
            System.out.println(move);
        }
    }
}
