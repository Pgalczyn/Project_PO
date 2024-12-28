package agh.oop.pdw.model.util;

import agh.oop.pdw.model.Vector2D;

public record Boundary(Vector2D bottomLeft, Vector2D topRight) {
    public Boundary(Vector2D bottomLeft, Vector2D topRight) {
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
    }

    public double getArea() {
        return (topRight.getX() - bottomLeft.getX() + 1) * (topRight.getY() - bottomLeft.getY() + 1);
    }

    public boolean contains(Vector2D position) {
        return position.follows(bottomLeft) && position.precedes(topRight);
    }
}