package agh.oop.pdw.model;

public class Grass implements WorldElement {
    private final Vector2D position;

    public Grass(Vector2D position) {
        this.position = position;
    }


    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
