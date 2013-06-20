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
   private static int userColor = Checker.WHITE;
   private static int computerColor = Checker.BLACK;
   private Coordinate from;
   private Board board = new Board();
   public Board CmuCamboard = new Board();
   public static int[] CmuCam = new int[32]; 
   private Coordinate[] mossa= new Coordinate[2];
   private Coordinate[] mossamult= new Coordinate[2];
   public static int[][] cordinate =new int[32][3];	//dove risiedono tutte le coordinate delle varie celle
   public static Camera cmucam =new Camera();
   public static String [] position =new String[32];
   public static int check =0;
  
   private void outputText(String s)
   {
      System.out.println(s);
   }
   
   // ********** Functionality ***********
   
   public void start() 
   {
      thinkDepth = LEVEL2;
      System.out.println("The computer make the first move");
      state = FROM; 
      board.initialize();
      String s=board.toString();
      outputText(s);
      computerMoves();
   }


   @SuppressWarnings("deprecation")
public static void main(String args[]) 
   {   
	   //spostemento motori in secondi
	   //Motore A				//Motore B				//Motore C 
	   cordinate[0][0]=7000;	cordinate[0][1]=1200;	cordinate[0][2]=-15000;
	   cordinate[1][0]=5500;	cordinate[1][1]=700;	cordinate[1][2]=-3300;
	   cordinate[2][0]=3000;	cordinate[2][1]=750;	cordinate[2][2]=11000;
	   cordinate[3][0]=1500;	cordinate[3][1]=1150;	cordinate[3][2]=21000;
	   cordinate[4][0]=8000;	cordinate[4][1]=1300;	cordinate[4][2]=-9000;
	   cordinate[5][0]=6000;	cordinate[5][1]=1200;	cordinate[5][2]=3300;
	   cordinate[6][0]=4000;	cordinate[6][1]=1300;	cordinate[6][2]=14000;
	   cordinate[7][0]=2500;	cordinate[7][1]=1700;	cordinate[7][2]=21000;
	   cordinate[8][0]=7000;	cordinate[8][1]=1900;	cordinate[8][2]=-10000;
	   cordinate[9][0]=6000;	cordinate[9][1]=1500;	cordinate[9][2]=2000;
	   cordinate[10][0]=5000;	cordinate[10][1]=1600;	cordinate[10][2]=7000;
	   
	   cordinate[11][0]=3500;	cordinate[11][1]=1800;	cordinate[11][2]=16000;
	   cordinate[12][0]=5000;	cordinate[12][1]=2000;	cordinate[12][2]=-5500;
	   cordinate[13][0]=7000;	cordinate[13][1]=2000;	cordinate[13][2]=2000;
	   cordinate[14][0]=5500;	cordinate[14][1]=2100;	cordinate[14][2]=10000;
	   cordinate[15][0]=4000;	cordinate[15][1]=2400;	cordinate[15][2]=16500;
	   cordinate[16][0]=10000;	cordinate[16][1]=2700;	cordinate[16][2]=-9000;
	   cordinate[17][0]=8000;	cordinate[17][1]=2400;	cordinate[17][2]=-2500;
	   cordinate[18][0]=7500;	cordinate[18][1]=2500;	cordinate[18][2]=6000;
	   cordinate[19][0]=6000;	cordinate[19][1]=2700;	cordinate[19][2]=12000;
	   
	   cordinate[20][0]=9500;	cordinate[20][1]=3000;	cordinate[20][2]=-5500;
	   cordinate[21][0]=8000;	cordinate[21][1]=2900;	cordinate[21][2]=1000;
	   cordinate[22][0]=5000;	cordinate[22][1]=3000;	cordinate[22][2]=9000;
	   cordinate[23][0]=5000;	cordinate[23][1]=3500;	cordinate[23][2]=14000;
	   cordinate[24][0]=12000;	cordinate[24][1]=3800;	cordinate[24][2]=-8000;
	   cordinate[25][0]=10500;	cordinate[25][1]=3500;	cordinate[25][2]=-2000;
	   cordinate[26][0]=9500;	cordinate[26][1]=3600;	cordinate[26][2]=4000;
	   cordinate[27][0]=9000;	cordinate[27][1]=3800;	cordinate[27][2]=9500;
	   cordinate[28][0]=13000;	cordinate[28][1]=4300;	cordinate[28][2]=-4500;
	   cordinate[29][0]=11500;	cordinate[29][1]=4200;	cordinate[29][2]=700;
	   
	   cordinate[30][0]=10500;	cordinate[30][1]=4200;	cordinate[30][2]=6000;
	   cordinate[31][0]=11000;	cordinate[31][1]=4700;	cordinate[31][2]=11000;
      MainFrame m = new MainFrame();
      m.start();
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
      
   
   public static void buildBoard()
   {
	   String[]histogram= new String[3];
	   //controllare se 0 o 1 l'inizio
	   for(int posi=1;posi<33;posi++)
	   {
		   //histogram(0=red,1=green,2=blue)
		   histogram=cmucam.start(position[posi]);
		   colore(histogram);
	   }
   }
   
   private static void colore(String[] histogram) 
   {
	   //se la casella è rossa
	   if((<histogram[0]<)&&(<histogram[1]<)&&(<histogram[2]<))
	   {
		   CmuCam[controllo]=userColor;
		   controllo++;
	   }
	   //se la casella è verde
	   if((<histogram[0]<)&&(<histogram[1]<)&&(<histogram[2]<))
	   {
		   CmuCam[controllo]=computerColor;
		   controllo++;
	   }
	   //se la casella è nera
	   if((<histogram[0]<)&&(<histogram[1]<)&&(<histogram[2]<))
	   {
		   CmuCam[controllo]=5;
		   controllo++;
	   }
	   
	   if(controllo==31)
	   {
		   controllo=0;
	   }
   }


//controllo se è avvenuta o meno una mossa
   //se avviene chiama una funzione per vedere il cambiamento che è stato fatto
   private Coordinate[] controllo() 
   {
	   boolean control=true;
	   Coordinate[] daa =new Coordinate[2];
	   
	   while(control)
	   {
		   //costruzione della CmuCam board
		   buildBoard();
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
		int da[]= new int[3];
		int a[]= new int[3];
		for(int i=0;i<2;i++)
		{
			if(i==0)
			{
				for(int k=0;k<3;k++)
				{
					da[k]=cordinate[from][k];
					if(da[2]<0)
					{
						check=1;
					}
				}
				movimento(da);
				check=0;
			}
			if(i==1)
			{
				for(int k=0;k<3;k++)
				{
					a[k]=cordinate[to][k];
					if(k==2)
					{
						
						if((da[2]<0) && (a[2]<0))
						{
							int ris=a[2]-da[2];
							if(ris<0)
							{
								a[2]=-ris;
								check=1;
							}
							if(ris>0)
							{
								a[2]=ris;
								check=0;
							}
							else
							{
								a[2]=0;
							}
						}
						
						if((da[2]<0) && (a[2]>0))
						{
							a[2]=a[2]-da[2];					
							check=0;
						}
						//da fare
						if((da[2]>0) && (a[2]<0))
						{
							a[2]=da[2]-a[2];
							check=1;

						}
			
						if((da[2]>0) && (a[2]>0))
						{
							int ris=a[2]-da[2];
							if(ris<0)
							{
								a[2]=-ris;
								check=0;
							}
							if(ris>0)
							{
								a[2]=ris;
								check=1;
							}
							else
							{
								a[2]=0;
							}
						}
					}		
				}
				movimento(a);
			}
		}
		
	}
	
   private void movimento(int[] daa) 
   {
	   if(check==0)
	   {
		   Motor.A.forward();
		   Delay.msDelay(daa[0]);
		   Motor.A.stop();
		   Motor.B.forward();
		   Delay.msDelay(daa[1]);
		   Motor.B.stop();
		   Motor.C.forward();
		   Delay.msDelay(daa[2]);
		   Motor.C.stop();
	   }
	   if(check==1)
	   {
		   Motor.A.forward();
		   Delay.msDelay(daa[0]);
		   Motor.A.stop();
		   Motor.B.forward();
		   Delay.msDelay(daa[1]);
		   Motor.B.stop();
		   Motor.C.backward();
		   Delay.msDelay(-daa[2]);
		   Motor.C.stop();
	   }
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

