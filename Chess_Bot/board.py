import numpy as np

class Board:
    def __init__(self):
        """Initialize the board and variables"""
        self.board = self.starting_board()
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
        
    def checkmated(self):
        """Returns true if current turn's player is in checkmate"""
        if not self.check: return False    
        

    def possible_moves(self):
        """
        Returns a list of all possible moves in the board's position (if there are any)
        Returns "CHECKMATE" if in check and no move is possible
        Returns "STALEMATE" if not in check and no move is possible
        
        A move is a 3-tuple, containing the piece (e.g. "WP"), the start square, and the end square. Squares are 2-tuples 
            e.g. move = ("WP", (2, 6), (2, 4))      # white's pawn from C2 to C4
        """
       
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
                #   -> note for non-knight pieces: if blocked in one square, all squares passed that are also blocked
                # Move puts you in check (king) or keeps you in check (if in check and the move keeps you in check)
                # Pawn: No opponent square on your diagonal or no en passant possible on your diagonal 
                
        # If no moves are possible, return "CHECKMATE" if in check, else "STALEMATE"
    
    def make_move(self, piece, start_square, end_square):
        """Moves piece to the (row, column) position
        ! Assumes move is valid. Should only be called from a move returned in possible_moves
        """
        
        # Set board[start_square] = 'X'
        # Set board[end_square] = piece
        # Set en_passant_square to square or None
        # Set check 
        # Set castle_rights for current color 
        # Flip self.white_to_play 
        
    def heuristic(self):
        """Returns the evaluation for the board's current position 
        Zero-sum: Eval for white = -(Eval for black)
        """
        
        # Maybe: 
        
        # 1. Material difference is just material white - material black, don't adjust that
        
        # the following will all increment the above slightly  
            # the amount that they move the above score may need to be learned 
            # NOTE: You must take into account BOTH SIDES for all of the following, this is zero-sum!
        # 2. King safety -> based on how safe the king is, + or - a certain amount 
            # ideally, king is behind 3 pawns, all pawns on the same rank
            # king is weaker in the center, when on a diagonal of a bishop or queen, or on the file of a rook or queen
            # king is stronger with pieces near it 
        # 3. Piece activity 
            # piece controls more squares, especially those in the center or the opponent's territory (preferable towards king)
        # 4. Pawn structure 
            # isolated pawns are bad
            # doubled pawns are bad 
        # 5. Space
            # differential between the number of squares you are attacking on your opponent's side of the board and the number of squares they are attacking on yours.
        # 6. Bishop pair 
        # 7. Passed pawn
    
            
        
        
        
    