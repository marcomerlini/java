package tesi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.util.Delay;

public class MainFrame extends JFrame 
{
   // Levels.
   private static final int LEVEL2 = 5;
   private int thinkDepth = LEVEL2;    // Default.
   
   // States.
   private static final int FROM = 1;  
   private static final int TO = 2;
   private static final int FROM_MULTIPLE = 3;
   private static final int TO_MULTIPLE = 4;
   private static final int COMPUTER_THINKS = 5;
   private static final int NOT_STARTED = 6;
   private int state = NOT_STARTED; 
   
   // First move.
   private static final int USER_MOVES_FIRST = 1;  
   private int firstMove = USER_MOVES_FIRST;
   private Checker multipleJumpsChecker = null;
   private int userColor = Checker.WHITE;
   private int computerColor = Checker.BLACK;
   private Coordinate from;
   private Board board = new Board();
   public Board CmuCamboard = new Board();
   public int[] CmuCam = new int[32]; 
   private Coordinate[] mossa= new Coordinate[2];
   private Coordinate[] mossamult= new Coordinate[2];
   public float[][] cordinate =new float[2][32];	//dove risiedono tutte le coordinate delle varie celle
     
  
   private void outputText(String s)
   {
      System.out.println(s);
   }
   
   
   // ********** Functionality ***********
   
   public MainFrame() 
   {
      thinkDepth = LEVEL2;
      System.out.println("Please make the first move");
      state = FROM; 
      board.initialize();
      String s=board.toString();
      outputText(s);
      computerMoves();
   }


   @SuppressWarnings("deprecation")
public static void main(String args[]) 
   {
      MainFrame m = new MainFrame();
   }
   
   
   /**
    * Performs an action depending on the state of the game. It gets the action
    * from the CheckerButton class.
    */
   public void generale() 
   {
         if (state == COMPUTER_THINKS)
            outputText("You cannot move while the computer is thinking.");
         else 
            switch(state)
            {
               case FROM:
            	  mossa=controllo();
                  from = mossa[0];
                  state = TO;
                  break;
               case TO:
                  moveUser(from,mossa[1]);
                  break;
               case FROM_MULTIPLE:
            	  mossamult=controllo();
                  from = mossamult[0];
                  state = TO_MULTIPLE;
                  break;
               case TO_MULTIPLE:
                  multipleJumps(from,mossamult[1]);
                  break;
            }        
   }        
      
