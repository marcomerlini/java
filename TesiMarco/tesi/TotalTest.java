package tesi;


import junit.framework.*;

public class TotalTest extends TestCase {

   public TotalTest(String name) {
      super(name);
   }
  
   public static Test suite() {
      TestSuite suite = new TestSuite();      
      suite.addTestSuite(tesi.BoardTest.class);
      suite.addTestSuite(tesi.CoordinateTest.class);
      suite.addTestSuite(tesi.RulesTest.class);
      suite.addTestSuite(tesi.MoveListTest.class);
      suite.addTestSuite(tesi.BoardListTest.class);
      suite.addTestSuite(tesi.CheckersGameTest.class);
      return suite;
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(TotalTest.suite());      
   }
}