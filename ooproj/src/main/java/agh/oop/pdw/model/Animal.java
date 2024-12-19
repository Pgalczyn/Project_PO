package agh.oop.pdw.model;

import static agh.oop.pdw.model.MapDirection.randomDirection;

public class Animal implements WorldElement{
    private MapDirection direction;
    private Vector2D position;

    public Animal(Vector2D position) {
        this.position = position;
        this.direction = randomDirection();
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "Position: " + position.toString() + ", direction: " + direction.toString();
    }
}
