package agh.oop.pdw.model;

import java.util.Objects;

public class Vector2D {

    final int x;
    final int y;


    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Vector2D addVector(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() + v2.getX(),v1.getY() + v2.getY());
    }

    @Override
    public String toString() {
        return "Vector2D [x=" + x + ", y=" + y + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2D vector = (Vector2D) other;
        return this.getX() == vector.getX() && this.getY() == vector.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}
