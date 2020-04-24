package lab10;

import java.awt.*;

public class Caption implements XmasShape {
    private String text;
    private int x;
    private int y;
    private double scale;
    private double rotation;
    private Color color;

    public Caption(String text,int x, int y, double scale, double rotation,Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotation = rotation;
        this.color = color;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
        g2d.rotate(rotation);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setFont(new Font("Helvetica", Font.BOLD, 18));
        g2d.setColor(color);
        g2d.drawString(text,0,0);
    }
}
