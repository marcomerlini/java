package tesi;


import tesi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RulesDialog extends JDialog implements ActionListener {
   
   public RulesDialog(Frame parent) {
      super(parent, "Rules of Checkers", true);
      Toolkit toolkit = getToolkit();
      Dimension size = toolkit.getScreenSize();
      setLocation(300, 100);
      JTextArea outputPanel = new JTextArea(message, 30, 45);
      outputPanel.setEditable(false);
      outputPanel.setBackground(Color.lightGray);
      JScrollPane scrollOutputPane = new JScrollPane(outputPanel);
      scrollOutputPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
      scrollOutputPane.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
      JPanel messagePane = new JPanel();
      messagePane.add(scrollOutputPane);
      getContentPane().add(messagePane);
      
      JPanel buttonPane = new JPanel();
      JButton button = new JButton("OK");
      buttonPane.add(button);
      button.addActionListener(this);
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      pack();
      setVisible(true);
   }
   
   
   public void actionPerformed(ActionEvent e) {
      setVisible(false);
      dispose(); 
   }  
   
   private String message = 
   "Rules of American Checkers\n" +
   "---------------------------------------\n\n" +
   "Checkers is a board game played between two players, who alternate moves. The\n" +
   "player who cannot move, because he has no pieces, or because all of his pieces\n" +
   "are blocked, loses the game.\n\n" +
   "Moving: A piece which is not a king can move one square, diagonally, forward,\n" +
   "as in the diagram at the right. A king can move one square diagonally, forward\n" +
   "or backward. A piece (piece or king) can only move to a vacant square. A move\n" +
   "can also consist of one or more jumps (next paragraph).\n" +
   "To move a piece you click with the mouse on the square that the piece occupy.\n" +
   "Then you click on the empty square that you want to move to.\n\n" +
   "Jumping: You capture an opponent's piece (piece or king) by jumping over it,\n" +
   "diagonally, to the adjacent vacant square beyond it. The three squares must be\n" +
   "lined up (diagonally adjacent). A king can jump diagonally, forward or backward.\n" +
   "A piece which is not a king, can only jump diagonally forward. You can make a\n" +
   "multiple jump, with one piece only, by jumping to empty square to empty square.\n" +
   "You can only jump one piece, with any given jump, but you can jump several\n" +
   "pieces, with a move of several jumps. You remove the jumped pieces from the\n" +
   "board. You cannot jump your own piece. You cannot jump the same piece twice, in\n" +
   "the same move. If you can jump, you must. And, a multiple jump must be\n" +
   "completed; you cannot stop part way through a multiple jump. If you have a\n" +
   "choice of jumps, you can choose among them, regardless of whether some of them\n" +
   "are multiple, or not. A piece, whether it is a king or not, can jump a king.\n\n" +
   "Kinging: When a piece reaches the last row (the King Row), it becomes a King. A\n" +
   "second checker is placed on top of that one, by the opponent. A piece that has\n" +
   "just kinged, cannot continue jumping pieces, until the next move.\n\n" +
   "The players take turns moving. You can make only one move per turn. You must\n" +
   "move. If you cannot move, you lose. Players normally choose colors at random,\n" +
   "and then alternate colors in subsequent games.\n";
}
