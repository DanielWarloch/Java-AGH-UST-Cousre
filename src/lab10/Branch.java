package lab10;

import java.awt.*;

public class Branch implements XmasShape {
    private int x;
    private int y;
    private double xScale,yScale;
    public Branch(int x,int y){
        this(x,y,1,1);
    }
    public Branch(int x, int y,double xScale,double yScale){

        this.x = x;
        this.y = y;
        this.xScale=xScale;
        this.yScale=yScale;
    }
    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(xScale,yScale);
    }

    @Override
    public void render(Graphics2D g2d) {
        var xPoints = new int[]{0,200,400};
        var yPoints = new int[]{100,0,100};
        g2d.setColor(new Color(0x0E9118));
        g2d.fillPolygon(xPoints,yPoints,xPoints.length);
    }
}
