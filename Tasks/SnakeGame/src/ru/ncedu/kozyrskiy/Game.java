package ru.ncedu.kozyrskiy;


import java.util.Random;

/**
 * The realisation of the game logic
 * All changes during the gaming process are handled here
 * It provides the <code>Panel</code> with the data to be drawn
 *
 * @see Panel
 */
class Game {

    //Controls the speed of the snake
    static final int DELAY = 120;

    //The quantity of all playing cells: 30x30
    private final int ALL_POINTS = 900;

    //The size of the square cell
    private final int CELL_SIZE = 20;

    //The width and length of the square playing field in cells
    private final int SIZE_IN_CELLS = 30;

    //The X and Y coordinates of the center of the playing field
    private final int CENTER = 320;

    //Arrays contain the coordinates of snake's body parts
    int[] xCoordinates;
    int[] yCoordinates;

    //The X and Y coordinates of the food
    private int foodX;
    private int foodY;

    //The current quantity of the snake's body parts
    private int bodyParts;

    //Controls the game state
    private boolean collided;

    //Shows the current direction of the snake's head
    private Direction currentDirection;



    Game() {
        xCoordinates = new int[ALL_POINTS];
        yCoordinates = new int[ALL_POINTS];
        foodX = CENTER;
        foodY = CENTER;
        xCoordinates[0] = CENTER;
        yCoordinates[0] = CENTER;
        bodyParts = 1;
        collided = false;
        currentDirection = Direction.leftDirection;
    }


    void setNewGame() {
        collided = false;
        bodyParts = 1;
        currentDirection = Direction.leftDirection;
        locateFood();
    }

    /**
     * Increases the number of body parts and relocates the food in case it was eaten
     */
    void checkFood(){
        if (xCoordinates[0] == foodX && yCoordinates[0] == foodY) {
            bodyParts++;
            locateFood();
        }
    }

    /**
     * Moves all body parts of the snake in <code>currentDirection</code> on the <code>CELL_SIZE</code>
     */
    void move(){

        for (int i = bodyParts; i > 0; i--) {
            xCoordinates[i] = xCoordinates[i-1];
            yCoordinates[i] = yCoordinates[i-1];
        }

        switch (currentDirection) {
            case upDirection:
                if (yCoordinates[0] < 30) {
                    yCoordinates[0] = 620;
                }
                yCoordinates[0] -= CELL_SIZE;
                break;
            case leftDirection:
                if (xCoordinates[0] < 30) {
                    xCoordinates[0] = 620;
                }
                xCoordinates[0] -= CELL_SIZE;
                break;
            case downDirection:
                if (yCoordinates[0] >= 600) {
                    yCoordinates[0] = 0;
                }
                yCoordinates[0] += CELL_SIZE;
                break;
            case rightDirection:
                if (xCoordinates[0] >= 600) {
                    xCoordinates[0] = 0;
                }
                xCoordinates[0] += CELL_SIZE;
                break;
        }
    }

    /**
     * Checks whether the snake has collided and changes the <code>collided</code> state respectively
     * @return collided state
     */
    boolean isCollided() {
        for (int i = 1; i < bodyParts; i++)
            if (xCoordinates[0] == xCoordinates[i] && yCoordinates[0] == yCoordinates[i])
                collided = true;

        return collided;
    }

    /**
     * Generates and locates a new food element
     */
    private void locateFood() {
        Random random = new Random();
        foodX = (random.nextInt(SIZE_IN_CELLS) + 1) * CELL_SIZE;
        foodY = (random.nextInt(SIZE_IN_CELLS) + 1) * CELL_SIZE;
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    int getFoodX() {
        return foodX;
    }

    int getFoodY() {
        return foodY;
    }

    int getBodyParts() {
        return bodyParts;
    }
}

/**
 * All possible moving directions
 */
enum Direction { upDirection, leftDirection, downDirection, rightDirection }