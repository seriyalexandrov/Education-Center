/**
 * Created by KrasnikovRoman on 05.04.2016.
 *
 * 0 - free space in the game field
 * 1 - head of the snake
 * -1 - object for eating
 * 2, 3, ... - body of the snake
 */
public class Game {
    private static final int SIZE_OF_FIELD_X = 30, SIZE_OF_FIELD_Y = 30;
    public int array[][];
    public int direction;
    public int newDirection;
    public int score;
    public boolean endOfGame;
    private int headX, headY;
    private int length;

    public Game() {
        array = new int[SIZE_OF_FIELD_Y][SIZE_OF_FIELD_X];
    }

    private void createObjectForEating() {
        while (true) {
            int x = (int)(Math.random()*SIZE_OF_FIELD_X);
            int y = (int)(Math.random()*SIZE_OF_FIELD_Y);

            if (array[y][x] == 0) {
                array[y][x] = -1;
                break;
            }
        }
    }

    public void start() {
        for (int i = 0; i < SIZE_OF_FIELD_Y; i++) {
            for (int j = 0; j < SIZE_OF_FIELD_X; j++) {
                array[i][j] = 0;
            }
        }
        headX = headY = 15;
        length = 1;
        endOfGame = false;
        direction = 0;
        score = 0;
        array[15][15] = 1;

        createObjectForEating();
    }


    private void turn() {
        if (Math.abs(newDirection - direction) != 2) {
            direction = newDirection;
        }
    }

    public int moveOfHead() {
        int result = 0;
        switch (direction) {
            case 0:
                if ((headX - 1) >= 0)
                    headX--;
                else
                    headX = 29;
                break;
            case 1:
                if ((headY - 1) >= 0)
                    headY--;
                else
                    headY = 29;
                break;
            case 2:
                if ((headX + 1) <= 29)
                    headX++;
                else
                    headX = 0;
                break;
            case 3:
                if ((headY + 1) <= 29)
                    headY++;
                else
                    headY = 0;
                break;
        }

        if (array[headY][headX] == -1) {
            result = 1; // если объект съеден
        }
        else if (array[headY][headX] == 0) {
            result = 2;  // если перемещение в пустое поле
        }
        else if (array[headY][headX] > 0 ) {
            result = 3;  // если змейка съела себя
        }

        array[headY][headX] = -2;
        return result;
    }

    public void move() {
        int flag = moveOfHead();

        if (flag == 3) // если змейка съела себя
            endOfGame = true;

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (array[i][j] > 0)
                    array[i][j]++;
                else if (array[i][j] == -2)
                    array[i][j] = 1;

                if (flag != 1) { // если объект не был съеден, то к хвосту добавляем 0
                    if (array[i][j] == (length + 1))
                        array[i][j] = 0;
                }
            }
        }

        if (flag == 1) {
            length++;
            createObjectForEating();
            score += 10;
        }

        turn();
    }
}