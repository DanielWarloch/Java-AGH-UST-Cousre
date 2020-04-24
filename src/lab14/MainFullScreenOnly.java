package lab14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFullScreenOnly extends JFrame {

    private int displayWidth;
    private int displayHeight;

    private GraphicsDevice device;


    public MainFullScreenOnly() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayWidth = dim.width;
        displayHeight = dim.height;


        device = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            this.setUndecorated(true);
            this.setResizable(false);

            device.setFullScreenWindow(this);
        } else {
            this.setSize(displayWidth, displayHeight - 40);
            this.setResizable(true);
        }


        BallWorld ballWorld = new BallWorld(this.getWidth(), this.getHeight());
        this.setContentPane(ballWorld);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                }
            }
        });
        this.setFocusable(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("A World of Balls");
        this.pack();
        this.setVisible(true);
    }

    /**
     * Entry main program
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFullScreenOnly();
            }
        });
    }
}
