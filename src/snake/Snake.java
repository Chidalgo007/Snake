/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author chg
 */
public class Snake extends JPanel implements KeyListener, ActionListener {

    private int gameWidth = 400;
    private int gameHeight = 600;
    private LinkedList<Point> snake = new LinkedList();
    ;
    private Point apple;
    private Timer timer;
    boolean moveDown = true;
    boolean moveUp = false;
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean gameOver = false;
    int vel = 20;
    int size = 20;
    int score = 0;

    public Snake() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 600));
        setFocusable(true); // need to receive the KeyListener
        addKeyListener(this);
        startGame();
    }

    private void startGame() {

        snake.add(new Point(50, 30));
        snake.add(new Point(50, 40));
        snake.add(new Point(50, 50));
        apple = newPoint();

        timer = new Timer(1000 / vel, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        for(int i=0;i<gameHeight/size;i++){
//         g.drawLine(0,i*size,gameWidth,i*size);
//         g.drawLine(i*size,0,i*size,gameHeight); 
//        }
        // background
        for (int i = 0; i < gameHeight / size; i++) {
            g.drawLine(i * size, 0, gameWidth, i * size);
            g.drawLine(0, i * size, i * size, gameHeight);
        }

        // apple
        Ellipse2D.Double appleShappe = new Ellipse2D.Double(apple.x, apple.y, size, size);
        g2d.setColor(Color.RED);
        g2d.fill(appleShappe);
        g2d.draw(appleShappe);

        for (Point p : snake) {
            Ellipse2D.Double snakeShape = new Ellipse2D.Double(p.getX(), p.getY(), size, size);
            g2d.setColor(Color.GREEN);
            g2d.fill(snakeShape);
            g2d.draw(snakeShape);
        }
        //----------- score -------------------------------------------------
        String scoreN = "Score: " + score;// message
        Font Scorefont = new Font("Arial", Font.BOLD, 12);//font type
        FontMetrics ScorefontWidth = g.getFontMetrics(Scorefont);//to get width
        int scoreWidth = ScorefontWidth.stringWidth(scoreN);// get width
        g2d.setFont(Scorefont);
        g2d.drawString(scoreN, (gameWidth - scoreWidth - 10), 20);

        //------------ game over --------------------------------------------
        if (gameOver) {
            timer.stop();
            Rectangle2D go = new Rectangle2D.Double(0, 0, gameWidth, gameHeight);
            g2d.setColor(new Color(0, 0, 0, 70));
            g2d.fill(go);
            g2d.draw(go);
            g2d.setColor(new Color(208, 208, 208));
            // ---------- to center message ---------------------------------
            String labelGO = "GAME OVER";// message
            Font font = new Font("Arial", Font.BOLD, 36);//font type
            FontMetrics fontWidth = g.getFontMetrics(font);//to get width
            int width = fontWidth.stringWidth(labelGO);// get width
            //---------------------------------------------------------------
            g2d.setFont(font);
            g2d.drawString(labelGO, (gameWidth - width) / 2, (gameHeight - 18) / 2);
        }
    }

    private void snakeMove() {
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        int sizeSpace = size - 5;
        if (moveDown) {
            newHead.y += sizeSpace;// this control the space beetween each element
        }
        if (moveUp) {
            newHead.y -= sizeSpace;
        }
        if (moveLeft) {
            newHead.x -= sizeSpace;
        }
        if (moveRight) {
            newHead.x += sizeSpace;
        }
        snake.addFirst(newHead);
        snake.removeLast();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !moveDown) {
            makeItFalse();
            moveUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !moveUp) {
            makeItFalse();
            moveDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !moveRight) {
            makeItFalse();
            moveLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !moveLeft) {
            makeItFalse();
            moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void makeItFalse() {
        moveDown = false;
        moveUp = false;
        moveLeft = false;
        moveRight = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snakeMove();
        checkcollision();
        repaint();
    }

    private void checkcollision() {
        Point head = snake.getFirst();
        if (head.x >= apple.x - size / 2 && head.x <= apple.x + size / 2
                && head.y >= apple.y - size / 2 && head.y <= apple.y + size / 2) {

            snake.addLast(new Point(apple));
            apple.setLocation(newPoint());
            score++;
            vel += 1;//0.06;
        }
        // wall collision
        if (head.x <= 0 || head.x >= this.gameWidth
                || head.y <= 0 || head.y >= this.gameHeight) {
            gameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            Point body = snake.get(i);
            if (head.equals(body)) {
                gameOver = true;
            }
        }
    }

    private Point newPoint() {
        return new Point((int) (Math.random() * (gameWidth - 2 * size)),
                (int) (Math.random() * (gameHeight - 2 * size)));
    }

}
