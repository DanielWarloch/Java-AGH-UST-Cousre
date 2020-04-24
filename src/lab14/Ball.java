package lab14;

import collisionphysics.CollisionPhysics;
import collisionphysics.CollisionResponse;

import java.awt.*;
import java.util.Formatter;

public class Ball {
    float x, y;
    float speedX, speedY;
    float radius;
    private Color color;
    private static final Color DEFAULT_COLOR = Color.BLUE;


    CollisionResponse earliestCollisionResponse = new CollisionResponse();

    public Ball(float x, float y, float radius, float speed, float angleInDegree,
                Color color) {
        this.x = x;
        this.y = y;

        this.speedX = (float) (speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float) (-speed * (float) Math.sin(Math.toRadians(angleInDegree)));
        this.radius = radius;
        this.color = color;
    }

    public Ball(float x, float y, float radius, float speed, float angleInDegree) {
        this(x, y, radius, speed, angleInDegree, DEFAULT_COLOR);
    }


    private CollisionResponse tempResponse = new CollisionResponse();


    public void intersect(ContainerBox box, float timeLimit) {

        CollisionPhysics.pointIntersectsRectangleOuter(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                box.minX, box.minY, box.maxX, box.maxY,
                timeLimit, tempResponse);
        if (tempResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(tempResponse);
        }
    }


    private CollisionResponse thisResponse = new CollisionResponse();
    private CollisionResponse anotherResponse = new CollisionResponse();


    public void intersect(Ball another, float timeLimit) {

        CollisionPhysics.pointIntersectsMovingPoint(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                another.x, another.y, another.speedX, another.speedY, another.radius,
                timeLimit, thisResponse, anotherResponse);

        if (anotherResponse.t < another.earliestCollisionResponse.t) {
            another.earliestCollisionResponse.copy(anotherResponse);
        }
        if (thisResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(thisResponse);
        }
    }

    public void intersect(ObstaclePolygon polygon, float timeLimit) {
        int numPoints = polygon.xPoints.length;
        CollisionPhysics.pointIntersectsPolygon(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                polygon.xPoints, polygon.yPoints, numPoints,
                timeLimit, tempResponse);
        if (tempResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(tempResponse);
        }
    }

    public void intersect(ObstacleLine line, float timeLimit) {
        CollisionPhysics.pointIntersectsLine(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                line.x1, line.y1, line.x2, line.y2,
                timeLimit, tempResponse);
        if (tempResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(tempResponse);
        }
    }


    public void intersect(ObstacleLineSegment lineSegment, float timeLimit) {
        CollisionPhysics.pointIntersectsLineSegment(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                lineSegment.x1, lineSegment.y1, lineSegment.x2, lineSegment.y2,
                timeLimit, tempResponse);
        if (tempResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(tempResponse);
        }
    }


    public void intersect(ObstacleCircle circle, float timeLimit) {
        CollisionPhysics.pointIntersectsPoint(
                this.x, this.y, this.speedX, this.speedY, this.radius,
                circle.centerX, circle.centerY, circle.radius,
                timeLimit, tempResponse);
        if (tempResponse.t < earliestCollisionResponse.t) {
            earliestCollisionResponse.copy(tempResponse);
        }
    }


    public void update(float time) {

        if (earliestCollisionResponse.t <= time) {

            this.x = earliestCollisionResponse.getNewX(this.x, this.speedX);
            this.y = earliestCollisionResponse.getNewY(this.y, this.speedY);
            this.speedX = earliestCollisionResponse.newSpeedX;
            this.speedY = earliestCollisionResponse.newSpeedY;
        } else {

            this.x += this.speedX * time;
            this.y += this.speedY * time;
        }

        earliestCollisionResponse.reset();
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) (2 * radius),
                (int) (2 * radius));
    }


    public float getSpeed() {
        return (float) Math.sqrt(speedX * speedX + speedY * speedY);
    }


    public float getMoveAngle() {
        return (float) Math.toDegrees(Math.atan2(-speedY, speedX));
    }


    public float getMass() {
        return radius * radius * radius / 1000f;
    }


    public float getKineticEnergy() {
        return 0.5f * getMass() * (speedX * speedX + speedY * speedY);
    }


    public String toString() {
        sb.delete(0, sb.length());
        formatter.format("@(%3.0f,%3.0f) r=%3.0f V=(%3.0f,%3.0f) " +
                        "S=%4.1f \u0398=%4.0f KE=%3.0f",
                x, y, radius, speedX, speedY, getSpeed(), getMoveAngle(),
                getKineticEnergy());
        return sb.toString();
    }

    private StringBuilder sb = new StringBuilder();
    private Formatter formatter = new Formatter(sb);

}
