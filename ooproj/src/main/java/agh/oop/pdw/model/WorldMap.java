package agh.oop.pdw.model;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final Map<Vector2D, Animal> animals = new HashMap<>();

    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    public Map<Vector2D, Animal> getAnimals() {
        return animals;
    }
}
