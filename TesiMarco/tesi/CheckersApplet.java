/*
<applet code="AppletButton.class"
        archive="example-swing/layout.jar"
        width="350" height="60">
<param name="windowClass" value="GridWindow">
<param name="windowTitle" value="GridLayout">
<param name="buttonText" value="Click here to see a GridLayout in action">
</applet>
*/
package tesi;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CheckersApplet extends JApplet {
   static ImageIcon whiteChecker = null;
   static ImageIcon blackChecker = null;
   static ImageIcon whiteKingChecker = null;
   static ImageIcon blackKingChecker = null;
   
   public void start() {}
   
   public void stop() {}
   
   public void destroy() {}
   
   public void init() {
      // Create the images.
      //whiteChecker = new ImageIcon(ClassLoader.getSystemResource("images/white.gif"));
      whiteChecker = new ImageIcon("image/white.gif");
      blackChecker = new ImageIcon("image/black.gif");
      whiteKingChecker = new ImageIcon("image/whiteKing.gif");
      blackKingChecker = new ImageIcon("image/blackKing.gif");
      
      MainFrame checkersWindow = new MainFrame();
      checkersWindow.setVisible(true);
   }
   
   
   public static ImageIcon getWhiteCheckerIcon() {
      return whiteChecker;
   }
   
   
   public static ImageIcon getBlackCheckerIcon() {
      return blackChecker;
   }
   
   
   public static ImageIcon getWhiteKingCheckerIcon() {
      return whiteKingChecker;
   }
   
   
   public static ImageIcon getBlackKingCheckerIcon() {
      return blackKingChecker;
   }
   
   
   private URL getUrl(String filename) {
        URL codeBase = getCodeBase();
        URL url = null;

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.err.println("Couldn't create image: " +
                               "badly specified URL");
            return null;
        }
    
        return url;
    }
}