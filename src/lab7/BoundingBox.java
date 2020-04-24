package lab7;

import lab7.utils.Haversine;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
public class BoundingBox {
    private double xmin;
    private double ymin;
    private double xmax;
    private double ymax;

    public BoundingBox(double x, double y)
    {
        xmin = x;
        xmax = x;
        ymin = y;
        ymax = y;
    }

    public BoundingBox(double xmin,double ymin,double xmax,double ymax)
    {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    BoundingBox addPoint(double x, double y)
    {
        double xmin=0, xmax=0, ymin=0, ymax=0;
        if(x < this.xmin)
            xmin = x;
        if(x > this.xmax)
            xmax = x;
        if(y<this.ymin)
            ymin = y;
        if(y>this.ymax)
            ymax = y;
        return new BoundingBox(xmin,ymin,xmax,ymax);
    }

    public boolean contains(double x, double y){

        if(x<xmin || x > xmax)
            return false;

        return !(y < ymin) && !(y > ymax);
    }

    public boolean contains(BoundingBox box)
    {
        return box.xmin >= xmin
                && box.ymin >= ymin
                && box.xmax <= xmax
                && box.ymax <= ymax;
    }

    public boolean intersects(BoundingBox box)
    {
        return !(box.ymin >= ymax || box.ymax <= ymin || box.xmax <= xmin || box.xmin >= xmax);
    }

    BoundingBox add(BoundingBox bb)
    {
        double xmin=0, xmax=0, ymin=0, ymax=0;

        if(bb.xmax > xmax)
            xmax = bb.xmax;
        if(bb.xmin < xmin)
            xmin = bb.xmin;
        if(bb.ymax > ymax)
            ymax = bb.ymax;
        if(bb.ymin < ymin)
            ymin = bb.ymin;
        return new BoundingBox(xmin,ymin,xmax,ymax);
    }

    public double getCenterX(){
        return (xmin + xmax) / 2;
    }

    public double getCenterY(){
        return (ymin + ymax) / 2;
    }

    double distanceTo(BoundingBox box)
    {
        return Haversine.distanceTo(getCenterY(),getCenterX(),box.getCenterY(),box.getCenterX());
    }
}