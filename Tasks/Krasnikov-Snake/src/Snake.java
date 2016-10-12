import javax.swing.*;

/**
 * Created by KrasnikovRoman on 05.04.2016.
 */
public class Snake {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        });
    }
}