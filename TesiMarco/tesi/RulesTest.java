package tesi;


import junit.framework.*;
import tesi.*;

public class RulesTest extends TestCase {

   public RulesTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(RulesTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(RulesTest.class);
   }
     
   public void testValidMove() {
      Board board = new Board();
      board.initialize();
      Coordinate c1 = new Coordinate(9);
      Coordinate c2 = new Coordinate(13);
      Coordinate c3 = new Coordinate(14);
      assertTrue("1", Rules.validBlackMove(c1, c2, board));
      assertTrue("2", Rules.validBlackMove(c1, c3, board));
      assertTrue("3", ! Rules.validBlackMove(new Coordinate(2), 
                        new Coordinate(6), board));
      assertTrue("4", ! Rules.validBlackMove(new Coordinate(3), 
                        new Coordinate(8), board));                        
      assertTrue("5", ! Rules.validBlackMove(new Coordinate(12), 
                        new Coordinate(17), board));                        
                        
      assertTrue("6", Rules.validWhiteMove(new Coordinate(22), 
                      new Coordinate(17), board));
      assertTrue("7", Rules.validWhiteMove(new Coordinate(22), 
                      new Coordinate(18), board));
      assertTrue("8", ! Rules.validWhiteMove(new Coordinate(26), 
                      new Coordinate(23), board));
      assertTrue("9", ! Rules.validWhiteMove(new Coordinate(21), 
                      new Coordinate(16), board));
   }
   
   
   public void testValidWhiteJump() {
      Board board = new Board();
      
      Rules.placeChecker(board, 23, Checker.WHITE, false);
      Rules.placeChecker(board, 22, Checker.WHITE, false);
      Rules.placeChecker(board, 18, Checker.BLACK, false);
      Rules.placeChecker(board, 19, Checker.BLACK, false);
      
      Coordinate w1c = new Coordinate(23);
      assertTrue("1", Rules.validWhiteJump(w1c, new Coordinate(14), board));
      assertTrue("2", Rules.validWhiteJump(w1c, new Coordinate(16), board));
      assertTrue("3", ! Rules.validWhiteJump(w1c, new Coordinate(30), board));
      assertTrue("1", ! Rules.validWhiteJump(w1c, new Coordinate(32), board));
      assertTrue("4", ! Rules.validWhiteJump(w1c, new Coordinate(26), board));
      assertTrue("5", ! Rules.validWhiteJump(w1c, new Coordinate(27), board));
      
      Rules.placeChecker(board, 16, Checker.BLACK, false);
      assertTrue("6", ! Rules.validWhiteJump(w1c, new Coordinate(16), board));
      assertTrue("7", ! Rules.validWhiteJump(new Coordinate(22), 
                                             new Coordinate(13), board));
      
      Rules.placeChecker(board, 17, Checker.WHITE, false);
      assertTrue("8", ! Rules.validWhiteJump(new Coordinate(22), 
                                             new Coordinate(13), board));
      
      Rules.placeChecker(board, 21, Checker.WHITE, false);
      // Check for off board jumps.
      assertTrue("9", ! Rules.validWhiteJump(new Coordinate(21), 
                                             new Coordinate(12), board));
   }    


