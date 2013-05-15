package tesi;


/** Implements the state pattern for the metods findValidMoves and 
 * findValidJumps in class Checker.
 */
public class NormalStateBlack implements CheckerState {   
   
   // Attaches valid moves to the validMoves list. Returns true if a valid jump
   // exist.
   public boolean findValidMoves(final Checker c, final Board board, 
                              MoveList validMoves) {   
      if (! findValidJumps(c, board, validMoves)) {
         // If no valid jump exist then look for valid moves.
         if (Rules.validBlackMove(c.getPosition(), c.getPosition().downLeftMove(), 
             board))
            validMoves.add(new MoveNormal(c, c.getPosition().downLeftMove()));
            
         if (Rules.validBlackMove(c.getPosition(), c.getPosition().downRightMove(), 
             board))
            validMoves.add(new MoveNormal(c, c.getPosition().downRightMove()));
         return false;
      }
      else
         return true;
   }
   
   // Attaches valid jumps to the validJumps list. Returns true if a valid jump
   // exist.
   public boolean findValidJumps(final Checker c, final Board board, 
                              MoveList validJumps) {   
      boolean found = false;
      if (Rules.validBlackJump(c.getPosition(), c.getPosition().downLeftJump(), 
        board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downLeftJump()));
         found = true;
      }
      
      if (Rules.validBlackJump(c.getPosition(), c.getPosition().downRightJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downRightJump()));
         found = true;
      }     
      return found;
   }
}