package agh.oop.pdw.model;

import java.util.HashSet;

public interface SimulationListener {
    void dayPassed(HashSet<Vector2D> updatedFields);
}
