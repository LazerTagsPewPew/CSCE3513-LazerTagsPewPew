/*
 * sample code to draw text,
 * since the data on the scoreboard moves I was thinking
 * we can draw the text at a specific location and when another score happens which is hit
 * in the demo. We increment the position of that text by 1. So maybe we will have to use a class for
 * this task store the objects in an array and then when the score is added we traverse the array and add one to the y value.
 */
package teamseven.lasertag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MovingText extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private String text = "Hello World!";
    private int x = 0;
    private int y = 50;
    private int dx = 1;
    private Timer timer;

    public MovingText() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += dx;
        if (x > getWidth() - 100 || x < 0) {
            dx = -dx;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLUE);
        g2d.drawString(text, x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Text");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MovingText());
        frame.pack();
        frame.setVisible(true);
    }
}
