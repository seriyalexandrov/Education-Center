package ru.ncedu.grishkova.snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



/**
 * The class of game logic. It can  give information about the current state of game
 * (lose or win, current score and speed) and condition of field.
 * It calculates and updates one step of snake moving.
 * Uses two enums: {@link ru.ncedu.grishkova.snake.Game.Directions}, which represents
 * 4 directions of moving snake, and {@link ru.ncedu.grishkova.snake.Game.CellType}
 * which represents the contents of field cells.
 */
public class Game {
    /**
     * Constructs an instance of game with empty field, one-size snake's body (only head)
     * and one food in defined places
     */
    public Game() {
        bodyLocation.add(new Integer[]{startPositionX, startPositionY});
        initField();
        setFoodOnField();
    }

    /**
     * The main method to calculate next state of game
     * @param newCommands list of commands received by {@link MainPanel#redrawTimer} in current
     *                    step represented as enum Directions
     */
    public void calcNextStep(List<Directions> newCommands) {
        updateDirection(newCommands);
        moveSnake();
    }
    //

    /**
     * Collects commands and sets new direction of snake in {@link #direction}.
     * Ignores commands to change direction in opposite and to keep moving in current direction
     * If method gets no appropriate command, direction will not changed
     * @param newCommands list of commands received by {@link MainPanel#redrawTimer} in current
     *                    step represented as enum Directions
     */
    private void updateDirection(List<Directions> newCommands) {
        directionsList.addAll(newCommands);
        Game.Directions newDir;
        while (directionsList.size() != 0) {
            newDir = directionsList.remove(0);
            if (!newDir.isOpposite(direction) && !newDir.equals(direction)) {
                direction = newDir;
                break;
            }
        }
    }

    /**
     * Calculates next location of snake and update {@link #field}, {@link #bodyLocation}
     * and state of game ({@link #score}, {@link #isWin} or {@link #isGameOver})
     * accordingly to game logic
     */
    private void moveSnake() {
        Integer currentCellX = bodyLocation.get(0)[0];
        Integer currentCellY = bodyLocation.get(0)[1];
        field[currentCellX][currentCellY] = CellType.BODY;
        Integer[] nextCellCoords = calcNextCellCoords(currentCellX, currentCellY);
        Integer nextCellX = nextCellCoords[0];
        Integer nextCellY = nextCellCoords[1];
        CellType nextCellType = field[nextCellX][nextCellY];
        bodyLocation.add(0, nextCellCoords);
        if (nextCellType == CellType.EMPTY || nextCellType == CellType.BODY) {
            int cellToFreeX = bodyLocation.get(bodyLocation.size()-1)[0];
            int cellToFreeY = bodyLocation.get(bodyLocation.size()-1)[1];
            field[cellToFreeX][cellToFreeY] = CellType.EMPTY;
            bodyLocation.remove(bodyLocation.size() - 1);
            if ( nextCellType == CellType.BODY) {
                isGameOver = true;
            }
        }
        else if (nextCellType == CellType.FOOD) {
            score += 10;
            if (bodyLocation.size() == field.length * field[0].length) {
                isWin = true;
            }
            setFoodOnField();
        }
        field[nextCellX][nextCellY] = CellType.HEAD;
    }

    /**
     * Calculates next position of snake head relative to current position and direction.
     * @param currentCellX current position of snake head
     * @param currentCellY current position of snake head
     * @return next position of snake head
     */

    private Integer[] calcNextCellCoords(Integer currentCellX, Integer currentCellY) {
        Integer nextCellX = currentCellX;
        Integer nextCellY = currentCellY;
        switch(direction) {
            case UP:
                nextCellY = (nextCellY - 1 + MainPanel.getHeightOfGrid()) % MainPanel.getHeightOfGrid();
                break;
            case RIGHT:
                nextCellX = (nextCellX + 1 + MainPanel.getWidthOfGrid()) % MainPanel.getWidthOfGrid();
                break;
            case DOWN:
                nextCellY = (nextCellY + 1 + MainPanel.getHeightOfGrid()) % MainPanel.getHeightOfGrid();
                break;
            case LEFT:
                nextCellX = (nextCellX - 1 + MainPanel.getWidthOfGrid()) % MainPanel.getWidthOfGrid();
                break;
            default:
                break;
        }
        return new Integer[]{nextCellX, nextCellY};
    }

    /**
     * Calculates random position in free space that has no snake body or another food
     * and set new food in this position.
     * Also it signals to {@link MainPanel} about this change by means of {@link #changeFoodIcon}
     * to change image for new food
     */
    private void setFoodOnField() {
        List<Integer[]> freeCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                if (field[i][j] == CellType.EMPTY) {
                    freeCells.add(new Integer[]{i,j});
                }
            }
        }
        if (freeCells.size() > 0) {
            Random rnd = new Random();
            int i = rnd.nextInt(freeCells.size());
            int x = freeCells.get(i)[0];
            int y = freeCells.get(i)[1];
            field[x][y] = CellType.FOOD;
            changeFoodIcon = true;
        }
    }

    /**
     * Initialises empty {@link #field} and put head of snake in start position
     * ({@link #startPositionX} and {@link #startPositionY})
     */
    private  void initField(){
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                field[i][j] = CellType.EMPTY;
            }
        }
        field[startPositionX][startPositionY] = CellType.HEAD;
    }
    //some getters and setters below
    public boolean isChangeFoodIcon() {
        return changeFoodIcon;
    }

    public void setChangeFoodIcon(boolean changeFoodIcon) {
        this.changeFoodIcon = changeFoodIcon;
    }
    public  int getScore() {
        return score;
    }

    public int getSpeed() {
        return speed;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public boolean isWin() {
        return isWin;
    }
    public CellType[][] getField() {
        return field;
    }
    /**
     * Represents 4 directions of moving snake. Has methods to calculate opposite direction and to check opposition of current and new directions
     */
    public enum Directions {UP, RIGHT, DOWN, LEFT;
        public Directions opposite() {
            switch (this) {
                case UP:    return DOWN;
                case DOWN:  return UP;
                case LEFT:  return RIGHT;
                case RIGHT: return LEFT;
            }
            return this;
        }
        public boolean isOpposite (Directions dir) {
            return this.opposite().equals(dir);
        }
    }
    public enum CellType { EMPTY, HEAD, BODY, FOOD }

    private  CellType[][] field = new CellType[MainPanel.getHeightOfGrid()][MainPanel.getWidthOfGrid()];
    private List<Directions> directionsList = new ArrayList<>();
    private Directions direction = Directions.LEFT;
    private  List<Integer[]> bodyLocation = new LinkedList<>();
    private int startPositionX = field.length/2;
    private int startPositionY = field[startPositionX].length/2;
    private boolean isGameOver = false;
    private boolean isWin = false;
    private boolean changeFoodIcon;
    private  int score = 0;
    private int speed = 250;
}
