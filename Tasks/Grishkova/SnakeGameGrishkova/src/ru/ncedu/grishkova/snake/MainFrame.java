package ru.ncedu.grishkova.snake;

/**
 * The class creates the frame in which game field located.
 * Also it contains all parameters of the frame (sizes, head).
 * Has an instanse of {@link ru.ncedu.grishkova.snake.MainPanel}
 */

import javax.swing.*;

public class MainFrame extends JFrame{
    public MainFrame() {
        super("Game SnakeMain");
        setSize(widthOfFrame, heightOfFrame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        MainPanel panel = new MainPanel();
        setContentPane(panel);
    }
    private static int widthOfFrame = 800;
    private static int heightOfFrame = 650;

    public static int getHeightOfFrame() {
        return heightOfFrame;
    }
    public static int getWidthOfFrame() {
        return widthOfFrame;
    }



}
