package tesi;

public class Board 
{
   public Checker[] board = new Checker[32];
   public MoveList history = new MoveList();  // History of moves.
   protected Board next = null;     
 
     
   public Board copy() 
   {
      Board newBoard = new Board();
      Coordinate temp = null;
      newBoard.setHistory(history.copy());
      for (int i = 1; i < 33; i++) 
      {
         temp = new Coordinate(i);
         if (getChecker(temp) != null)
            newBoard.setChecker(getChecker(temp).copy(), temp);
      }
      return newBoard;
      //for (int y = 0; y < 8; y++) {
         //for (int x = (y + 1) % 2; x < 8; x = x + 2)
   }

   //torna il valore della casella
   public int getValue(int c) 
   {
	  int value=0;
	  value=board[c].getValue();
      return value;
   }
   
   public Coordinate getPosition(int c) 
   {
	   Coordinate position;
	   position=board[c].getPosition();
	   return position;
   }
             
   public Checker getChecker(Coordinate c)
   {
      return board[c.get() - 1];
   }
   
   
   public void setChecker(Checker checker, Coordinate c)
   {
      board[c.get() - 1] = checker;
      checker.setPosition(c);
   }
   
   
   public void removeChecker(Checker checker) 
   {
      board[checker.getPosition().get() - 1] = null;
   }
   
   
   public boolean vacantCoordinate(Coordinate c) 
   {
      return (getChecker(c) == null);
   }

   
   public int evaluate() 
   {
      int score = 0;
      Coordinate c = null;
      for (int i = 1; i < 33; i++)
      {
         c = new Coordinate(i);
         if (getChecker(c) != null)
            score = score + getChecker(c).getValue();
      }
      return score;
   }
  

   public void setHistory(MoveList history)
   {
      this.history = history;
   }
   
   
   public MoveList getHistory() 
   {
      return history;
   }


   public void addMoveToHistory(Move move)
   {
      history.add(move);
   }
   
   //inizializzazione della board
   public void initialize() 
   {
	   for (int i = 1; i < 13; i++)
	        Black(i);   
	   for (int i = 13; i < 21; i++)
	         Vuoto(i);
	   for (int i = 21; i < 33; i++)
	         White(i); 
   } 
   
   //casella dove c'è pedina nera
   public void Black(int i) 
   {
         board[i - 1] = new BlackChecker(new Coordinate(i));   
   }  
   
   //casella dove c'è pedina bianca
   public void White(int i) 
   {
         board[i - 1] = new WhiteChecker(new Coordinate(i));   
   }  
   
   //casella vuota
   public void Vuoto(int i) 
   {
         board[i - 1] =null;   
   }  
   
   //passatogli un vettore la trasforma in Board
   public void VettoreToBoard(int[] cmucam)
   {
   	Coordinate temp = null;
   	 for (int i = 1; i < 33; i++) 
        {
           temp = new Coordinate(i);
           if (getChecker(temp) != null)
           {
           	//se c'è una pedina nera
           	if(cmucam[i-1]==1)
           	{
           		Black(i);
           	}
           	//se c'è una pedina bianca
           	if(cmucam[i-1]==2)
           	{
           		White(i);
           	}
           	//se la casella è vuota
           	if(cmucam[i-1]==5)
           	{
           		Vuoto(i);
           	}
           }
        }
   }
   
   // For the BoardList class.
   public void setNext(Board next) 
   { 
      this.next = next;   
   }
   
   
   // For the BoardList class.
   public Board getNext() 
   {
      return next;
   }
  
   
   public String toString() 
   {
      String stringBoard[][] = new String[8][8];
      Coordinate temp = null;
      for (int i = 1;i < 33; i++) 
      {
         temp = new Coordinate(i);
         if (getChecker(temp) != null)
            stringBoard[temp.column()][temp.row()] = getChecker(temp).toString();
      }
      String s = " +---+---+---+---+---+---+---+---+\n ";
      for (int y = 0; y < 8; y++)
      {
         for (int x = 0; x < 8; x++)
         {
            if (stringBoard[x][y] != null)
               s = s + "| " + stringBoard[x][y] + " ";
            else
               s = s + "|   ";
         }
         s = s + "| (" + ((y*4)+1) + "-" + ((y*4)+4) + ")";
         s = s + "\n +---+---+---+---+---+---+---+---+\n ";       
      }
      return s;
   }
}