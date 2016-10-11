package ru.ncedu.grishkova.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


/**
 * The class is implementation of KeyAdapter. Contains the list of given commands
 * and has methods to work with commands and collect them.
 * Uses {@link ru.ncedu.grishkova.snake.Game.Directions} to represent pressed keys.
 */
public class MyKeyListener extends KeyAdapter {
    private List<Game.Directions> commands = new LinkedList<>();
    public List<Game.Directions> getCommands() {
        return commands;
    }
    public void restartCommandsCollecting() {
        commands.clear();
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_UP:
                commands.add(Game.Directions.UP);
                break;
            case KeyEvent.VK_LEFT:
                commands.add(Game.Directions.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                commands.add(Game.Directions.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                commands.add(Game.Directions.DOWN);
                break;
            default:
                break;
        }
    }
}
