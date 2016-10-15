package ru.ncedu.kozyrskiy;

import javax.swing.*;

/**
 * Start point of the application.
 *
 * @see Frame
 * @author Nikolay Kozyrskiy
 */
public class Snake {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame().setVisible(true);
            }
        });

    }
}