      public void testValidBlackJump() {
      Board board = new Board();
      
      Coordinate b1c = new Coordinate(10);
      Checker b1 = new BlackChecker(b1c);
      board.setChecker(b1, b1c);
      
      Coordinate b2c = new Coordinate(11);
      Checker b2 = new BlackChecker(b2c);
      board.setChecker(b2, b2c);
      
      Coordinate w1c = new Coordinate(14);
      Checker w1 = new WhiteChecker(w1c);
      board.setChecker(w1, w1c);
      
      Coordinate w2c = new Coordinate(15);
      Checker w2 = new WhiteChecker(w2c);
      board.setChecker(w2, w2c);
           
      assertTrue("1", Rules.validBlackJump(b1c, new Coordinate(17), board));
      assertTrue("2", Rules.validBlackJump(b1c, new Coordinate(19), board));
      assertTrue("3", Rules.validBlackJump(b2c, new Coordinate(18), board));
      assertTrue("4", ! Rules.validBlackJump(b1c, new Coordinate(6), board));
      assertTrue("5", ! Rules.validBlackJump(b1c, new Coordinate(7), board));
      assertTrue("6", ! Rules.validBlackJump(b1c, new Coordinate(1), board));
      assertTrue("7", ! Rules.validBlackJump(b1c, new Coordinate(3), board));
      
      // Cannot jump to occupied square.
      Coordinate w3c = new Coordinate(17);
      Checker w3 = new WhiteChecker(w3c);
      board.setChecker(w3, w3c);
      assertTrue("8", ! Rules.validBlackJump(w1c, new Coordinate(17), board));
      
      // No jumping over black checkers.
      Coordinate b3c = new Coordinate(16);
      Checker b3 = new BlackChecker(w3c);
      board.setChecker(b3, b3c);
      assertTrue("8", ! Rules.validWhiteJump(w2c, new Coordinate(20), board));
      
      // Check for off board jumps.
      Coordinate b4c = new Coordinate(12);
      Checker b4 = new WhiteChecker(b4c);
      board.setChecker(b4, b4c);
      assertTrue("9", ! Rules.validWhiteJump(b4c, new Coordinate(21), board));
   }    

  
   public void testValidKingMove() {
      Board board = new Board();
      
      // White king.
      Rules.placeChecker(board, 2, Checker.WHITE, true);
      Coordinate w1c = new Coordinate(2);
      assertTrue("1", Rules.validKingMove(w1c, new Coordinate(6), board));
      assertTrue("2", Rules.validKingMove(w1c, new Coordinate(7), board));
      Rules.placeChecker(board, 15, Checker.WHITE, true);
      Coordinate w2c = new Coordinate(15);
      assertTrue("3", Rules.validKingMove(w2c, new Coordinate(10), board));
      assertTrue("4", Rules.validKingMove(w2c, new Coordinate(11), board));
      assertTrue("5", Rules.validKingMove(w2c, new Coordinate(18), board));
      assertTrue("6", Rules.validKingMove(w2c, new Coordinate(19), board));
      Rules.placeChecker(board, 1, Checker.WHITE, true);
      Coordinate w3c = new Coordinate(1);
      assertTrue("7", Rules.validKingMove(w3c, new Coordinate(5), board));
      
      // Black king.
      Rules.placeChecker(board, 31, Checker.BLACK, true);
      Coordinate b1c = new Coordinate(31);
      assertTrue("11", Rules.validKingMove(b1c, new Coordinate(26), board));
      assertTrue("12", Rules.validKingMove(b1c, new Coordinate(27), board));
      Rules.placeChecker(board, 22, Checker.BLACK, true);
      Coordinate b2c = new Coordinate(22);
      assertTrue("13", Rules.validKingMove(b2c, new Coordinate(17), board));
      assertTrue("14", Rules.validKingMove(b2c, new Coordinate(18), board));
      assertTrue("15", Rules.validKingMove(b2c, new Coordinate(25), board));
      assertTrue("16", Rules.validKingMove(b2c, new Coordinate(26), board));
    }
    
    
  
