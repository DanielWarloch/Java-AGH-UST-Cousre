package lab10;

import java.awt.*;

public class Bauble implements XmasShape{
    private int x;
    private int y;
    private double scale;
    private Color lineColor;
    private Color fillColor;

    public Bauble(int x, int y, double scale, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.fillOval(0,0,100,100);
        g2d.setColor(lineColor);
        g2d.drawOval(0,0,100,100);
    }
}
