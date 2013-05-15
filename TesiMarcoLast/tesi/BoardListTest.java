package tesi;


import junit.framework.*;
import tesi.*;

public class BoardListTest extends TestCase {

   public BoardListTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(BoardListTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(BoardListTest.class);
   }
   
   
   public void testFindBestBoard() {
      Board board1 = new Board();   // score = 0
      board1.initialize();
      
      Board board2 = new Board();   // score = 2
      Rules.placeChecker(board2, 23, Checker.WHITE, false);
      
      Board board3 = new Board();   // score = -2
      Rules.placeChecker(board3, 18, Checker.BLACK, false);
      
      Board board4 = new Board();   // score = -3
      Rules.placeChecker(board4, 19, Checker.BLACK, true);
      
      BoardList boardlist = new BoardList();
      boardlist.add(board1);
      boardlist.add(board2);
      boardlist.add(board3);
      boardlist.add(board4);
      assertTrue(boardlist.findBestBoard(Checker.WHITE) == board2);
      assertTrue(boardlist.findBestBoard(Checker.BLACK) == board4);
   }              
}