package agh.oop.pdw.model.util;

import agh.oop.pdw.model.Vector2D;

public record Boundary(Vector2D bottomLeft, Vector2D topRight) {
    public Boundary(Vector2D bottomLeft, Vector2D topRight) {
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
    }
}