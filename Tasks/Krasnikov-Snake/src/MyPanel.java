import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by KrasnikovRoman on 05.04.2016.
 */
public class MyPanel extends JPanel implements ActionListener {
    public static final int WIDTHBUTTON = 120;
    public static final int HEIGHTBUTTON = 50;
    public static final int SCALE = 20;
    private Image backGround, body, head, apple, endOfGame;
    private Game game;
    private Timer tmDraw, tmUpdate;
    private MyPanel panel;
    JButton exit, newGame;
    JLabel score;

    MyPanel() {
        panel = this;
        try {
            backGround = ImageIO.read(new File("C:\\Users\\KrasnikovRoman\\Desktop\\Java\\newSnake\\img\\BackGround.png"));
            body = ImageIO.read(new File("C:\\Users\\KrasnikovRoman\\Desktop\\Java\\newSnake\\img\\BodySnake.png"));
            head = ImageIO.read(new File("C:\\Users\\KrasnikovRoman\\Desktop\\Java\\newSnake\\img\\HeadSnake.png"));
            apple = ImageIO.read(new File("C:\\Users\\KrasnikovRoman\\Desktop\\Java\\newSnake\\img\\Apple.png"));
            endOfGame = ImageIO.read(new File("C:\\Users\\KrasnikovRoman\\Desktop\\Java\\newSnake\\img\\GameOver.png"));
        }
        catch (Exception e) {}

        game = new Game();
        game.start();
        tmDraw = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        tmDraw.start();

        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        tmUpdate = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.endOfGame == false) {
                    game.move();
                }
                score.setText("Счёт: " + game.score);
            }
        });
        tmUpdate.start();

        createButtons();
        createLabel("Cчёт: 0");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backGround, 0, 0, 800, 650, null);

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (game.array[i][j] != 0) {
                    if (game.array[i][j] == 1) {
                        g.drawImage(head, 10 + j*SCALE, 10 + i*SCALE, SCALE, SCALE, null);
                    }
                    else if (game.array[i][j] == -1) {
                        g.drawImage(apple, 10 + j*SCALE, 10 + i*SCALE, SCALE, SCALE, null);
                    }
                    else if (game.array[i][j] >= 2) {
                        g.drawImage(body, 10 + j*SCALE, 10 + i*SCALE, SCALE, SCALE, null);
                    }
                }
            }
        }

        g.setColor(Color.ORANGE);
        for (int i = 0; i <= 30*SCALE + 10; i+= SCALE) {
            g.drawLine(10 + i, 10, 10 + i, 30*SCALE + 10);
            g.drawLine(10, 10 + i, 30*SCALE + 10, 10 + i);
        }

        if (game.endOfGame == true) {
            g.drawImage(endOfGame, 170, 210, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Выход")) {
            System.exit(0);
        }
        else if (e.getActionCommand().equals("Новая игра")) {
            game.start();
            exit.setFocusable(false);
            newGame.setFocusable(false);
            panel.setFocusable(true);
        }
    }

    private void createButtons() {
        setLayout(null);
        exit = new JButton("Выход");
        newGame = new JButton("Новая игра");
        newGame.setBounds(655, 30, WIDTHBUTTON, HEIGHTBUTTON);
        exit.setBounds(655, 110, WIDTHBUTTON, HEIGHTBUTTON);
        exit.setFont(new Font(null, Font.BOLD, 20));
        newGame.setFont(new Font(null, Font.BOLD, 15));
        exit.addActionListener(this);
        newGame.addActionListener(this);
        this.add(exit);
        this.add(newGame);
    }

    private void createLabel(String nameLabel) {
        setLayout(null);
        score = new JLabel(nameLabel);
        score.setBounds(655, 180, 100, 20);
        Font font = new Font(null, Font.BOLD, 16);
        score.setFont(font);
        this.add(score);
    }

    public class MyKey implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    game.newDirection = 0;
                    break;
                case KeyEvent.VK_UP:
                    game.newDirection = 1;
                    break;
                case KeyEvent.VK_RIGHT:
                    game.newDirection = 2;
                    break;
                case KeyEvent.VK_DOWN:
                    game.newDirection = 3;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}