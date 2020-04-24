package lab10;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class Tree implements XmasShape {
    private final int x;
    private final int y;
    private final double xScale;
    private final double yScale;
    private java.util.List<Branch> branches;

    public Tree(int x, int y, double xScale, double yScale) {
        this.x = x;
        this.y = y;
        this.xScale = xScale;
        this.yScale = yScale;
        this.branches = Arrays.asList(
                new Branch(120,0,0.4,1),
                new Branch(80,80,0.6,1),
                new Branch(40,160,0.8,1),
                new Branch(0,240,1,1)
        );
    }


    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(xScale,yScale);
    }

    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform saveAT = g2d.getTransform();

        transform(g2d);
        for (Branch branch : branches) {
            branch.draw(g2d);
        }
        drawTrunk(g2d);
        g2d.setTransform(saveAT);
    }
    private void drawTrunk(Graphics2D g2d)
    {
        g2d.setColor(new Color(0x482411));
        g2d.fillRect(160,340,80,80);
    }
}
