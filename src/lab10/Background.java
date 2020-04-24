package lab10;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Background implements XmasShape {

    private Image img;
    private JPanel panel;

    public Background(JPanel panel){
        this.panel = panel;

        img = null;
        try {
            img = ImageIO.read(new File("src/lab10/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void transform(Graphics2D g2d) {

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(img,0,0,panel.getWidth(),panel.getHeight(),panel);

    }
}
