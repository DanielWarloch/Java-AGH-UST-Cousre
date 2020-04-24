package lab10;

import java.awt.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Star implements XmasShape {
    private int x;
    private int y;
    private double scale;

    Star(int x, int y,double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        class Point
        {
            int x,y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        Supplier<Stream<Point>> points = ()->Stream.of(
                new Point(25,0),
                new Point(30,10),
                new Point(40,9),
                new Point(33,15),
                new Point(35,25),
                new Point(25,17),
                new Point(15,25),
                new Point(16,15),
                new Point(10,8),
                new Point(20,10)
        );
        g2d.setColor(new Color(0xFF0018));
        int[] xs = points.get().mapToInt(x->x.x).toArray();
        int[] ys = points.get().mapToInt(x->x.y).toArray();
        g2d.fillPolygon(xs,ys,(int)points.get().count());
    }
}
