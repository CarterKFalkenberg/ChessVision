# Chess Bot

Chess bot uses minimax with alpha beta pruning to depth ??, followed by a hueristic function

Current board representation is likely very inefficient, but goal here is to get an algorithm that works and optimize later if I really need (the main focus of the project is not the bot, but rather the computer vision)

Heuristics used:
- ??

Potential idea for speed improvement:
- At any point in the search tree with depth > d, if the game is +2 or more, just do that move 
    - maybe d = 5? Depends on speed of alg 