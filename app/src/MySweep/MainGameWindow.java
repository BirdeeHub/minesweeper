package MySweep;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//This is the main game board display window. contains action listeners and displays control buttons, and a Grid instance, which is the game board.
public class MainGameWindow extends javax.swing.JFrame {//Originally grid and mainGameWindow were all 1 class but it got way too long.
    //-----------------------Initialize-----------------------------
    private final int Fieldx, Fieldy, bombCount, lives;//<-- variables to hold field x and y, number of bombs and lives.
    //display stuff
    private static final Color PURPLE = new Color(58, 0, 82);//<-- these variables are final, meaning they can't be changed.
    private static final Color GREEN = new Color(0, 255, 0);       //You can start them as uninitialized if you want, like our board size above,
    private static final Dimension DefaultWindowSize = new Dimension(830, 830);//but they must be set before the end of the constructor.

    private JLabel GameOverDisplay = new JLabel();//our other 3 display labels and functions to set them.
    private JLabel BombsFoundDisplay = new JLabel();
    private JLabel livesLostDisplay = new JLabel();
    private void setBombsFoundDisplay(){//<-- the values are read from the Grid instance because the logic is there and the display for this is here.
        BombsFoundDisplay.setText("M:" + Integer.toString(grid.getBombsFound()) + "/" + Integer.toString(bombCount));
    }
    private void setLivesLostDisplay(){//<-- you can initialize functions before the constructor but only do it if they are short and actually improve readability
        livesLostDisplay.setText("L:" + Integer.toString(grid.getLivesLeft()) + "/" + Integer.toString(lives));
    }
    private static final String highScoreMessage = "Record Time!";//to change text of game over messages in menubar display, edit from here
    private static final String newBoardSizeAndWonMessage = "New Board Cleared!";
    private static final String wonAndNotHighScoreMessage = "Cleared!";
    private static final String diedButNewBoardMessage = "1st Board Death";//<-- you can make things both final and static if you want
    private static final String diedAndNotNewBoardMessage = "Exploded...";
    private void setGameOverDisplay(){//<-- we set which one based on an index from grid whenever we call this function. same as the other labels.
        int[] GOIndex = new int[2];//<-- This is a 2 integer array. GOIndex[0] is message index, GOIndex[1] is won value.
        GOIndex = grid.getGameOverIndex();//<-- if not in game over state, these will both be -1.
        if(GOIndex[1]==1){ //<-- if you won
            GameOverDisplay.setText((GOIndex[0]==2)?highScoreMessage:((GOIndex[0]==1)?newBoardSizeAndWonMessage:wonAndNotHighScoreMessage));
        }else if(GOIndex[1]==0){ //<-- if you lost
            GameOverDisplay.setText((GOIndex[0]==1)?diedButNewBoardMessage:diedAndNotNewBoardMessage);
        }else {//<-- if you havent won or lost yet
            GameOverDisplay.setText("");
        }
    }
    private JLabel timeDisplay = new JLabel();//<-- create thing to display our timer
    private final Timer displayTimer = new Timer();//<-- Create Timer Displayer (the actual timer is in minefield)
    private final TimerTask timeDisplayTask = new TimerTask() {//<-- this is added to displayTimer in constructor to start the time display
        public void run() {//<-- it does this every x number of milliseconds
            long time = grid.getTime();//<-- returns -1 if game has not yet started
            if(time == -1){//<-- If no game started
                timeDisplay.setText("");//<-- set text to nothing
            }else{
                timeDisplay.setText(Long.toString(time/1000));//<-- if you want to add a time format you can do that here. time variable is in milliseconds
            } //we pass the value through grid.getTime() to get the correct minefield's timer without needing to find it from here
        }//the actual time we save from is in Minefield. it has a 200ms refresh rate which is quite close to the system one. 
    };
    //logic stuff for listeners and the variable to hold the game board
    private Grid grid;//<-- Game Board Class (action listeners not included, only a function to set them. we set them here with the rest.)
    //things for listeners that needed persistance across buttons or are used in different scopes
    private JScrollPane scrollPane;
    private JToggleButton markToggle = new JToggleButton("Mark");
    private JToggleButton chordToggle = new JToggleButton("Chord");
    private boolean LMB = false;
    private boolean RMB = false;
    private JButton currentButton = null;
    //these are accessible from instructions window if it has a reference to this instance of this class. They are public.
    void toggleDarkMode(){
        grid.toggleDarkMode();
    }
    boolean isDMOn(){
        return grid.isDarkMode();
    }
//---------------------MainGameWindow CONSTRUCTOR----------------------MainGameWindow CONSTRUCTOR----------------------------MainGameWindow CONSTRUCTOR------------------------------
    public MainGameWindow(int w, int h, int bombNum, int lives) {
        Fieldx = w;
        Fieldy = h;
        this.lives = lives;//<-- "this.something" allows us to refer to 'something' belonging to 'this' instance of the class
                                                //which is useful because we already have a lives that got passed in by the constructor
        bombCount = bombNum;
        grid = new Grid(Fieldx, Fieldy, bombCount, lives);//<-- **Generate game board**
        displayTimer.scheduleAtFixedRate(timeDisplayTask, 0, 50);//<-- this just displays the time in Minefield answers. This rate is higher than the one in minefield so you dont tilt players
        scrollPane = new JScrollPane(grid);//<-- create a scroll pane containing our grid panel
        addGridActionListeners();//<-- I could have done this in Grid but it was nice to keep the listeners in 1 place, and it would have been harder
        initComponentsAndMiscListeners();
    }
    //functions!
    private void addGridActionListeners(){//i would have needed to pass a reference to the toggle buttons for click action in grid constructor. this is better.
        grid.addCellListener(new MouseAdapter() {//<-- addCellListener() function in grid to add our listener to each cell.
            @Override
            public void mouseEntered(MouseEvent e){//<-- this allows the 1.5 click trick by actually getting the component the mouse is over rather than just
                currentButton=(JButton)e.getSource();// getting the component that fired the mousePressed and released actions which will always be the same
            }//     ^ this is because each Adapter is associated with a particular button, and contains both methods, so if one fires, then the release for that one will be the one that runs.
            
            @Override                                  //dont worry too much if this listener is confusing for now.
            public void mousePressed(MouseEvent e) {//<-- you need to know that they can fire on things like mouse pressed
                if(SwingUtilities.isLeftMouseButton(e)){// and how to search for how to write an event listener for a mouse or a key or whatever in a language
                    LMB = true;//<-- set our left mouse button variable true so that all the buttons can hear if the mouse was pressed
                    if(currentButton!=null){//<-- if mouse is over a button
                        if(chordToggle.isSelected()){//<-- if the toggle button for chords is selected
                            grid.doClickType(currentButton, 2);//<-- in this case, we run grid.doClickType 2 (a chord)
                        }else if(markToggle.isSelected()){//<-- if the toggle button for marking is selected
                            grid.doClickType(currentButton, 1);//mark
                        }else if(RMB){//was the right mouse button also pressed?
                            grid.doClickType(currentButton, 2);//Chord
                        }else{//else do regular left click
                            grid.doClickType(currentButton, 0);//regular
                        }
                    }
                }
                if(SwingUtilities.isRightMouseButton(e)){//same thing but for right click, so no need for clickType 0 or checking our toggle buttons
                    RMB = true;
                    if(currentButton!=null){
                        if(LMB){
                            grid.doClickType(currentButton, 2);
                        }else{
                            grid.doClickType(currentButton, 1);
                        }
                    }
                }
                setBombsFoundDisplay();//<-- update display text with changes
                setLivesLostDisplay();//doing this after clicking a cell made most sense.
                setGameOverDisplay();
            }

            @Override //<-- mouseAdapter already had a default for these. But we want to write them instead to do our game stuff so we override
            public void mouseReleased(MouseEvent e) {//<-- not holding the mouse anymore
                if(SwingUtilities.isLeftMouseButton(e)){
                    LMB = false;//<-- set the global variable back so other buttons know you released the button
                }
                if(SwingUtilities.isRightMouseButton(e)){
                    RMB = false;
                }
            }
        });
        grid.addMouseWheelListener(new MouseWheelListener() {//zoom and scroll (only active over grid because it is added to the JPanel, i.e. the grid instance itself)
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            int rotationAmount = 0;
            boolean zoomInProgress = false;
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.isControlDown()){
                    if(zoomInProgress){rotationAmount+=e.getWheelRotation();//<--save for when previous action is done
                    }else{//initialize stuff we need to know for recentering before changing zoom
                        zoomInProgress = true;
                        rotationAmount += e.getWheelRotation();
                        PointerInfo pointerInfo = MouseInfo.getPointerInfo();//<-- get current mouse location on screen
                        Point mouseLocation = pointerInfo.getLocation();
                        SwingUtilities.convertPointFromScreen(mouseLocation, grid);//<-- find mouse relative to grid
                        int mouseX1 = mouseLocation.x;//<-- save current position over grid for recentering later
                        int mouseY1 = mouseLocation.y;
                        getContentPane().setPreferredSize(MainGameWindow.this.getContentPane().getSize());//<-- stop it from reverting to original size
                        //get wheel and cell size info and do zoom
                        SwingUtilities.invokeLater(() -> {//<-- invoke later to avoid issues with many simultaneous scroll inputs,
                            int[] gridSizesOldNew = grid.doZoom(-rotationAmount, mouseX1, mouseY1);//<-- changes grid size, gives old and new grid sizes
                            rotationAmount=0;//reset rotation amount
                            grid.setCellFontSize();//set font size after doZoom so that its not dependent on rotationAmount
                            
                            //make sure it keeps the spot the mouse is over in more or less the same place on the board.
                            int mouseX2 = (gridSizesOldNew[2]*mouseX1)/gridSizesOldNew[0];//if i calculate these inside doZoom and then call
                            int mouseY2 = (gridSizesOldNew[3]*mouseY1)/gridSizesOldNew[1];//revalidate it works weird. Doing it here works great. Idk.
                            int scrollAmountX = (mouseX2 - mouseX1);
                            int scrollAmountY = (mouseY2 - mouseY1);
                            int newScrollValueX = horizontalScrollBar.getValue() + scrollAmountX;
                            int newScrollValueY = verticalScrollBar.getValue() + scrollAmountY;
                            revalidate();//i got rid of the need to pack() by creating newScrollValueX & Y BEFORE i revalidate apparently
                            horizontalScrollBar.setValue(newScrollValueX);
                            verticalScrollBar.setValue(newScrollValueY);
                            zoomInProgress = false;
                        });
                    }
                } else {//if no control key, do scroll
                    verticalScrollBar.setUnitIncrement(20);
                    verticalScrollBar.setValue(verticalScrollBar.getValue() + (e.getUnitsToScroll() * verticalScrollBar.getUnitIncrement()));
                }
            }
        });
    }
    private void initComponentsAndMiscListeners() {//-------------------initComponents() on window below-----------------------------------------------------------------------------
        //--------------init the stuff that doesnt need to be global
        JMenuBar menuBar = new JMenuBar();
        JPanel menuPanel = new JPanel(new GridBagLayout());//<-- we will be using gridbaglayout manager again for this window, but inside a menu bar.
        GridBagConstraints menuBagConstraints = new GridBagConstraints();
        JButton NewGame = new JButton("New Game");
        JButton Reset = new JButton("Reset");
        JButton HowToPlay = new JButton("Help");
        JToggleButton toggleQuestionMarking = new JToggleButton("?'s?");
        JButton ScoreBoard = new JButton("HiSc");
        Font ScoreAreaFontSize = new Font("Tahoma", 0, 20);
        //------------------------set properties of stuff
        setBombsFoundDisplay();
        setLivesLostDisplay();
        setGameOverDisplay();
        BombsFoundDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        livesLostDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        GameOverDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        timeDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        BombsFoundDisplay.setFont(ScoreAreaFontSize);
        livesLostDisplay.setFont(ScoreAreaFontSize);
        GameOverDisplay.setFont(ScoreAreaFontSize);
        timeDisplay.setFont(ScoreAreaFontSize);
        menuPanel.setBackground(PURPLE);
        timeDisplay.setForeground(GREEN);
        BombsFoundDisplay.setForeground(GREEN);
        livesLostDisplay.setForeground(GREEN);
        GameOverDisplay.setForeground(GREEN);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setBackground(PURPLE);
        scrollPane.getHorizontalScrollBar().setBackground(PURPLE);
        scrollPane.setBackground(PURPLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(DefaultWindowSize);
        setIconImage(MineSweeper.MineIcon);
        //---------------------component adding and layout managing
        menuBagConstraints.gridx =0;
        menuBagConstraints.gridy =0;//           theyre all in a menu bar.
        menuBagConstraints.gridwidth =1;// so i only need to change x value and weight each time
        menuBagConstraints.gridheight =1;
        menuBagConstraints.weightx = 0.0;
        menuBagConstraints.fill = GridBagConstraints.BOTH;
        menuPanel.add(markToggle, menuBagConstraints);
        menuBagConstraints.gridx =1;
        menuPanel.add(chordToggle, menuBagConstraints);
        menuBagConstraints.gridx =2;
        menuPanel.add(toggleQuestionMarking, menuBagConstraints);
        menuBagConstraints.gridx =3;
        menuBagConstraints.weightx = 0.25;
        menuPanel.add(BombsFoundDisplay, menuBagConstraints);
        menuBagConstraints.gridx =4;
        menuPanel.add(livesLostDisplay, menuBagConstraints);
        menuBagConstraints.gridx =5;
        menuBagConstraints.weightx = 1.0;
        menuPanel.add(timeDisplay, menuBagConstraints);
        menuBagConstraints.weightx = 0.75;
        menuBagConstraints.gridx =6;
        menuPanel.add(GameOverDisplay, menuBagConstraints);
        menuBagConstraints.gridx =7;
        menuBagConstraints.weightx = 0.0;
        menuPanel.add(ScoreBoard, menuBagConstraints);
        menuBagConstraints.gridx =8;
        menuPanel.add(HowToPlay, menuBagConstraints);
        menuBagConstraints.gridx =9;
        menuPanel.add(Reset, menuBagConstraints);
        menuBagConstraints.gridx =10;
        menuPanel.add(NewGame, menuBagConstraints);
        menuBar.add(menuPanel);
        setJMenuBar(menuBar);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        //System.out.println("Start packing.");//<-- this would print to terminal when you reached here if you uncommented it
        pack();//<-- This pack() call is the slowest, heaviest thing in the entire program. But we need to call it to use layout managers...
        //System.out.println("done packing.");//<-- if you need proof, uncomment this and start a 300x300 with 6000 bombs game.
        getContentPane().revalidate();//^For 300x300 (90,000 cells) execution reaches here in under 1s, and the rest after it is even faster.
        grid.setCellFontSize();//       ^pretty sure to make it faster would need a different language unless there is a better pack function somewhere?

        //------------------misc action listeners----------------------misc action listeners-------------misc action listeners-------------misc action listeners------
        Reset.addActionListener(new ActionListener() {//<-- reset button
            long clickmemory = System.currentTimeMillis();//<-- this is for protection from spamming
            public void actionPerformed(ActionEvent evt) {
                getContentPane().setPreferredSize(MainGameWindow.this.getContentPane().getSize());//<-- stop it from reverting to old window size
                if((System.currentTimeMillis()-clickmemory)>1000){//<-- this means 1s has passed since last click
                    clickmemory = System.currentTimeMillis();//<-- if so, update clickmemory
                    grid.ResetBoard();//<-- call reset function from grid
                    setBombsFoundDisplay();//and also get the properly reset display values
                    setLivesLostDisplay();
                    setGameOverDisplay();
                    getContentPane().revalidate();//<-- revalidate is faster than pack and is fine for here.
                }else clickmemory = System.currentTimeMillis();//if you spammed, update clickmemory instead
            }
        });
        toggleQuestionMarking.addActionListener(new ActionListener(){//<---toggles the ? option for marking cells on and off
            public void actionPerformed(ActionEvent e){
                grid.toggleQuestionMarks();
            }
        });
        NewGame.addActionListener(new ActionListener() {//<-- new game button listener
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new OpeningWindow().setVisible(true);
                    }
                });
                MainGameWindow.this.dispose();
            }
        });
        HowToPlay.addActionListener(new ActionListener() {// instructions window
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new instructionsWindow(MainGameWindow.this).setVisible(true);
                    }
                });
            }
        });
        ScoreBoard.addActionListener(new ActionListener() {// starts the scoreboard
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new ScoresWindow(Fieldx, Fieldy, bombCount, lives, MainGameWindow.this).setVisible(true);
                    }//clickable is false at start so you dont cancel ur game on accident.
                });
            }
        });
        KeyAdapter keyAdapter = new KeyAdapter() {//enter key functionality
            public void keyPressed(KeyEvent evt) {
                // Check if the Enter key is pressed
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    // get focused component source
                    Component CurrComp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                    if(CurrComp instanceof JToggleButton){
                        ((JToggleButton)CurrComp).doClick();//<-- you need to cast it as the correct type
                    }else ((JButton)CurrComp).doClick();
                }
            }
        };
        ScoreBoard.addKeyListener(keyAdapter);//<-- add the listeners
        NewGame.addKeyListener(keyAdapter);
        Reset.addKeyListener(keyAdapter);
        HowToPlay.addKeyListener(keyAdapter);
        markToggle.addKeyListener(keyAdapter);
        toggleQuestionMarking.addKeyListener(keyAdapter);
        chordToggle.addKeyListener(keyAdapter);

        getContentPane().setVisible(true);//<-- let there be light!

        //Cool! We have a functioning window to display our game! Now it is time to read Grid up through the constructor and see whats going on there!
    }
}