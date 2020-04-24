package lab14;

import javax.swing.JApplet;

public class MainApplet extends JApplet {
    @Override
    public void init() {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setContentPane(new BallWorld(800, 550));
            }
        });
    }
}
