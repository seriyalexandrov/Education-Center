package ru.ncedu.kozyrskiy;


import javax.swing.*;

/**
 * Frame settings
 */
class Frame extends JFrame {
    private final int width = 950;
    private final int height = 680;

    Frame() {
        add(new Panel());
        setTitle("Snake");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }
}