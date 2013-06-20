package tesi;


import junit.framework.*;
import tesi.*;

public class CheckersGameTest extends TestCase {

   public CheckersGameTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(CheckersGameTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(CheckersGameTest.class);
   }
   
   
   public void testValidUserMove() {
      CheckersGame cg = new CheckersGame();
      cg.setUserColor(Checker.WHITE);
      Board board = new Board();
      
      Rules.placeChecker(board, 23, Checker.WHITE, false);
      Rules.placeChecker(board, 18, Checker.BLACK, false);
      Rules.placeChecker(board, 26, Checker.BLACK, false);
      Rules.placeChecker(board, 27, Checker.BLACK, false);
      
      assertTrue("1", cg.validateUserMove(new Coordinate(23), new Coordinate(14), 
                 board) != null);         
      assertTrue("2", cg.validateUserMove(new Coordinate(23), new Coordinate(19), 
                 board) != null);         
      assertTrue("3", cg.validateUserMove(new Coordinate(23), new Coordinate(16), 
                 board) == null);         
   }
}