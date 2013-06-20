package tesi;


public class Coordinate
{
   private int c;
  
   public Coordinate(int c) 
   {
      this.c = c;
   }

   
   public int get()
   {
      return c;
   }
   
   
   public boolean equals(Coordinate d) 
   {
      return (c == d.get());
   }
   
   
   // Returns the row (0-7) of the coordinate (1-32).
   public int row()
   {
      int row;
      row = (int) Math.floor(c / 4.0);
      if (c % 4 == 0)
         return row - 1;
      else
         return row;
   }
   
   
   // Returns the column (0-7) of the coordinate (1-32).
   public int column() 
   {
      if (row() % 2 == 0)
         return ( ((c - (row() * 4)) * 2) - 1 );
      else
         return ( ((c - (row() * 4)) * 2) - 2 );
   }
   
   public int toInt()
   {
	   String s = this.toString();
       int num = Integer.parseInt(s);
	   return num;
   }
   
   public String toString()
   {
      return "" + c;
   }
   
   
   // Returns the coordinate of the square that is one square above and one
   // square to the left of c.
   public Coordinate upLeftMove()
   {
      if (row() % 2 == 0)
         return new Coordinate(c - 4);
      else
         return new Coordinate(c - 5);
   }
   
   
   public Coordinate upRightMove()
   {
      if (row() % 2 == 0)
         return new Coordinate(c - 3);
      else
         return new Coordinate(c - 4);
   }
   
   
   public Coordinate downLeftMove()
   {
      if (row() % 2 == 0)
         return new Coordinate(c + 4);
      else
         return new Coordinate(c + 3);
   }
   
   
   public Coordinate downRightMove()
   {
      if (row() % 2 == 0)
         return new Coordinate(c + 5);
      else
         return new Coordinate(c + 4);
   }
   
   
   public Coordinate upLeftJump()
   {
      return new Coordinate(c - 9);
   }
   
   
   public Coordinate upRightJump()
   {
      return new Coordinate(c - 7);
   }
   
   
   public Coordinate downLeftJump()
   {
      return new Coordinate(c + 7);
   }
   
   
   public Coordinate downRightJump()
   {
      return new Coordinate(c + 9);
   }
}