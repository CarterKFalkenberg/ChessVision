# ChessVision
Website where you can choose an image of a chess board and AI will tell you the best move in that position. 

Computer vision required to detect pieces as well as to detect which squares the pieces are on

Once detected, a GUI representation of the predicted position will be needed so the user can confirm the chess position and adjust any mistakes 

Finally, a chess-bot will need to pick the best move (likely will utilize mini-max ab pruning + heuristic function)

Currently will only use images of my own chess board, but may expand in the future for general use and creation of data flywheel

Data labeling options:
  - Roboflow -> locally run predictions to get prelabeled data, upload labeled data and adjust labeling
  - https://github.com/HumanSignal/label-studio -> all in one solution?
  - https://github.com/stephanecharette/DarkMark?tab=readme-ov-file -> all in one solution?
    
 Pre-trained model options:
  - https://huggingface.co/docs/transformers/model_doc/resnet -> ResNET
  - https://huggingface.co/docs/transformers/model_doc/yolos -> Yolos
