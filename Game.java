import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;

public class Game extends JPanel implements MouseListener, ActionListener {
    private Grid grid;
    private JToggleButton[][] buttonArray;
    private Tile[][] tiles;
    JMenu game, icon, controls;
    JMenuBar menuBar;
    private int clickCount = 0;
    Timer timer;
    int timePassed=-1;
    JLabel timeLabel;
    private JFrame frame;
    private boolean gameOver=false;
    private int regTiles =0;
    private int numOfFlags = 0;
    private boolean firstClick = true;
    JLabel flagsLabel;
    private int mines =0;
    JButton resetButton;
    ImageIcon block;
    ImageIcon empty = new ImageIcon("./Sprites/Classic/empty.png");
    ImageIcon bomb = new ImageIcon("./Sprites/Classic/mine.png");
    ImageIcon bell = new ImageIcon("./Sprites/Christmas/bell.png");
    ImageIcon clickedBomb = new ImageIcon("./Sprites/Classic/clickedmine.png");
    ImageIcon flag = new ImageIcon("./Sprites/Classic/flagged.png");
    ImageIcon sant = new ImageIcon("./Sprites/Christmas/sant.png");
    JMenuItem beginner, intermediate, expert;
    JMenuItem classic, christmas, pirate;
    String currentTheme="";
    public Game(Grid grid) {
        this.grid = grid;
        currentTheme= grid.getTheme();
    }

    public Game(int n) {
        grid = new Grid(9, 9, 10, "Classic");
        currentTheme= grid.getTheme();
    }

    public Game() {
        grid = new Grid(9, 9, 10, "Classic");
        currentTheme= grid.getTheme();
    }

