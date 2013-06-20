package tesi;


import junit.framework.*;
import tesi.*;

public class BoardTest extends TestCase {

   public BoardTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(BoardTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(BoardTest.class);
   }
   
   public void testVacantCoordinate() {
      Board board = new Board();
      board.initialize();
      assertTrue(board.vacantCoordinate(new Coordinate(13))); 
      assertTrue(! board.vacantCoordinate(new Coordinate(1))); 
   }     
   
   public void testCopy() {
      Board board = new Board();
      Coordinate c1 = new Coordinate(23);
      Checker w1 = new WhiteChecker(c1);
      board.setChecker(w1, c1);
      Board newBoard = board.copy();
      Coordinate c2 = new Coordinate(18);
      Checker b1 = new BlackChecker(c2);
      board.setChecker(b1, c2);
      assertTrue(newBoard.vacantCoordinate(c2));
      Coordinate c3 = new Coordinate(19);
      Checker b2 = new BlackChecker(c3);
      newBoard.setChecker(b2, c3);
      assertTrue(board.vacantCoordinate(c3));
   } 
}