package tesi;


import junit.framework.*;
import tesi.*;

public class MoveListTest extends TestCase {

   public MoveListTest(String name) {
      super(name);      
   }

   public static Test suite() {      
      TestSuite suite = new TestSuite(MoveListTest.class);     
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(MoveListTest.class);
   }
   
   
   public void testAdd() {
      Coordinate c1 = new Coordinate(6);
      Checker w1 = new WhiteChecker(c1);
      Move move1 = new MoveNormal(w1, new Coordinate(2));
      
      Coordinate c2 = new Coordinate(15);
      Checker b1 = new BlackChecker(c2);
      Move move2 = new MoveNormal(b1, new Coordinate(18));
      
      Coordinate c3 = new Coordinate(24);
      Checker w2 = new WhiteChecker(c3);
      Move move3 = new MoveNormal(w2, new Coordinate(20));
      
      MoveList movelist = new MoveList();
      movelist.add(move1);
      movelist.add(move2);
      movelist.add(move3);
      
      assertTrue(movelist.size() == 3);
      assertTrue(movelist.get(0) == move1);
      assertTrue(movelist.get(1) == move2);
      assertTrue(movelist.get(2) == move3);
      
      MoveIterator iterator = movelist.getIterator();
      assertTrue(iterator.next() == move1);
      assertTrue(iterator.next() == move2);
      assertTrue(iterator.next() == move3);
   }
   
   
   public void testRemove() {
      Coordinate c1 = new Coordinate(6);
      Checker w1 = new WhiteChecker(c1);
      Move move1 = new MoveNormal(w1, new Coordinate(2));
      
      Coordinate c2 = new Coordinate(15);
      Checker b1 = new BlackChecker(c2);
      Move move2 = new MoveNormal(b1, new Coordinate(18));
      
      Coordinate c3 = new Coordinate(24);
      Checker w2 = new WhiteChecker(c3);
      Move move3 = new MoveNormal(w2, new Coordinate(20));
      
      MoveList movelist = new MoveList();
      movelist.add(move1);
      movelist.add(move2);
      movelist.add(move3);
      movelist.remove(move1);
      
      assertTrue("1", movelist.size() == 2);
      assertTrue("2", movelist.get(0) == move2);
      assertTrue("3", movelist.get(1) == move3);
      movelist.remove(move3);
      assertTrue("4", movelist.size() == 1);
      assertTrue("5", movelist.get(0) == move2);
   }              
}