    public void start() {

        frame = new JFrame("Minesweeper");
        timer = new Timer();
        timeLabel = new JLabel("Time Passed: "+timePassed);
        timer.schedule(new UpdateTimer(), 0, 1000);
        menuBar = new JMenuBar();
        game = new JMenu("Game");
        beginner = new JMenuItem("Beginner");
        beginner.addActionListener(this);
        intermediate = new JMenuItem("Intermediate");
        intermediate.addActionListener(this);
        expert = new JMenuItem("Expert");
        expert.addActionListener(this);
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        beginner.setSelected(true);
        menuBar.add(game);
        icon = new JMenu("Themes");
        controls = new JMenu("Controls");
        JMenuItem controlItem = new JMenuItem("Click to reveal the tile! Left-Click to Flag! The numbered tiles tell you how many mines are adjacent to them! When all the tiles EXCEPT the mines are clicked, you win!");
        controls.add(controlItem);
        menuBar.add(icon);
        menuBar.add(controls);
        classic = new JMenuItem("Classic");
        classic.addActionListener(this);
        christmas = new JMenuItem("Christmas");
        christmas.addActionListener(this);
        pirate = new JMenuItem("Pirate");
        pirate.addActionListener(this);
        icon.add(classic);
        icon.add(christmas);
        icon.add(pirate);
        flagsLabel = new JLabel("Number of Flags: "+numOfFlags);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        frame.setLayout(new BorderLayout());
        topPanel.add(menuBar, BorderLayout.NORTH);
        this.setLayout(new GridLayout(grid.getWidth(), grid.getHeight()));
        frame.setSize(grid.getHeight()*40, grid.getWidth()*47); // frame size should be variable based on number of buttons in grid
        mines = grid.getMines();
        setGame();
        frame.add(this);
        //resetButton= new JButton(scaledImage(new ImageIcon("./Sprites/Classic/smile.png")));
        resetButton = new JButton();
        resetButton.setIcon(scaledImage(new ImageIcon("./Sprites/Classic/smile.png")));
        resetButton.setPreferredSize(new Dimension(40, 40));
        resetButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(resetButton, BorderLayout.CENTER);
        buttonPanel.add(flagsLabel, BorderLayout.WEST);
        buttonPanel.add(timeLabel, BorderLayout.EAST);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setGame() {
        grid.setTiles();
        tiles = grid.getTiles();
        buttonArray = new JToggleButton[tiles.length][tiles[0].length];
        regTiles = grid.getHeight()*grid.getWidth() - mines;
        if(currentTheme.equals("Classic"))
            block = new ImageIcon("./Sprites/Classic/block.png");
        else if(currentTheme.equals("Pirate"))
            block = new ImageIcon("./Sprites/Pirate/block.png");
        for(int x=0;x<tiles.length;x++)
        {
            for(int y=0;y<tiles[0].length;y++)
            {
                buttonArray[x][y] = new JToggleButton();
                buttonArray[x][y].addMouseListener(this);
                buttonArray[x][y].setSize(80, 80);
                if(currentTheme.equals("Classic") || currentTheme.equals("Pirate"))
                {
                    buttonArray[x][y].setIcon(scaledImage(block));
                }
                else if(currentTheme.equals("Christmas"))
                {
                    buttonArray[x][y].setBackground(Color.PINK);
                    buttonArray[x][y].setOpaque(true);
                }
                buttonArray[x][y].setBorder(BorderFactory.createBevelBorder(1));
                this.add(buttonArray[x][y]);
            }
        }
    }
    public ImageIcon scaledImage(ImageIcon icon)
    {
        return new ImageIcon(icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
    }
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
    }
    public void setNumberedIcons(JToggleButton jToggleButton, int x, int y)
    {
            ImageIcon one = new ImageIcon();
            ImageIcon two = new ImageIcon();
            ImageIcon three = new ImageIcon();
            ImageIcon four = new ImageIcon();   
            ImageIcon five = new ImageIcon();
            ImageIcon six = new ImageIcon();
            ImageIcon seven = new ImageIcon();
            ImageIcon eight = new ImageIcon();
        if(currentTheme.equals("Classic"))
        {
            one = new ImageIcon("./Sprites/Classic/one.png");
            two = new ImageIcon("./Sprites/Classic/two.png");
            three = new ImageIcon("./Sprites/Classic/three.png");
            four = new ImageIcon("./Sprites/Classic/four.png");   
            five = new ImageIcon("./Sprites/Classic/five.png");
            six = new ImageIcon("./Sprites/Classic/six.png");
            seven = new ImageIcon("./Sprites/Classic/seven.png");
            eight = new ImageIcon("./Sprites/Classic/eight.png");
        }
        else if(currentTheme.equals("Christmas"))
        {
            one = new ImageIcon("./Sprites/Christmas/one.png");
            two = new ImageIcon("./Sprites/Christmas/two.png");
            three = new ImageIcon("./Sprites/Christmas/three.png");
            four = new ImageIcon("./Sprites/Christmas/four.png");   
            five = new ImageIcon("./Sprites/Christmas/five.png");
            six = new ImageIcon("./Sprites/Christmas/six.png");
            seven = new ImageIcon("./Sprites/Christmas/seven.png");
            eight = new ImageIcon("./Sprites/Christmas/eight.png");
            jToggleButton.setBackground(Color.GRAY);
         }
         else if(currentTheme.equals("Pirate"))
         {
            one = new ImageIcon("./Sprites/Pirate/one.png");
            two = new ImageIcon("./Sprites/Pirate/two.png");
            three = new ImageIcon("./Sprites/Pirate/three.png");
            four = new ImageIcon("./Sprites/Pirate/four.png");   
            five = new ImageIcon("./Sprites/Pirate/five.png");
            six = new ImageIcon("./Sprites/Pirate/six.png");
            seven = new ImageIcon("./Sprites/Pirate/seven.png");
            eight = new ImageIcon("./Sprites/Pirate/eight.png");
         }
         switch(tiles[x][y].getNumber())
         {
             case 1: jToggleButton.setIcon(scaledImage(one));
                     break;
             case 2: jToggleButton.setIcon(scaledImage(two));
                     break;
             case 3: jToggleButton.setIcon(scaledImage(three));
                     break;
             case 4: jToggleButton.setIcon(scaledImage(four));
                     break;
             case 5: jToggleButton.setIcon(scaledImage(five));
                     break;
             case 6: jToggleButton.setIcon(scaledImage(six));
                     break;
             case 7: jToggleButton.setIcon(scaledImage(seven));
                     break;
             case 8: jToggleButton.setIcon(scaledImage(eight));
                     break;
             default: break;
         }
    }
    public void revealNeighbors(int x, int y)
    {
        expand(x - 1, y - 1);
        expand(x - 1, y);
        expand(x - 1, y + 1);
        expand(x + 1, y);
        expand(x, y - 1);
        expand(x, y + 1);
        expand(x + 1, y - 1);
        expand(x + 1, y + 1);
    }
    public void expand(int x, int y)
    {
        if(x<0 || y<0)
            return;
        else if(x>=grid.getWidth() || y>=grid.getHeight())
            return;
        else
        {
            if(buttonArray[x][y].isSelected())
                return;
            else
            {
                if(tiles[x][y].isEmpty())
                {
                    buttonArray[x][y].setSelected(true);
                    if(currentTheme.equals("Classic"))
                        buttonArray[x][y].setIcon(scaledImage(empty));
                    else if(currentTheme.equals("Pirate"))
                        buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/empty.png")));
                    else if(currentTheme.equals("Christmas"))
                        buttonArray[x][y].setBackground(Color.GRAY);
                    revealNeighbors(x, y);
                }
                else if(tiles[x][y].isNumbered())
                {
                    buttonArray[x][y].setSelected(true);
                    //buttonArray[x][y].setText(tiles[x][y].getNumber()+"");
                    setNumberedIcons(buttonArray[x][y], x, y);
                    return;
                }
                else if(tiles[x][y].isBomb())
                    return;
                else if(tiles[x][y].isFlagged())
                    return;
            }
        }
    }
    public void revealAllMines(int a, int b)
    {
        gameOver = true;
        for(int x=0;x<buttonArray.length;x++)
        {
            for(int y=0;y<buttonArray[0].length;y++)
            {
                if(tiles[x][y].getState().equals("Bomb") && !(x==a && y==b)) //if a bomb is flagged it does not open up at the end
                {
                    //buttonArray[x][y].setText(tiles[x][y].getState()+"");
                    if(currentTheme.equals("Classic"))
                        buttonArray[x][y].setIcon(scaledImage(bomb));
                    else if(currentTheme.equals("Christmas"))
                        buttonArray[x][y].setIcon(scaledImage(bell));  
                    else if(currentTheme.equals("Pirate"))
                        buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/bomb.png")));
                    buttonArray[x][y].setSelected(true);
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) 
    {
        if(!gameOver)
        {
            for(int x=0;x<buttonArray.length;x++)
            {
                for(int y=0;y<buttonArray[0].length;y++)
                {
                    if(e.getSource()==buttonArray[x][y])
                    {
                        if(!buttonArray[x][y].isSelected())
                        {
                            buttonArray[x][y].setSelected(true);
                            if(e.getButton()==e.BUTTON3)
                            {
                                tiles[x][y].setState("Flag");
                                //buttonArray[x][y].setText("Flag");
                                if(currentTheme.equals("Classic"))
                                    buttonArray[x][y].setIcon(scaledImage(flag));
                                else if(currentTheme.equals("Christmas"))
                                    buttonArray[x][y].setIcon(scaledImage(sant));
                                else if(currentTheme.equals("Pirate"))
                                    buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/flagged.png")));
                                numOfFlags++;
                                flagsLabel.setText("Number of Flags: "+numOfFlags);
                            }
                        }
                        else //this is what happened when a new button is selected
                        {
                            buttonArray[x][y].setSelected(true);
                            if(firstClick)
                            {
                                grid.setNumbers(x, y); //pass in first location to make sure first click always lands on empty square
                                if(e.getButton() == e.BUTTON3)
                                {
                                    tiles[x][y].setState(tiles[x][y].getOriginalState());
                                    //buttonArray[x][y].setText("");
                                    if(currentTheme.equals("Classic") || currentTheme.equals("Pirate"))
                                        buttonArray[x][y].setIcon(scaledImage(block));
                                    else if(currentTheme.equals("Christmas"))
                                        buttonArray[x][y].setIcon(null);
                                    buttonArray[x][y].setSelected(false);
                                    numOfFlags--;
                                    flagsLabel.setText("Number of Flags: "+numOfFlags);
                                }
                                else 
                                {
                                    if(currentTheme.equals("Classic"))
                                        buttonArray[x][y].setIcon(scaledImage(empty));
                                    else if(currentTheme.equals("Pirate"))
                                        buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/empty.png")));
                                    else if(currentTheme.equals("Christmas"))
                                        buttonArray[x][y].setBackground(Color.GRAY);
                                    firstClick=false;
                                    clickCount++;
                                    revealNeighbors(x, y);
                                }
                            }
                            else
                            {
                                if(e.getButton() == e.BUTTON3)
                                {
                                    if(tiles[x][y].isFlagged())
                                    {
                                        tiles[x][y].setState(tiles[x][y].getOriginalState());
                                        //buttonArray[x][y].setText("");
                                        if(currentTheme.equals("Classic") || currentTheme.equals("Pirate"))
                                            buttonArray[x][y].setIcon(scaledImage(block));
                                        else if(currentTheme.equals("Christmas"))
                                            buttonArray[x][y].setIcon(null);
                                        buttonArray[x][y].setSelected(false);
                                        numOfFlags--;
                                        flagsLabel.setText("Number of Flags: "+numOfFlags);
                                    }
                                }
                                else
                                {
                                    clickCount++;
                                    if(tiles[x][y].isNumbered())
                                    {
                                        //buttonArray[x][y].setText(tiles[x][y].getNumber()+"");
                                        setNumberedIcons(buttonArray[x][y], x, y);
                                    }
                                    else if(tiles[x][y].isBomb())
                                    {
                                        if(currentTheme.equals("Classic"))
                                            buttonArray[x][y].setIcon(scaledImage(clickedBomb));
                                        else if(currentTheme.equals("Pirate"))
                                            buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/clickedbomb.png")));    
                                        else if(currentTheme.equals("Christmas"))
                                        {
                                            buttonArray[x][y].setBackground(Color.GREEN);
                                            buttonArray[x][y].setIcon(scaledImage(bell));
                                        }
                                        revealAllMines(x, y);
                                    }
                                    else 
                                    {
                                        if(currentTheme.equals("Classic"))
                                            buttonArray[x][y].setIcon(scaledImage(empty));
                                        else if(currentTheme.equals("Christmas"))
                                            buttonArray[x][y].setBackground(Color.GRAY);
                                        else if(currentTheme.equals("Pirate"))
                                            buttonArray[x][y].setIcon(scaledImage(new ImageIcon("./Sprites/Pirate/empty.png")));    
                                        revealNeighbors(x, y);
                                    }
                                }
                            }
                    }

                }
                }
            }
            if(clickCount==regTiles)
            {
                gameOver = true;
            }
        }
        else
        {
            for(int x=0;x<buttonArray.length;x++)
            {
                for(int y=0;y<buttonArray[0].length;y++)
                {
                    buttonArray[x][y].setDisabledIcon(buttonArray[x][y].getIcon());
                    buttonArray[x][y].setEnabled(false);
                }
            }
        }
    }
    public void mousePressed(MouseEvent e) 
    {
    }
    public void mouseClicked(MouseEvent e) 
    {
    }
    public void mouseEntered(MouseEvent e) 
    {
    }
    public void mouseExited(MouseEvent e) 
    {
    }
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == beginner)
        {
            frame.setVisible(false);
            new Game(new Grid(9, 9, 10, currentTheme)).start();
        }
        if(e.getSource() == intermediate)
        {
            frame.setVisible(false);
            new Game(new Grid(16, 16, 40, currentTheme)).start();
        }
        if(e.getSource() == expert)
        {
            frame.setVisible(false);
            new Game(new Grid(16, 30, 99, currentTheme)).start();
        }
        if(e.getSource()==resetButton)
        {
            int w = grid.getWidth();
            int h = grid.getHeight();
            frame.setVisible(false);
            new Game(new Grid(w, h, mines, currentTheme)).start();
        }
        if(e.getSource()==classic)
        {
            int w = grid.getWidth();
            int h = grid.getHeight();
            frame.setVisible(false);
            new Game(new Grid(w, h, mines, "Classic")).start();
        }
        if(e.getSource()==christmas)
        {
            int w = grid.getWidth();
            int h = grid.getHeight();
            frame.setVisible(false);
            new Game(new Grid(w, h, mines, "Christmas")).start();
        }
        if(e.getSource()==pirate)
        {
            int w = grid.getWidth();
            int h = grid.getHeight();
            frame.setVisible(false);
            new Game(new Grid(w, h, mines, "Pirate")).start();
        }
    }
    class UpdateTimer extends TimerTask 
    {
        public void run() 
        {
            if(!gameOver)
            {
                timePassed++;
                timeLabel.setText("Time Passed: " + timePassed);
            }
        }
    }
}