package tesi;

import tesi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckerButton extends JButton {
   
   private Coordinate coordinate;
   private MainFrame checkerGui;
 
   public CheckerButton(int c, MainFrame m)  {
      super();
      coordinate = new Coordinate(c);
      checkerGui = m;
      setBackground(new Color(0,100,0)); // Green.
      setFont(new Font("Serif", Font.PLAIN, 10));
      setVerticalTextPosition(AbstractButton.CENTER);
      setHorizontalTextPosition(AbstractButton.CENTER);
      setBounds(new Rectangle(50,50));
      setBorderPainted(false);
      addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            new ButtonThread(coordinate, checkerGui).start();
         }}
      );
   }
   
   
   /** 
    * Sends a button press to the MainFrame. We need a separate thread for this,
    * since if the action to perform a move is done inside the actionPerformed
    * method, the button will remain pressed during the whole action. This 
    * means that it will also remain pressed when the computer moves, which is
    * undesirable. 
    */
   class ButtonThread extends Thread {
      private Coordinate coordinate;
      private MainFrame checkerGui;
      
      public ButtonThread(Coordinate c, MainFrame m) {
         coordinate = c;
         checkerGui = m;
      }
      
      public void run() {
         checkerGui.handleButtonEvents(coordinate);
      }
   } 
}