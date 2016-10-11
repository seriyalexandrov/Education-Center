package ru.ncedu.grishkova.snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class creates a panel, which contains and paints all elements of game
 * (buttons, labels, images, grid, etc).It located in the {@link MainFrame}.
 * An instance of MainPanel processes pressed keys and run movement of snake in the
 * {@link Game} class with certain pace using {@link #redrawTimer}.
 * The class contains the instance of class Game, which provides information about game field
 */
public class MainPanel extends JPanel{
    /**
     * Constructs panel containing necessary buttons, label and loading images from file
     */
    public MainPanel() {
        createExitButton();
        createNewGameButton();
        createScoreLabel();
        loadImagesFromFile();
        placeComponents();
        setFocusable(true);
        setKeyListenerToAllComponents();
    }
    //This method allows to all components to be focusable on {@link #myKeyListener}
    private void setKeyListenerToAllComponents() {
        addKeyListener(myKeyListener);
        Component[] components = getComponents();
        for (Component component : components) {
            component.addKeyListener(myKeyListener);
        }
    }

    /**
     *Places buttons and label on panel using {@link BoxLayout} to default placement
     */
    public void placeComponents () {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add empty space (fake component) for better appearance
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(exitButton);
        add(Box.createRigidArea(new Dimension(0,50)));
        add(newGameButton);
        add(Box.createRigidArea(new Dimension(0,50)));
        add(scoreLabel);
        //correction of components' alignment
        JComponent glue = (JComponent)Box.createHorizontalGlue();
        glue.setMinimumSize(new Dimension(400, 10));
        glue.setAlignmentX(1.0f);
        add(glue);
    }


    /**
     * Returns random image of food
     * @return random element of {@link #foodImages}
     */
    public Image getNextFoodImage() {
        Random rnd = new Random();
        int i = rnd.nextInt(foodImages.size());
        return foodImages.get(i);
    }
    private void createExitButton () {
        exitButton = new JButton("Выход");
        exitButton.setAlignmentX(0.5f);
        exitButton.setMaximumSize(new Dimension(100, 50));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
            }
        });
    }
    private void createNewGameButton() {
        newGameButton = new JButton("Новая игра");
        newGameButton.setMaximumSize(new Dimension(100, 50));
        newGameButton.setAlignmentX(0.5f);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myKeyListener.restartCommandsCollecting();
                //checks necessity to create new Game instance
                if (!isFirstRun) {
                    game = new Game();
                } else {
                    isFirstRun = false;
                }
                redrawTimer.restart();
            }
        });
    }
    private void createScoreLabel() {
        scoreLabel = new JLabel("   Счет: " + game.getScore()+ "   ");
        scoreLabel.setAlignmentX(0.5f);
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
    }
    /**
     * Loads images from files mentioned in parameters , using different scaling.
     * If not successful, exits program with state 1.
     * Sets current food image to {@link #currentFoodImage}.
     */
    public  void loadImagesFromFile() {
        try {
            backgroundImage = ImageIO.read(new File(backgroundPath)).getScaledInstance(MainFrame.getWidthOfFrame(), MainFrame.getHeightOfFrame(), Image.SCALE_DEFAULT);
            snakeHeadImage = ImageIO.read(new File(snakeHeadPath)).getScaledInstance(scaleOfGrid, scaleOfGrid, Image.SCALE_DEFAULT);
            snakeBodyImage = ImageIO.read(new File(snakeBodyPath)).getScaledInstance(scaleOfGrid, scaleOfGrid, Image.SCALE_DEFAULT);
            gameOverImage = ImageIO.read(new File(gameOverPath)).getScaledInstance(600,600, Image.SCALE_DEFAULT);
            gameEndWinImage = ImageIO.read(new File(gameEndWinPath)).getScaledInstance(600,600, Image.SCALE_DEFAULT);
            File foodDirectory = new File(foodPath);
            File[] foodIcons ;
            foodImages = new ArrayList<>();
            if (foodDirectory.isDirectory() && (foodIcons = foodDirectory.listFiles())!=null) {
                for (File file : foodIcons) {
                    Image image = ImageIO.read(file).getScaledInstance(scaleOfGrid, scaleOfGrid, Image.SCALE_DEFAULT);
                    foodImages.add(image);
                }
            }
            currentFoodImage = getNextFoodImage();
        } catch (IOException e) {
            System.out.println("Exception: can't load Images from file. Setup:");
            System.out.println( backgroundPath + ",");
            System.out.println(gameEndWinPath + ",");
            System.out.println(gameOverPath + ",");
            System.out.println(snakeBodyPath + ",");
            System.out.println(snakeHeadPath);
            System.out.println("in directory " );
            System.out.println(imagesDirectory);
            System.out.println("and some food icons in ");
            System.out.println(foodPath);
            System.exit(1);
        }
    }

    /**
     * Paints all needed images and grid.
     * @param g
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0,0,null);
        paintGrid(g);
        paintIcons(g);
        if (game.isGameOver()) {
            g.drawImage(makeImgTransparent(gameOverImage), indent,indent,null);
        }
        if (game.isWin()) {
            g.drawImage(makeImgTransparent(gameEndWinImage), indent,indent,null);
        }
    }

    /**
     * Returns transparent images for better appearance
     * @param imgSrc source image
     * @return transparent image
     */
    public Image makeImgTransparent(Image imgSrc) {
        ImageProducer ip = new FilteredImageSource(imgSrc.getSource(), new TransparentImageFilter());
        Image imTransparent = createImage(ip);
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(imTransparent, 0);
        try {
            mt.waitForAll();
        } catch(InterruptedException ie) {
            return null;
        }
        return imTransparent;
    }
    /**
     * Uses Game instance to get field and paints images of food and snake in necessary places.
     * Checks necessity of changing current food image
     * @param g
     */
    private void paintIcons(Graphics g) {
        for (int i = 0; i < game.getField().length; i++){
            for (int j = 0; j < game.getField()[i].length; j++){
                int xToDraw = indent + i* scaleOfGrid;
                int yToDraw = indent + j* scaleOfGrid;
                switch(game.getField()[i][j]) {
                    case BODY:
                        g.drawImage(snakeBodyImage, xToDraw,yToDraw,null);
                        break;
                    case HEAD:
                        g.drawImage(snakeHeadImage, xToDraw,yToDraw,null);
                        break;
                    case FOOD:
                        if (game.isChangeFoodIcon()) {
                            currentFoodImage = getNextFoodImage();
                            game.setChangeFoodIcon(false);
                        }
                        g.drawImage(currentFoodImage, xToDraw,yToDraw,null);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Paints lines that form grid
     * @param g
     */
    private void paintGrid(Graphics g) {
        for (int i = 0; i <= widthOfGrid; i++) {
            g.drawLine(indent+ scaleOfGrid *i, indent,indent+ scaleOfGrid *i, indent+ heightOfGrid * scaleOfGrid);
        }
        for (int i = 0; i <= heightOfGrid; i++) {
            g.drawLine(indent, indent+ scaleOfGrid *i, indent+ widthOfGrid * scaleOfGrid,indent+ scaleOfGrid *i);
        }
    }
    //some getters below
    public static int getHeightOfGrid() {
        return heightOfGrid;
    }

    public static int getWidthOfGrid() {
        return widthOfGrid;
    }
    //  size parameters
    private static int scaleOfGrid = 20;
    private static int widthOfGrid = 30;
    private static int heightOfGrid = 30 ;
    // indent from border of frame
    private static int indent = 5;

    //paths of all used images
    private  String imagesDirectory = new File("").getAbsolutePath() + "\\src\\ru\\ncedu\\grishkova\\snake\\images\\";
    private String backgroundPath = imagesDirectory +"background.jpg";
    private String snakeHeadPath = imagesDirectory +"snakeHead.png";
    private String snakeBodyPath = imagesDirectory +"snakeBody.png";
    private String gameEndWinPath = imagesDirectory +"youWin.jpg";
    private String gameOverPath = imagesDirectory +"gameOver.jpg";
    private String foodPath = imagesDirectory +"foodImages";
    /**
     * Game instance to know field condition and game state
     */
    private  Game game = new Game();

    private JButton exitButton;
    private JButton newGameButton;
    private JLabel scoreLabel;
    private Image backgroundImage;
    private Image gameOverImage;
    private Image gameEndWinImage;
    private Image snakeHeadImage;
    private Image snakeBodyImage;
    private Image currentFoodImage;
    private List<Image> foodImages;
    private boolean isFirstRun = true;
    /**
     * Timer processes pressed keys and run movement of snake in Game class with certain pace
     */
    private Timer redrawTimer = new Timer(game.getSpeed(), new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.isGameOver() && !game.isWin()) {
                game.calcNextStep(myKeyListener.getCommands());
                myKeyListener.restartCommandsCollecting();
                scoreLabel.setText("Счет: " + game.getScore());
                repaint();
            }
        }
    });
    private MyKeyListener myKeyListener = new MyKeyListener();

}
