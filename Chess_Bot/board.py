import numpy as np

class board:
    def __init__(self):
        self.board = np.zeros((8, 8), dtype=np.uint8)
        
    def possible_moves(self):
        """Returns a list of all possible moves in the board's position"""
        pass
    
    def make_move(self, piece, row, column):
        """Moves piece to the (row, column) position"""
        
    