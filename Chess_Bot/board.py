import numpy as np

class board:
    def __init__(self):
        """Initialize the board and variables"""
        self.board = board
        self.white_to_play = True
        self.en_passant_square = None   # When a pawn moves forward 2 pieces, set en_passant_square to the square it moved to (row, col)
        self.check = False  # true when the player to play is in check
        self.white_castle_rights = (True, True) # (King side, Queen side)
        self.black_castle_rights = (True, True) # (King side, Queen side)
         
    def starting_board(self):
        """Creates 8x8 board representation
        Board is from the perspective of white, meaning 0th/1st row is black, 6th/7th row is white
        Empty is represented as X
        Piece is represented by 2 chars: Color (W/B) and Piece:
            P : Pawn
            R : Rook
            N : Knight
            B : Bishop
            Q : Queen 
            K : King
        """
        board = np.array((8, 8))
        board.fill("X")
        
        # PAWNS
        board[1] = ["BP" for i in range(8)]
        board[6] = ["WP" for i in range(8)]
        
        # ROOKS
        board[0][0], board[0][7] = "BR", "BR"
        board[7][0], board[7][7] = "WR", "WR"
        
        # KNIGHTS
        board[0][1], board[0][6] = "BN", "BN"
        board[7][1], board[7][6] = "WN", "WN"
        
        # BISHOPS
        board[0][2], board[0][5] = "BB", "BB"
        board[7][2], board[7][5] = "WB", "WB"
        
        # QUEENS 
        board[0][3], board[0][3] = "BQ", "BQ"
        board[7][3], board[7][3] = "WQ", "WQ"
        
        # KINGS
        board[0][4], board[0][4] = "BK", "BK"
        board[7][4], board[7][4] = "WK", "WK"

    def possible_moves(self, whites_turn = True, can_en_passant = False):
        """Returns a list of all possible moves in the board's position
        A move is a 2-tuple, containing the piece (e.g. "WP") and the square's index (e.g. (2, 4))
            e.g. move = ("WP", (2, 4))      # white's pawn to C4
        """
        pass
    
    def make_move(self, piece, row, column):
        """Moves piece to the (row, column) position
        Returns "CHECKMATE" if in check and no move is possible
        Returns "STALEMATE" if not in check and no move is possible
        Returns "" if game continues 
        """
        
        # If in check, check for mate
            # If true, return "CHECKMATE"
        # If in check, check for double check
        #   If true, you must move the king
        
        # Check if can castle to either or both sides, if you can, add it/them to list of moves 
            # You can castle iff:
                # self.castle_rights = True for the side you wish to castle to 
                # you are not in check 
                # you are not castling through check
        # For each piece 
            # Calculate all theoretically possible moves 
            # Eliminate moves that are not actually possible 
                # Absolute pin
                # Blocked: Piece occupied by your own square (or opponents in the case of a pawn)
                #   (not knight) note: if blocked in one square, all squares passed that are also blocked
                # Move puts you in check (king) or keeps you in check (if in check and the move keeps you in check)
                # Pawn: No opponent square on your diagonal or no en passant on your diagonal 
                
        # If no moves are possible, return "STALEMATE"
            
        
        
        
    