   //arriva il vettore dalla CmuCam4
   //controllo se è avvenuta o meno una mossa
   //se avviene chiama una funzione per vedere il cambiamento che è stato fatto
   private Coordinate[] controllo() 
   {
	   boolean control=true;
	   Coordinate[] daa =new Coordinate[2];
	   
	   while(control)
	   {
		 //arrivo vettore da CmuCam4
		   CmuCamboard.VettoreToBoard(CmuCam);
		   if(!(CmuCamboard.equals(board)))
	   		{
			   control=false;
		   		for(int i=0;i<32;i++)
		   		{
		   			//da
		   			if(CmuCamboard.getValue(i)>board.getValue(i))
		   			{
		   				daa[0]=CmuCamboard.getPosition(i);
		   			}
		   			//a
		   			if(CmuCamboard.getValue(i)<board.getValue(i))
		   			{
		   				daa[1]=CmuCamboard.getPosition(i);
		   			}
		   		}
	   		}
	   		else
	   		{
	   			CmuCamboard=null;
	   			Delay.msDelay(10000);
	   		}
	   	}
	return daa;
   }

// The computer thinks....
   public void computerMoves() 
   {
      MoveList validMoves = Rules.findAllValidMoves(board, computerColor);
      if (validMoves.size() == 0)
      {
    	  state = NOT_STARTED;
    	  System.out.println("You win");
    	  Delay.msDelay(1000);
          NXT.shutDown();
      }
      else 
      {
         board.getHistory().reset();
         Board comBoard = Rules.minimaxAB(board, thinkDepth, computerColor,Rules.minusInfinityBoard(),Rules.plusInfinityBoard());                                        
         Move move = comBoard.getHistory().first();
         board = Rules.executeMove(move, board); //rinnovo della board
         MoveIterator iterator = board.getHistory().getIterator();
         String moves = "";
         while (iterator.hasNext()) 
         {
            moves = moves + iterator.next();
            if (iterator.hasNext())
            	{
            		moves = moves + " , ";
                    
            	}
         }      
         Coordinate fromcoo = move.checker.position;
         int from = fromcoo.toInt();	//ricontrollare perchè così ne fa solo una di mossa
         Coordinate tocoo = move.destination;
         int to = tocoo.toInt();
         sposta(from, to);
         outputText("Computer moves: " + moves);
         String s=board.toString();
         outputText(s);
         state = FROM;
         validMoves = Rules.findAllValidMoves(board, userColor);
         if (validMoves.size() == 0) 
         {
        	state = NOT_STARTED;
            System.out.println("Sorry, the computer wins!");
            Delay.msDelay(1000);
            NXT.shutDown();
         }
         generale();
      }           
   }  
   
   
   /**
    * A move is validated. If it is a normal move the method checks that there 
    * is no mandatory jumps to be made. If the move is a jump the method 
    * checks if a multiple jump is possible.
    */
   public void moveUser(Coordinate from, Coordinate to)
   {
      Move move = validateUserMove(from, to);
      if (move == null)
      {
         System.out.println("Invalid move.");
         state = FROM;
         generale();
      }
      else 
      {
         if (move.isJump()) 
         {
            board = Rules.executeUserJump(move, board);
            multipleJumpsChecker = board.getChecker(move.getDestination());
            if (mandatoryJump(multipleJumpsChecker, board))
            {
               outputText("A multiple jump must be completed.");
               state = FROM_MULTIPLE;
               generale();
            }
            else
            {
               state = COMPUTER_THINKS;
               String s=board.toString();
               outputText(s);
               computerMoves();
            }       
         }
         else 
         {   // Normal move.
            if (Rules.existJump(board, userColor))
            {
               outputText("Invalid move. If you can jump, you must.");
               state = FROM;
               generale();
            }
            else
            {
               board = Rules.executeMove(move, board);
               outputText("You move: " + move);
               state = COMPUTER_THINKS;
               String s=board.toString();
               outputText(s);
               computerMoves();
            }             
         }
      }
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
    * Checks that the entered move is a jump and that the multiple jumps is 
    * performed with the same checker.
    */
   private void multipleJumps(Coordinate from, Coordinate to)
   {  
      Move move = validateUserMove(from, to);
      if (move == null) 
      {
         outputText("Invalid move.");
         state = FROM_MULTIPLE;
         generale();
         
      }
      else {
         if (!move.isJump()) 
         {
            outputText("Invalid move. Must be a jump.");
            state = FROM_MULTIPLE;
            generale();
         }
         else {
            if (!multipleJumpsChecker.getPosition().equals(from)) 
            {
               outputText("Invalid move. Must be a jump with the same checker.");
               System.out.println("Invalid move. Must be a jump with the same checker.");
               state = FROM_MULTIPLE;
               generale();
            }   
            else 
            {
               board = Rules.executeUserJump(move, board);
               outputText("You move: " + move);
               multipleJumpsChecker = board.getChecker(move.getDestination());
               if (mandatoryJump(multipleJumpsChecker, board)) 
               {
                  outputText("A multiple jump must be completed.");
                  state = FROM_MULTIPLE;
                  generale();
               }
               else
               {
                  state = COMPUTER_THINKS;
                  computerMoves();
               }   
            }    
         }
      }
   }

      
   // Returns a valid move entered by the user otherwise null.
   public Move validateUserMove(Coordinate from, Coordinate to) {
      Move move = null;   
      Checker checker = board.getChecker(from);
      if (checker != null) {
         if (userColor == Checker.WHITE) {   
            if (checker.getColor() == Checker.WHITE) {  
               if (checker.getValue() == Checker.WHITE_VALUE_KING) {
                  if (Math.abs(from.row() - to.row()) == 1) {
                     if (Rules.validKingMove(from, to, board))
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(from, to, board))
                        move = new MoveJump(checker, to);
               }           
               else {  // Normal white checker. 
                  if (from.row() - to.row() == 1) { 
                     if (Rules.validWhiteMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validWhiteJump(from, to, board))
                        move = new MoveJump(checker, to);       
               }
            }
         }
         else {   // User is black.
            if (checker.getColor() == Checker.BLACK) {  
               if (checker.getValue() == Checker.BLACK_VALUE_KING) {
                  if (Math.abs(from.row() - to.row()) == 1) {
                     if (Rules.validKingMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(from, to, board)) 
                        move = new MoveJump(checker, to);
               }           
               else {   // Normal black checker.
                  if (to.row() - from.row() == 1) {
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
   
	public void sposta(int from,int to)
	{
		float [] daa = new float[2];
		int controllo=from;
		for(int i=0;i<2;i++)
		{
			daa[i]=cordinate[i][controllo];
			controllo=to;
		}
		movimento(daa);
	}
	
   private void movimento(float[] daa) 
   {
	   Motor.A.forward();
	   //fare il movimento dei bracci
   }


// Transform a coordinate from the Board grid (1-32) to the GUI grid (0-63).
   private int findSquare(int c) {
      int row = new Coordinate(c).row();
      if (row % 2 == 0)
         return (c * 2) - 1;
      else
         return (c * 2) - 2; 
   }
   
   
   // Returns the board coordinate (1-32) from the square coordinates (0-7,0-7).
   // The square coordinate must be at an even row.
   private int findCoordinateEvenRow(int x, int y) {
      return ((int) Math.ceil(x/2.0)) + (y * 4);   
   }
   
   
   private int findCoordinateOddRow(int x, int y) {
      return ((int) Math.ceil(x/2.0)) + (y * 4) + 1;   
   }  
   
}

