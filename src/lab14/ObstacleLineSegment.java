package lab14;

import java.awt.Color;
import java.awt.Graphics;


public class ObstacleLineSegment {
    int x1, y1;
    int x2, y2;
    Color color;

    /**
     * Constructors
     */
    public ObstacleLineSegment(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public ObstacleLineSegment(int x1, int y1, int x2, int y2) {
        this(x1, y1, x2, y2, Color.YELLOW);
    }


    public void set(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
