package Chess_Bot;

class Board{

    int[][] board_colors;  
    int[][] board_pieces;
    Piece[] white_pieces;
    Piece[] black_pieces;
   
    Board(){
        this.board_colors = new int[8][8];
        this.board_pieces = new int[8][8];
        this.white_pieces = new Piece[16];
        this.black_pieces = new Piece[16];
    
    }

}