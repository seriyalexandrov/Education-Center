package ru.ncedu.kozyrskiy;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The representation of the playing field.
 * All graphics, timer settings and listeners are handled here.
 * The data to be drawn is received from <code>Game</code>
 *
 * @see Game
 */
class Panel extends JPanel implements ActionListener {

    //The size of square cells
    private final int CELL_SIZE = 20;

    private Image background;
    private Image headUp;
    private Image headLeft;
    private Image headDown;
    private Image headRight;
    private Image bodyHor;
    private Image bodyVert;
    private Image food;
    private Image gameOver;

    private JButton beginButton;
    private JButton exitButton;

    private JLabel scoreLabel;
    private int score = 0;

    //This timer controls the frequency of checking the state of the game and repainting the panel
    private Timer timerDraw = null;

    //For drawing the field
    private Graphics2D graphics2D;
    private boolean collision;
    private Game game;


    Panel() {
        addKeyListener(new Key());
        game = new Game();
        collision = false;
        setFocusable(true);
        setLayout(new GridBagLayout());
        loadImages();
        setExitButton();
        setNewGameButton();
        setScoreLabel();
    }

    /**
     * This method is used in <code>beginButton</code> to set a new game
     */
    private void startNewGame() {
        collision = false;
        scoreUpdate(getGraphics());
        removeAll();
        setExitButton();
        setNewGameButton();
        setScoreLabel();
        revalidate();
        if (timerDraw != null)
            timerDraw.stop();
        game = new Game();
        game.setNewGame();
        timerDraw = new Timer(Game.DELAY, this);
        timerDraw.start();
    }

    /**
     * The state of the playing field is checked and according to <code>collision</code> state actions are done
     * Timer event recurs every <code>Game.DELAY</code> milliseconds
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!collision) {
            game.checkFood();
            collision = game.isCollided();
            game.move();
        }
        repaint();

        if (collision)
            timerDraw.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        drawField(g);
        draw(g);
    }

    /**
     * The main drawing method.
     * Draws all elements according to <code>collision</code> state
     *
     * @param g
     */
    private void draw(Graphics g) {

        if(!collision) {

            g.drawImage(food, game.getFoodX(), game.getFoodY(), CELL_SIZE - 1, CELL_SIZE - 1, this);


            Direction curDir = game.getCurrentDirection();
            for (int i = 0; i < game.getBodyParts(); i++) {
                if (i != 0) {
                    if (curDir == Direction.leftDirection || curDir == Direction.rightDirection)
                        g.drawImage(bodyHor, game.xCoordinates[i], game.yCoordinates[i], CELL_SIZE, CELL_SIZE, this);
                    else
                        g.drawImage(bodyVert, game.xCoordinates[i], game.yCoordinates[i], CELL_SIZE, CELL_SIZE, this);
                }
                else {
                    switch (curDir) {
                        case upDirection: g.drawImage(headUp, game.xCoordinates[0], game.yCoordinates[0], CELL_SIZE, CELL_SIZE, this);
                            break;
                        case leftDirection: g.drawImage(headLeft, game.xCoordinates[0], game.yCoordinates[0], CELL_SIZE, CELL_SIZE, this);
                            break;
                        case downDirection:  g.drawImage(headDown, game.xCoordinates[0], game.yCoordinates[0], CELL_SIZE, CELL_SIZE, this);
                            break;
                        case rightDirection:  g.drawImage(headRight, game.xCoordinates[0], game.yCoordinates[0], CELL_SIZE, CELL_SIZE, this);
                            break;
                    }
                }
            }

            scoreUpdate(g);
            Toolkit.getDefaultToolkit().sync();
        }
        else
            gameOver(g);
    }

    /**
     * Updates a score label according to data received from <code>Game</code>
     *
     * @param g
     */
    private void scoreUpdate(Graphics g) {
        score = (game.getBodyParts() - 1) * 10;
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Draws a game over text and a picture
     *
     * @param g
     */
    private void gameOver(Graphics g) {
        g.drawImage(gameOver, 20, 70, 600, 400, this);
        Font font = new Font("Helvetica", Font.BOLD, 58);
        g.setColor(new Color(220, 10, 10));
        g.setFont(font);
        g.drawString("Game Over! :(", 130, 133);
    }


    /**
     * Draws a playing field 30x30 squares
     *
     * @param g
     */
    private void drawField(Graphics g) {
        graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(1));

        for(int i = 40; i < 620; i += CELL_SIZE)
        {
            graphics2D.drawLine(i, 20, i, 620);

            for(int j = 40; j < 620; j += CELL_SIZE)
            {
                graphics2D.drawLine(20, j, 620, j);
            }
        }
    }


    private void loadImages(){
        background = new ImageIcon("bg.jpg").getImage();
        headUp = new ImageIcon("headUp.png").getImage();
        headLeft = new ImageIcon("headLeft.png").getImage();
        headDown = new ImageIcon("headDown.png").getImage();
        headRight = new ImageIcon("headRight.png").getImage();
        bodyHor = new ImageIcon("bodyHor.png").getImage();
        bodyVert = new ImageIcon("bodyVert.png").getImage();
        food = new ImageIcon("apple.png").getImage();
        gameOver = new ImageIcon("gameover.png").getImage();
    }


    private void setExitButton(){
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Times New Roman", Font.BOLD, 26));
        exitButton.setForeground(Color.red);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(exitButton, new GridBagConstraints(0, 0, 0, 0, 1, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(110, 685, 500, 65), 0, 0));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    private void setNewGameButton(){
        beginButton = new JButton("New Game");
        beginButton.setFont(new Font("Times New Roman", Font.BOLD, 26));
        beginButton.setForeground(Color.blue);
        beginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(beginButton, new GridBagConstraints(0, 0, 0, 0, 1, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 685, 520, 65), 0, 0));
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
    }


    private void setScoreLabel(){
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 55));
        add(scoreLabel, new GridBagConstraints(0, 0, 0, 0, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(220, 650, 430, 0), 0, 0));
    }


    /**
     * Key events handler
     */
    private class Key extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Direction curDir = game.getCurrentDirection();

            if (key == KeyEvent.VK_UP && curDir != Direction.downDirection)
                game.setCurrentDirection(Direction.upDirection);
            if (key == KeyEvent.VK_LEFT && curDir != Direction.rightDirection)
                game.setCurrentDirection(Direction.leftDirection);
            if (key == KeyEvent.VK_DOWN && curDir != Direction.upDirection)
                game.setCurrentDirection(Direction.downDirection);
            if (key == KeyEvent.VK_RIGHT && curDir != Direction.leftDirection)
                game.setCurrentDirection(Direction.rightDirection);
        }
    }
}