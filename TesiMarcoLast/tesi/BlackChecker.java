package tesi;

import javax.swing.ImageIcon; 
import java.net.URL;

public class BlackChecker extends Checker
{

   public BlackChecker(Coordinate c)
   {
      checkerState = new NormalStateBlack();
      position = c;
      value = BLACK_VALUE_NORMAL;
      stringRep = "X";
   }
     
   
   public int getColor() 
   {
      return BLACK;
   }
   
   public ImageIcon getIcon() 
   {
      return null;
   }
   
   
   public void makeKing() 
   {
      checkerState = new KingState();
      value = BLACK_VALUE_KING;
      stringRep = "B";
   }
   
   
   public boolean isKing() 
   {
      return (value == BLACK_VALUE_KING);
   }
   
     
   public boolean kingRow()
   {
      return ( (position.get() >= 29) && (position.get() <= 32) );
   }
   
   
   public Checker copy()
   {
      Checker newChecker = new BlackChecker(position);
      if (value == BLACK_VALUE_KING)
         newChecker.makeKing();
      return newChecker;
   }          
}