   public void testValidKingJump() {
      Board board = new Board();
      
      // White king.
      Rules.placeChecker(board, 23, Checker.WHITE, true);
      Rules.placeChecker(board, 26, Checker.BLACK, false);
      Rules.placeChecker(board, 27, Checker.BLACK, false);
      Rules.placeChecker(board, 19, Checker.BLACK, false);
      Coordinate w1c = new Coordinate(23);
      assertTrue("1", Rules.validKingJump(w1c, new Coordinate(30), board));
      assertTrue("2", Rules.validKingJump(w1c, new Coordinate(32), board));
      assertTrue("3", ! Rules.validKingJump(w1c, new Coordinate(14), board));
      assertTrue("4", Rules.validKingJump(w1c, new Coordinate(16), board));
      
      // Black king.
      Rules.placeChecker(board, 10, Checker.BLACK, true);
      Rules.placeChecker(board, 6, Checker.WHITE, false);
      Rules.placeChecker(board, 7, Checker.WHITE, false);
      Rules.placeChecker(board, 14, Checker.WHITE, false);
      Coordinate b1c = new Coordinate(10);
      assertTrue("5", Rules.validKingJump(b1c, new Coordinate(1), board));
      assertTrue("6", Rules.validKingJump(b1c, new Coordinate(3), board));                                          
      assertTrue("7", Rules.validKingJump(b1c, new Coordinate(17), board));
      assertTrue("8", ! Rules.validKingJump(b1c, new Coordinate(19), board));
   }
  
   
   public void testExecuteMove() {
      Board board = new Board();
      
      Coordinate c1 = new Coordinate(6);
      Checker w1 = new WhiteChecker(c1);
      board.setChecker(w1, c1);
      
      Move move1 = new MoveNormal(w1, new Coordinate(2));
      Board newBoard = Rules.executeMove(move1, board);
      
      assertTrue(!newBoard.vacantCoordinate(new Coordinate(2)));
      assertTrue(newBoard.getChecker(new Coordinate(2)).getColor() == 
                 Checker.WHITE);
      assertTrue(newBoard.vacantCoordinate(new Coordinate(6))); 
      assertTrue(newBoard.getChecker(new Coordinate(2)).kingRow()); 
   }           
   
   
   public void testExecuteJump() {
      Board board = new Board();
      
      Rules.placeChecker(board, 27, Checker.WHITE, false);
      Rules.placeChecker(board, 23, Checker.BLACK, false);
      Rules.placeChecker(board, 14, Checker.BLACK, false);
      Rules.placeChecker(board, 6, Checker.BLACK, false);
      Rules.placeChecker(board, 15, Checker.BLACK, false);
  
      Move move = new MoveJump(board.getChecker(new Coordinate(27)), 
                                                new Coordinate(18));
      Board newBoard = Rules.executeMove(move, board);
      
      assertTrue(newBoard.vacantCoordinate(new Coordinate(23))); 
      assertTrue(newBoard.vacantCoordinate(new Coordinate(14))); 
      assertTrue(newBoard.vacantCoordinate(new Coordinate(6))); 
      assertTrue(!newBoard.vacantCoordinate(new Coordinate(2)));
      assertTrue(!newBoard.vacantCoordinate(new Coordinate(15))); 
      assertTrue(newBoard.getChecker(new Coordinate(2)).kingRow()); 
   }       
   
   
   public void testEvaluateBoard() {
      Board board = new Board();
      board.initialize();
      assertTrue("0", board.evaluate() == 0);
      
      Rules.placeChecker(board, 18, Checker.WHITE, false);
      assertTrue("1", board.evaluate() == 2);
      
      Rules.placeChecker(board, 15, Checker.BLACK, true);
      assertTrue("2", board.evaluate() == -1);
   }
      
      
   public void testMinimaxAB1() {
      /*
      Board board = new Board();
      board.initialize();
      board = Rules.executeMove(new MoveNormal(board.getChecker(new Coordinate(9)), new Coordinate(13)), board);
      board = Rules.executeMove(new MoveNormal(board.getChecker(new Coordinate(21)), new Coordinate(17)), board);
      System.out.println("\n" + board);
      board.getHistory().reset();
      board = Rules.minimaxAB(board, 6, Checker.BLACK, 
                                     Rules.minusInfinityBoard(),
                                     Rules.plusInfinityBoard());
      //System.out.println("\nComputer move: " + newBoard.getHistory().first());
      System.out.println("\n" + board.getHistory());
      */
   }

   
   public void testMiniMaxAB2() {
      /*
      Board board = new Board();
      board.initialize();
         
      Move move1 = new MoveNormal(board.getChecker(new Coordinate(21)),
                            new Coordinate(17));
      Board board1 = Rules.executeMove(move1, board);
      
      Move move2 = new MoveNormal(board1.getChecker(new Coordinate(9)),
                            new Coordinate(13));
      Board board2 = Rules.executeMove(move2, board1);
      
      Move move3 = new MoveNormal(board2.getChecker(new Coordinate(17)),
                            new Coordinate(14));
      Board board3 = Rules.executeMove(move3, board2);
      
      Move move4 = new MoveNormal(board3.getChecker(new Coordinate(12)),
                            new Coordinate(16));
      Board board4 = Rules.executeMove(move4, board3);
      
      Move move5 = new MoveNormal(board4.getChecker(new Coordinate(25)),
                            new Coordinate(21));
      Board board5 = Rules.executeMove(move5, board4);
      
      Move move6 = new MoveNormal(board5.getChecker(new Coordinate(8)),
                            new Coordinate(12));
      Board board6 = Rules.executeMove(move6, board5);
      
      System.out.println("");
      Board board8 = Rules.minimaxAB(board6, 6, Checker.WHITE, 
                                     Rules.minusInfinityBoard(),
                                     Rules.plusInfinityBoard());
      System.out.println(board8.getHistory());
      
      Board board8 = Rules.minimax(board6, 6, Checker.WHITE);
      System.out.println(board8.getHistory());
      
      
      Move move7 = new MoveNormal(board6.getChecker(new Coordinate(14)),
                            new Coordinate(9));
      Board board7 = Rules.executeMove(move7, board6);
      */
   }
}