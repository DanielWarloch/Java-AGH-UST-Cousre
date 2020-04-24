package lab10;

import java.awt.*;

public class Gift implements XmasShape{
    private int y;
    private int x;
    private final int width;
    private final int height;
    private int arcWidth = 20;
    private int arcHeight = 20;
    private Color color;
    private double scale = 1.0;
    private double rotation = 0.0;


    public Gift(int x, int y,int width,int height,Color color) {
        this.y = y;
        this.x = x;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public Gift(int x, int y,int width,int height,Color color,double rotation) {
        this.y = y;
        this.x = x;
        this.width = width;
        this.height = height;
        this.color = color;
        this.rotation = rotation;
    }
    public Gift(int x, int y,int width,int height,Color color,int arcWidth,int arcHeight) {
        this.y = y;
        this.x = x;
        this.width = width;
        this.height = height;
        this.color = color;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(this.x,this.y);
        g2d.scale(scale,scale);
        g2d.rotate(rotation);

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(x,y,width,height);
    }
}
