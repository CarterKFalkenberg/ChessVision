import numpy as np

class board:
    def __init__(self):
        """Creates 8x8 board representation
        Empty is represented as X
        Piece is represented by 2 chars: Color (W/B) and Piece:
            P : Pawn
            R : Rook
            N : Knight
            B : Bishop
            Q : Queen 
            K : King
        """
        board = np.zeros((8, 8))
        
        # PAWNS
        board[8:15] = ["BP" for i in range(8)]
        board[32:39] = ["WP" for i in range(8)]
        
        # ROOKS
        
        # KNIGHTS
        
        # BISHOPS
        board[2], board[5] = "BB", "BB"
        board[42], board[45] = "BB", "BB"
        
        # QUEENS 
        
        # KINGS
         
    def possible_moves(self):
        """Returns a list of all possible moves in the board's position
        A move is a 2-tuple, containing the piece (e.g. "WP") and the square's index (e.g. (2, 4))
            e.g. move = ("WP", (2, 4))      # white's pawn to C4
        """
        pass
    
    def make_move(self, piece, row, column):
        """Moves piece to the (row, column) position"""
        
    