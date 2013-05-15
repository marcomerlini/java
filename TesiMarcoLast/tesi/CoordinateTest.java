package tesi;


import junit.framework.*;
import tesi.*;

public class CoordinateTest extends TestCase {

   public CoordinateTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(CoordinateTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(CoordinateTest.class);
   }     
   
   
   public void testRow() {
      assertTrue("1", new Coordinate(26).row() == 6);
      assertTrue("2", new Coordinate(2).row() == 0);
      assertTrue("3", new Coordinate(4).row() == 0);
      assertTrue("4", new Coordinate(13).row() == 3);
      assertTrue("5", new Coordinate(20).row() == 4);
      assertTrue("6", new Coordinate(23).row() == 5);
      assertTrue("7", new Coordinate(32).row() == 7);  
   }
   
   
   public void testColumn() {
      assertTrue("1", new Coordinate(26).column() == 3);
      assertTrue("2", new Coordinate(2).column() == 3);
      assertTrue("3", new Coordinate(4).column() == 7);
      assertTrue("4", new Coordinate(13).column() == 0);
      assertTrue("5", new Coordinate(20).column() == 7);
      assertTrue("6", new Coordinate(21).column() == 0);
      assertTrue("7", new Coordinate(22).column() == 2);
      assertTrue("8", new Coordinate(23).column() == 4);
      assertTrue("9", new Coordinate(32).column() == 6);
   }
   
   
   public void testupLeftMove() {
      assertTrue("1", new Coordinate(6).upLeftMove().equals(new Coordinate(1)));
      assertTrue("2", new Coordinate(10).upLeftMove().equals(new Coordinate(6)));
      assertTrue("3", new Coordinate(32).upLeftMove().equals(new Coordinate(27)));
      assertTrue("4", new Coordinate(25).upLeftMove().equals(new Coordinate(21)));
   } 
   
   
   public void testupRightMove() {
      assertTrue("1", new Coordinate(6).upRightMove().equals(new Coordinate(2)));
      assertTrue("2", new Coordinate(10).upRightMove().equals(new Coordinate(7)));
      assertTrue("3", new Coordinate(32).upRightMove().equals(new Coordinate(28)));
      assertTrue("4", new Coordinate(25).upRightMove().equals(new Coordinate(22)));
   } 

   
   public void testDownLeftMove() {
      assertTrue("1", new Coordinate(6).downLeftMove().equals(new Coordinate(9)));
      assertTrue("2", new Coordinate(10).downLeftMove().equals(new Coordinate(14)));
      assertTrue("3", new Coordinate(26).downLeftMove().equals(new Coordinate(30)));
      assertTrue("4", new Coordinate(19).downLeftMove().equals(new Coordinate(23)));
   }
   
   public void testDownRightMove() {
      assertTrue("1", new Coordinate(6).downRightMove().equals(new Coordinate(10)));
      assertTrue("2", new Coordinate(10).downRightMove().equals(new Coordinate(15)));
      assertTrue("3", new Coordinate(26).downRightMove().equals(new Coordinate(31)));
      assertTrue("4", new Coordinate(19).downRightMove().equals(new Coordinate(24)));
   } 
    
   // ------- Jump --------
   
   public void testupLeftJump() {
      assertTrue("1", new Coordinate(15).upLeftJump().equals(new Coordinate(6)));
      assertTrue("2", new Coordinate(10).upLeftJump().equals(new Coordinate(1)));
      assertTrue("3", new Coordinate(32).upLeftJump().equals(new Coordinate(23)));
      assertTrue("4", new Coordinate(26).upLeftJump().equals(new Coordinate(17)));
   } 
   
   
   public void testupRightJump() {
      assertTrue("1", new Coordinate(15).upRightJump().equals(new Coordinate(8)));
      assertTrue("2", new Coordinate(10).upRightJump().equals(new Coordinate(3)));
      assertTrue("3", new Coordinate(31).upRightJump().equals(new Coordinate(24)));
      assertTrue("4", new Coordinate(25).upRightJump().equals(new Coordinate(18)));
   } 

   
   public void testDownLeftJump() {
      assertTrue("1", new Coordinate(6).downLeftJump().equals(new Coordinate(13)));
      assertTrue("2", new Coordinate(10).downLeftJump().equals(new Coordinate(17)));
      assertTrue("3", new Coordinate(23).downLeftJump().equals(new Coordinate(30)));
      assertTrue("4", new Coordinate(19).downLeftJump().equals(new Coordinate(26)));
   }
   
   
   public void testDownRightJump() {
      assertTrue("1", new Coordinate(6).downRightJump().equals(new Coordinate(15)));
      assertTrue("2", new Coordinate(10).downRightJump().equals(new Coordinate(19)));
      assertTrue("3", new Coordinate(22).downRightJump().equals(new Coordinate(31)));
      assertTrue("4", new Coordinate(19).downRightJump().equals(new Coordinate(28)));
   }
}