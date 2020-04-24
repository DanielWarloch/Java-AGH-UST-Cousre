package lab14;

import java.awt.Color;
import java.awt.Graphics;


public class ObstacleCircle {
    int centerX, centerY, radius;
    Color color;


    public ObstacleCircle(int centerX, int centerY, int radius, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = color;
    }

    public ObstacleCircle(int centerX, int centerY, int radius) {
        this(centerX, centerY, radius, Color.YELLOW);
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius - 1, 2 * radius - 1);
    }
}
