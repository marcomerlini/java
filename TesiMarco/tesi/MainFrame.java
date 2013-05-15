package tesi;

import tesi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
	public static char[] mossadafare;
	public static char[] da;
	public static char[] a;
	public static char ciao;
	
   // GUI fields.
   private JMenuBar menuBar = new JMenuBar();
   private JMenu checkersMenu = new JMenu("Options");
   private JMenu levelMenu = new JMenu("Level");
   private JMenu colorMenu = new JMenu("Your color");
   private JMenu firstMoveMenu = new JMenu("First move");
   private JRadioButtonMenuItem level1;
   private JRadioButtonMenuItem level2;
   private JRadioButtonMenuItem level3;
   private JRadioButtonMenuItem colorWhite;
   private JRadioButtonMenuItem colorBlack;
   private JRadioButtonMenuItem firstMoveUser;
   private JRadioButtonMenuItem firstMoveComputer;
   private ButtonGroup levelGroup;
   private ButtonGroup colorGroup;
   private ButtonGroup firstMoveGroup;
   private JPanel mainPanel = new JPanel();
   private JTextArea outputPanel = new JTextArea(3,20);
   private Cursor thinkCursor = new Cursor(Cursor.WAIT_CURSOR);
   private Cursor moveCursor = new Cursor(Cursor.DEFAULT_CURSOR);
   String output = "";  
   
   // Levels.
   private static final int LEVEL1 = 3;
   private static final int LEVEL2 = 5;
   private static final int LEVEL3 = 8;
   private int thinkDepth = LEVEL2;    // Default.
   
   // States.
   private static final int FROM = 1;  
   private static final int TO = 2;
   private static final int FROM_MULTIPLE = 3;
   private static final int TO_MULTIPLE = 4;
   private static final int COMPUTER_THINKS = 5;
   private static final int NOT_STARTED = 6;
   private int state = NOT_STARTED; 
   
   // First move.
   private static final int USER_MOVES_FIRST = 1;  
   private static final int COMPUTER_MOVES_FIRST = 2;  
   private int firstMove = USER_MOVES_FIRST;
   
   private Checker multipleJumpsChecker = null;
   private int userColor = Checker.WHITE;
   private int computerColor = Checker.BLACK;
   private Coordinate from;
   private Board board = new Board();
   private static boolean seenStartDialog = false;
   
   
   // ********** GUI ************
   private void initGui() {
      Toolkit toolkit = getToolkit();
      setTitle("Checkers");
      Dimension size = toolkit.getScreenSize();
      setBounds(size.width/4, size.height/4, 400, 470);
      setResizable(false);
      setJMenuBar(menuBar);
      menuBar.add(checkersMenu);
      checkersMenu.add(new StartAction("Begin new game"));
      checkersMenu.add(new ResetAction("Reset board"));
      // Color radio buttons.
      checkersMenu.add(colorMenu);
      colorWhite = new JRadioButtonMenuItem("White", userColor == Checker.WHITE);
      colorBlack = new JRadioButtonMenuItem("Black", userColor == Checker.BLACK);
      colorWhite.addActionListener(new ColorListener(Checker.WHITE));
      colorBlack.addActionListener(new ColorListener(Checker.BLACK));
      colorGroup = new ButtonGroup();
      colorGroup.add(colorWhite);
      colorGroup.add(colorBlack);
      colorMenu.add(colorWhite);
      colorMenu.add(colorBlack);
      // Level radio buttons.
      checkersMenu.add(levelMenu);   
      level1 = new JRadioButtonMenuItem("Level 1", thinkDepth == LEVEL1);
      level2 = new JRadioButtonMenuItem("Level 2", thinkDepth == LEVEL2);
      level3 = new JRadioButtonMenuItem("Level 3", thinkDepth == LEVEL3);
      level1.addActionListener(new LevelListener(LEVEL1));
      level2.addActionListener(new LevelListener(LEVEL2));
      level3.addActionListener(new LevelListener(LEVEL3));
      levelGroup = new ButtonGroup();
      levelGroup.add(level1);
      levelGroup.add(level2);
      levelGroup.add(level3);
      levelMenu.add(level1);
      levelMenu.add(level2);
      levelMenu.add(level3);
      // First move buttons.
      checkersMenu.add(firstMoveMenu);   
      firstMoveUser = new JRadioButtonMenuItem("User moves first",firstMove == USER_MOVES_FIRST);
      firstMoveComputer = new JRadioButtonMenuItem("Computer moves first",firstMove == COMPUTER_MOVES_FIRST);
      firstMoveUser.addActionListener(new FirstMoveListener(USER_MOVES_FIRST));
      firstMoveComputer.addActionListener(new FirstMoveListener(COMPUTER_MOVES_FIRST));
      firstMoveGroup = new ButtonGroup();
      firstMoveGroup.add(firstMoveUser);
      firstMoveGroup.add(firstMoveComputer);
      firstMoveMenu.add(firstMoveUser);
      firstMoveMenu.add(firstMoveComputer);
      // The rest of the buttons.
      checkersMenu.add(new AboutAction("About Checkers")); // dove c'è scritto le cose di baumann
      checkersMenu.add(new RulesAction("Rules of Checkers", this));
      checkersMenu.add(new ExitAction("Exit"));
      mainPanel.setLayout(new GridLayout(8,8,0,0));
      createGrid();
      outputPanel.setEditable(false);
      outputPanel.setBackground(Color.lightGray);
      JScrollPane scrollOutputPane = new JScrollPane(outputPanel);
      scrollOutputPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      getContentPane().add(mainPanel, BorderLayout.CENTER);
      getContentPane().add(scrollOutputPane, BorderLayout.SOUTH);
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
   }
   
   
   protected void processWindowEvent(WindowEvent e) {
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         dispose();
         System.exit(0);
      }
      super.processWindowEvent(e);
   }
        
   
   private void createGrid() {      
      for (int y = 0; y < 8; y++)
         for (int x = 0; x < 8; x++)
            if (y % 2 == 0)
               if (x % 2 == 0) 
                  mainPanel.add(new CheckerLabel());
               else 
                  mainPanel.add(new CheckerButton(findCoordinateEvenRow(x,y), this));
            else 
               if (x % 2 == 0)
                  mainPanel.add(new CheckerButton(findCoordinateOddRow(x,y), this));
               else 
                  mainPanel.add(new CheckerLabel());
   }
   
   
   // Paints the board (model) on the gui (view). 
   private void updateGrid() {
      JButton button = null;
      for (int i = 1; i < 33; i++) {
         button = (JButton) mainPanel.getComponent(findSquare(i));
         if (board.getChecker(new Coordinate(i)) != null) {
            button.setText("");           
            button.setIcon(board.getChecker(new Coordinate(i)).getIcon());
            //guardare quale immagine è
            //mettere in matrice il risultato
         }
         else {
            button.setText("" + i);
            button.setIcon(null);
         }
      }
   }
   
  
   private void outputText(String s) {
      output = "\n> " + s;
      //output = "> " + s + "\n" + output;
      //outputPanel.setText(output);
      System.out.println(s);
   }
   
   
   // ********** Functionality ***********
   
   public MainFrame() {
      initGui();
      board.initialize();
      updateGrid();
      String s=board.toString();
      outputText(s);
      outputText(s);
   }


   public static void main(String args[]) {
      MainFrame m = new MainFrame();
      m.show();
   }
   
   
   /**
    * Performs an action depending on the state of the game. It gets the action
    * from the CheckerButton class.
    */
   public void handleButtonEvents(Coordinate c) {
      if (state == NOT_STARTED)
         outputText("Please choose begin game fra the menu.");
      else   
         if (state == COMPUTER_THINKS)
            outputText("You cannot move while the computer is thinking.");
         else 
            switch(state) {
               case FROM:
                  from = c;
                  state = TO;
                  break;
               case TO:
                  moveUser(from, c);
                  break;
               case FROM_MULTIPLE:
                  from = c;
                  state = TO_MULTIPLE;
                  break;
               case TO_MULTIPLE:
                  multipleJumps(from, c);
                  break;
            }        
   }        
      
   
   // The computer thinks....
   public void computerMoves() {
      MoveList validMoves = Rules.findAllValidMoves(board, computerColor);
      if (validMoves.size() == 0) {
         JOptionPane.showMessageDialog((Component)this, "\nCongratualations!" +
         "You win\n", "Checkers", JOptionPane.INFORMATION_MESSAGE);
         outputText("Congratulations. You win.");
         state = NOT_STARTED;
         levelMenu.setEnabled(true);
         colorMenu.setEnabled(true);
         firstMoveMenu.setEnabled(true);
      }
      else {
         board.getHistory().reset();
         setCursor(thinkCursor);
         Board comBoard = Rules.minimaxAB(board,thinkDepth,computerColor,Rules.minusInfinityBoard(),Rules.plusInfinityBoard());
         setCursor(moveCursor);                                          
         Move move = comBoard.getHistory().first();
         board = Rules.executeMove(move, board);
         updateGrid();
         MoveIterator iterator = board.getHistory().getIterator();
         String moves = "";
         while (iterator.hasNext()) {
            moves = moves + iterator.next(); //la mossa!!!!!!
            if (iterator.hasNext()) moves = moves + " , ";
         }     
         System.out.println(moves);
         outputText("Computer moves: " + moves);
         state = FROM;
         validMoves = Rules.findAllValidMoves(board, userColor);
         if (validMoves.size() == 0) {
            JOptionPane.showMessageDialog((Component)this, "Sorry! " +
            "The computer wins.", "Checkers", JOptionPane.INFORMATION_MESSAGE);
            outputText("Sorry. The computer wins.");
            state = NOT_STARTED;
            levelMenu.setEnabled(true);
            colorMenu.setEnabled(true);
            firstMoveMenu.setEnabled(true);
         }
      }           
   }  
   
   
   /**
    * A move is validated. If it is a normal move the method checks that there 
    * is no mandatory jumps to be made. If the move is a jump the method 
    * checks if a multiple jump is possible.
    */
   public void moveUser(Coordinate from, Coordinate to) {
      Move move = validateUserMove(from, to);
      if (move == null) {
         outputText("Invalid move.");
         state = FROM;
      }
      else {
         if (move.isJump()) {
            board = Rules.executeUserJump(move, board);
            outputText("You move: " + move);
            updateGrid();
            multipleJumpsChecker = board.getChecker(move.getDestination());
            if (mandatoryJump(multipleJumpsChecker, board)) {
               outputText("A multiple jump must be completed.");
               state = FROM_MULTIPLE;
            }
            else {
               state = COMPUTER_THINKS;
               computerMoves();
            }       
         }
         else {   // Normal move.
            if (Rules.existJump(board, userColor)) {
               outputText("Invalid move. If you can jump, you must.");
               state = FROM;
            }
            else {
               board = Rules.executeMove(move, board);
               outputText("You move: " + move);
               updateGrid();
               state = COMPUTER_THINKS;
               computerMoves();
            }             
         }
      }
   }
   
   
   // Returns true if checker can make a jump.
   private boolean mandatoryJump(Checker checker, Board board) {
      MoveList movelist = new MoveList();
      checker.findValidJumps(movelist, board);
      if (movelist.size() != 0)
         return true;
      else
         return false;
   }
   
   
   /**
    * Checks that the entered move is a jump and that the multiple jumps is 
    * performed with the same checker.
    */
   private void multipleJumps(Coordinate from, Coordinate to) {  
      Move move = validateUserMove(from, to);
      if (move == null) {
         outputText("Invalid move.");
         state = FROM_MULTIPLE;
      }
      else {
         if (!move.isJump()) {
            outputText("Invalid move. Must be a jump.");
            state = FROM_MULTIPLE;
         }
         else {
            if (!multipleJumpsChecker.getPosition().equals(from)) {
               outputText("Invalid move. Must be a jump with the same checker.");
               state = FROM_MULTIPLE;
            }   
            else {
               board = Rules.executeUserJump(move, board);
               outputText("You move: " + move);
               updateGrid();
               multipleJumpsChecker = board.getChecker(move.getDestination());
               if (mandatoryJump(multipleJumpsChecker, board)) {
                  outputText("A multiple jump must be completed.");
                  state = FROM_MULTIPLE;
               }
               else {
                  state = COMPUTER_THINKS;
                  computerMoves();
               }   
            }    
         }
      }
   }

      
   // Returns a valid move entered by the user otherwise null.
   public Move validateUserMove(Coordinate from, Coordinate to) {
      Move move = null;   
      Checker checker = board.getChecker(from);
      if (checker != null) {
         if (userColor == Checker.WHITE) {   
            if (checker.getColor() == Checker.WHITE) {  
               if (checker.getValue() == Checker.WHITE_VALUE_KING) {
                  if (Math.abs(from.row() - to.row()) == 1) {
                     if (Rules.validKingMove(from, to, board))
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(from, to, board))
                        move = new MoveJump(checker, to);
               }           
               else {  // Normal white checker. 
                  if (from.row() - to.row() == 1) { 
                     if (Rules.validWhiteMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validWhiteJump(from, to, board))
                        move = new MoveJump(checker, to);       
               }
            }
         }
         else {   // User is black.
            if (checker.getColor() == Checker.BLACK) {  
               if (checker.getValue() == Checker.BLACK_VALUE_KING) {
                  if (Math.abs(from.row() - to.row()) == 1) {
                     if (Rules.validKingMove(from, to, board)) 
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validKingJump(from, to, board)) 
                        move = new MoveJump(checker, to);
               }           
               else {   // Normal black checker.
                  if (to.row() - from.row() == 1) {
                     if (Rules.validBlackMove(from, to, board))
                        move = new MoveNormal(checker, to);
                  }
                  else
                     if (Rules.validBlackJump(from, to, board)) 
                        move = new MoveJump(checker, to);
               }
            }
         }
      }
      return move;
   }
   
   
   // Transform a coordinate from the Board grid (1-32) to the GUI grid (0-63).
   private int findSquare(int c) {
      int row = new Coordinate(c).row();
      if (row % 2 == 0)
         return (c * 2) - 1;
      else
         return (c * 2) - 2; 
   }
   
   
   // Returns the board coordinate (1-32) from the square coordinates (0-7,0-7).
   // The square coordinate must be at an even row.
   private int findCoordinateEvenRow(int x, int y) {
      return ((int) Math.ceil(x/2.0)) + (y * 4);   
   }
   
   
   private int findCoordinateOddRow(int x, int y) {
      return ((int) Math.ceil(x/2.0)) + (y * 4) + 1;   
   }
   
   
   // *****************************************************
   // Inner classes for menu items.
   
   class StartAction extends AbstractAction {   
      StartAction(String name) {
         super(name);
      }
      
      public void actionPerformed(ActionEvent e) {
         levelMenu.setEnabled(false);
         colorMenu.setEnabled(false);
         firstMoveMenu.setEnabled(false);
         board.initialize();
         updateGrid();
         if (! seenStartDialog) {
            JOptionPane.showMessageDialog((Component)e.getSource(), "To move a " +
            "piece click with the mouse on the square that the piece occupy. " +
            "Then click on the empty square that you want to move to.", "Checkers", 
            JOptionPane.INFORMATION_MESSAGE);
            seenStartDialog = true;  
         }
         if (firstMove == COMPUTER_MOVES_FIRST) {
            state = COMPUTER_THINKS;
            computerMoves();
         }
         else {
            outputText("Please make the first move");
            state = FROM;
         }     
      }
   }
   
   
   class ResetAction extends AbstractAction {   
      ResetAction(String name) {
         super(name);
      }
      
      public void actionPerformed(ActionEvent e) {
         levelMenu.setEnabled(true);
         colorMenu.setEnabled(true);
         firstMoveMenu.setEnabled(true);
         board.initialize();
         updateGrid();
         state = NOT_STARTED;
      }
   }
   
   
   class ExitAction extends AbstractAction {   
      ExitAction(String name) {
         super(name);
      }
      
      public void actionPerformed(ActionEvent e) {
         dispose();
         System.exit(0);
      }
   }
   
   
   class AboutAction extends AbstractAction {   
      AboutAction(String name) {
         super(name);
      }
      
      public void actionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog((Component)e.getSource(), "Checkers\n" +
                "Copyright Anders Baumann 2002\n www.it-c.dk/people/baumann",
                "About Checkers", JOptionPane.INFORMATION_MESSAGE);
      }
   }
   
   
   class RulesAction extends AbstractAction {   
      private Frame parent;
      
      RulesAction(String name, Frame p) {
         super(name);
         parent = p;
      }
      
      public void actionPerformed(ActionEvent e) {
         new RulesDialog(parent);
      }
   }
   
   
   class LevelListener implements ActionListener  {
      private int level;
    
      LevelListener(int l) {
         level = l;
      }
    
      public void actionPerformed(ActionEvent e) {
         thinkDepth = level;
      }
   }
   
   
   class ColorListener implements ActionListener  {
      private int color;
    
      ColorListener(int c) {
         color = c;
      }
    
      public void actionPerformed(ActionEvent e) {
         userColor = color;
         computerColor = Rules.opponent(userColor);
      }
   }
   
   
   class FirstMoveListener implements ActionListener  {
      private int fm;
    
      FirstMoveListener(int f) {
         fm = f;
      }
    
      public void actionPerformed(ActionEvent e) {
         firstMove = fm;
      }
   }
}

