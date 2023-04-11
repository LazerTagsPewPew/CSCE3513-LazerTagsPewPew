package teamseven.lasertag;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CountDown2 
{

    //Creating JFrame class object so we can dispose of it in the LaserTag file
    JFrame frame;
    public static void main(String[] args) {
        new CountDown();
    }
    // Countdown function
    public CountDown2() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                //creating the JFrame and setting it's dimensions and properties
                frame = new JFrame("Laser Tag count down");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        //Setting the duration of the timer to 30 seconds
        private Timer timer;
        private long startTime = -1;
        private long duration = 360000;

        private JLabel label;

        public TestPane() {
            setLayout(new GridBagLayout());
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }
                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                    }
                    SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
                    label.setText(df.format(duration - clockTime));
                }
            });
            timer.setInitialDelay(0);

            //Starts the Timer
            timer.start();
            
            //Formating of the numbers
            label = new JLabel("...");
            add(label);
            
            // while(timer.isRunning())
            // {}
            //this.setVisible(false);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

    }

}