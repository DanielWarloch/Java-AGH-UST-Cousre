package lab14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFullScreen extends JFrame implements KeyListener {

    private int displayWidth;
    private int displayHeight;

    private int windowedModeWidth;
    private int windowedModeHeight;

    private GraphicsDevice device;
    private boolean fullScreenMode;


    public MainFullScreen() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayWidth = dim.width;
        displayHeight = dim.height;
        windowedModeWidth = displayWidth;
        windowedModeHeight = displayHeight - 40;


        device = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            this.setUndecorated(true);
            this.setResizable(false);
            //this.setIgnoreRepaint(true);
            device.setFullScreenWindow(this);
            fullScreenMode = true;
        } else {
            this.setSize(windowedModeWidth, windowedModeHeight);
            this.setResizable(true);
            fullScreenMode = false;
        }


        BallWorld ballWorld = new BallWorld(this.getWidth(), this.getHeight());
        this.setContentPane(ballWorld);


        this.addKeyListener(this);
        this.setFocusable(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("A World of Balls");
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_F1:
                fullScreenMode = !fullScreenMode;
                this.setVisible(false);
                if (this.isDisplayable()) this.dispose();
                if (fullScreenMode) {

                    windowedModeWidth = this.getWidth();
                    windowedModeHeight = this.getHeight();
                    if (device.isFullScreenSupported()) {
                        this.setUndecorated(true);
                        this.setResizable(false);
                        device.setFullScreenWindow(this);
                    }
                } else {
                    this.setUndecorated(false);
                    device.setFullScreenWindow(null);
                    this.setSize(windowedModeWidth, windowedModeHeight);
                    this.setResizable(true);
                }
                this.setVisible(true);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFullScreen();
            }
        });
    }
}
