# Chess Bot

Chess bot uses minimax with alpha beta pruning to depth ??, followed by a hueristic function

Heuristics used:
1. Material difference is just material white - material black, don't adjust that the following will all increment the above slightly  
    the amount that they move the above score may need to be learned 
    NOTE: You must take into account BOTH SIDES for all of the following, this is zero-sum!
2. King safety -> based on how safe the king is, + or - a certain amount 
    ideally, king is behind 3 pawns, all pawns on the same rank
    king is weaker in the center, when on a diagonal of a bishop or queen, or on the file of a rook or queen
    king is stronger with pieces near it 
3. Piece activity 
    piece controls more squares, especially those in the center or the opponent's territory (preferable towards king)
4. Pawn structure 
    isolated pawns are bad
    doubled pawns are bad 
5. Space
   differential between the number of squares you are attacking on your opponent's side of the board and the number of squares they are attacking on yours.
6. Bishop pair 
7. Passed pawn

Potential idea for speed improvement:
- At any point in the search tree with depth > d, if the game is +2 or more, just do that move 
    - maybe d = 5? Depends on speed of alg 
