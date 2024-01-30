package fr.iut.club;

import fr.iut.Ball;

import java.awt.geom.Point2D;
/**
 * Simple putter club implementation.
 */
public class Wood {
    private final static int DISTANCE_MAX = 100;
    public void shoot(final double force, final double direction, final Ball ball) {
        double x = ball.getPosition().getX();
        double y = ball.getPosition().getY();
        x += (force * DISTANCE_MAX) * Math.sin(direction);
        y += (force * DISTANCE_MAX) * Math.cos(direction);
        ball.setPosition(new Point2D.Double(x, y));  }
}
