/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author chg
 */
public class Main {

    public static void main(String[] args) {
        ImageIcon snake = new ImageIcon(Main.class.getResource("/img/snake.png"));
        JFrame frame = new JFrame();
        frame.add(new Snake());
        frame.setIconImage(snake.getImage());
        frame.setTitle("Snake");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
