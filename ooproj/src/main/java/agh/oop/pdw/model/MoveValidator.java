package agh.oop.pdw.model;

import agh.oop.pdw.model.util.Boundary;

public interface MoveValidator {

    boolean canMoveTo(Vector2D position);

    Vector2D getNextPosition(Animal animal);

    Boundary getBoundary();
}
