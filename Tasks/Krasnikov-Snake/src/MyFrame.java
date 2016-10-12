import javax.swing.*;

/**
 * Created by KrasnikovRoman on 05.04.2016.
 */
public class MyFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;
    JFrame frame;
    MyPanel panel;

    public MyFrame() {
        frame = new JFrame("Игра Змейка");
        panel = new MyPanel();
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}