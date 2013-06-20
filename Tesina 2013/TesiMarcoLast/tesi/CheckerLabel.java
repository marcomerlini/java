package tesi;

import tesi.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CheckerLabel extends JTextField {
   
   public CheckerLabel()  {
      super();
      setBackground(new Color(175,0,0));   // Red.
      setBounds(new Rectangle(50,50));
      setBorder(new EmptyBorder(0,0,0,0));
      setEditable(false);
   }
}