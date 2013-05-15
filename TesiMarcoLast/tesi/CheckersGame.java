package tesi;


/**
 * This is the class that controls the Checkers game in the text based version
 * of Checkers. Its main task is to receive moves from the user and validate 
 * the moves.
 */
public class CheckersGame 
{

   private int userColor;
   private int computerColor;
   private FormattedInput keyboard = new FormattedInput();

   
   public static void main(String[] args) 
   {
      CheckersGame checkersGame = new CheckersGame();
      checkersGame.playGame();
   }
     
   
   private void playGame() 
   {
      System.out.println("\n\n***********************************");
      System.out.println("******* Welcome to Checkers *******");
      System.out.println("***********************************\n\n");
      Board board = new Board();
      //board.initialize(); 
      Rules.placeChecker(board, 2, Checker.BLACK, false);
      Rules.placeChecker(board, 6, Checker.WHITE, false);
      Rules.placeChecker(board, 7, Checker.WHITE, false);
      Rules.placeChecker(board, 14, Checker.WHITE, false);
      Rules.placeChecker(board, 20, Checker.BLACK, false);
      Rules.placeChecker(board, 22, Checker.BLACK, false);
      Rules.placeChecker(board, 24, Checker.WHITE, false);
      System.out.println("\n" + board);
      userColor = keyboard.readColor();
      computerColor = Rules.opponent(userColor);
      int turn = Checker.BLACK;
      while (true) 
      {
         if (turn == userColor)
            board = moveUser(board);
         else {
            //if (Rules.checkBoard(board))
            //   System.out.println("Inconsistent board."); 
            board.getHistory().reset();
            Board comBoard = Rules.minimaxAB(board, 6, computerColor,Rules.minusInfinityBoard(),Rules.plusInfinityBoard());                               
            Move move = comBoard.getHistory().first();
            board = Rules.executeMove(move, board);
            System.out.println("Computer move: " + move);
         }  
         System.out.println("\n" + board); 
         turn = Rules.opponent(turn);        
      }
         
      /*
      userColor = Checker.WHITE;
      board = Rules.executeMove(new MoveNormal(board.getChecker(new Coordinate(9)), new Coordinate(13)), board);
      System.out.println("\n" + board);
      board = moveUser(board);
      */
      
   }   

   
   /**
    * Returns a new board where the user has performed a move. The move is 
    * validated. If it is a normal move the method checks that there is no 
    * mandatory jumps to be made. If the move is a jump the method checks if  
    * a multiple jump is possible.
    */
   public Board moveUser(final Board board) 
   {
      boolean validMove = false;
      Board newBoard = null;
      while (! validMove) 
      {
         Coordinate from = new Coordinate(keyboard.readCoordinate("From:"));
         Coordinate to = new Coordinate(keyboard.readCoordinate("To:"));   
         Move move = validateUserMove(from, to, board);
         if (move == null) 
            System.out.println("Invalid move.");   
         else 
         {
            if (move.isJump()) 
            {
               newBoard = Rules.executeUserJump(move, board);
               Checker checker = newBoard.getChecker(move.getDestination());
               if (mandatoryJump(checker, newBoard))
               {
                  System.out.println("A multiple jump must be completed.");
                  newBoard = multipleJumps(checker, newBoard);
                  validMove = true;
               }    
            }
            else 
            {   // Normal move.
               if (mandatoryJump(move.getChecker(), board))
                  System.out.println("Invalid move. If you can jump, you must.");
               else
               {
                  newBoard = Rules.executeMove(move, board);
                  validMove = true;
               }             
            }
         }
      }
      return newBoard;
   }
        
   
   // Returns true if checker can make a jump.
   private boolean mandatoryJump(Checker checker, Board board) 
   {
      MoveList movelist = new MoveList();
      checker.findValidJumps(movelist, board);
      if (movelist.size() != 0)
         return true;
      else
         return false;
   }
   
   
   /**
    * Returns a new board after the user has executed multiple jumps. It 
    * continues to asks the user to make mandatory jumps. It checks that the 
    * entered move is a jump and that the multiple jumps is performed with
    * the same checker.
    */
   private Board multipleJumps(Checker checker, Board board)
   {
      while (mandatoryJump(checker, board)) 
      {
         Coordinate from = new Coordinate(keyboard.readCoordinate("From:"));
         Coordinate to = new Coordinate(keyboard.readCoordinate("To:"));
         Move move = validateUserMove(from, to, board);
         if (move == null)
            System.out.println("Invalid move.");
         else
         {
            if (!move.isJump())
               System.out.println("Invalid move. Must be a jump.");
            else 
            {
               if (!checker.getPosition().equals(from))
                  System.out.println("Invalid move. Must be a jump with the " + "same checker.");                          
               else 
               {
                  board = Rules.executeUserJump(move, board);
                  checker = board.getChecker(move.getDestination());
               }
            }
         }
      }
      return board;
   }
   
      
   // Returns a valid move entered by the user otherwise null.
   public Move validateUserMove(Coordinate from, Coordinate to, Board board) 
   {
      Move move = null;   
      Checker checker = board.getChecker(from);
      if (checker != null) 
      {
         if (userColor == Checker.WHITE) 
         {   
            if (checker.getColor() == Checker.WHITE)
            {  
               if (checker.getValue() == Checker.WHITE_VALUE_KING) 
               {
                  if (Math.abs(from.row() - to.row()) == 1) 
                  {
                     if (Rules.validKingMove(from, to, board))
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(to, from, board))
                        move = new MoveJump(checker, to);
               }           
               else 
               {  // Normal white checker. 
                  if (from.row() - to.row() == 1) 
                  { 
                     if (Rules.validWhiteMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validWhiteJump(from, to, board))
                        move = new MoveJump(checker, to);       
               }
            }
         }
         else 
         {   // User is black.
            if (checker.getColor() == Checker.BLACK)
            {  
               if (checker.getValue() == Checker.BLACK_VALUE_KING)
               {
                  if (Math.abs(from.row() - to.row()) == 1)
                  {
                     if (Rules.validKingMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(from, to, board)) 
                        move = new MoveJump(checker, to);
               }           
               else 
               {   // Normal black checker.
                  if (to.row() - from.row() == 1)
                  {
                     if (Rules.validBlackMove(from, to, board))
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validBlackJump(from, to, board)) 
                        move = new MoveJump(checker, to);
               }
            }
         }
      }
      return move;
   }
   
   // For test. 
   public void setUserColor(int color)
   {
      userColor = color;
   }  
}