package tesi;


public abstract class Move 
{   
   protected Coordinate destination;
   protected Checker checker;
   protected Move next = null;
      
   public abstract boolean isJump();
   public abstract String toString();
   public abstract Move copy(Board newBoard);
   public abstract Move copy();
   
   
   public Checker getChecker() 
   {
      return checker;
   }
   
   
   public Coordinate getDestination() 
   {
      return destination;
   }


   // For the MoveList class.
   public void setNext(Move next) 
   {
      this.next = next;   
   }
   
   // For the MoveList class.
   public Move getNext() 
   {
      return next;
   }
}