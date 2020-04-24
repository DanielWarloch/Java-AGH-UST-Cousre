package lab14;

import java.awt.Color;
import java.awt.Graphics;


public class ObstaclePolygon {
    int[] xPoints;
    int[] yPoints;
    Color color;


    public ObstaclePolygon(int[] xPoints, int[] yPoints, Color color) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.color = color;
    }


    public ObstaclePolygon(int[] xPoints, int[] yPoints, int numPoints) {
        this(xPoints, yPoints, Color.YELLOW);
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}
