package agh.oop.pdw.model;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final Map<Vector2D, Animal> animals = new HashMap<>();
    private final Map<Vector2D, Grass> grasses = new HashMap<>();

    public void placeAnimal(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    public void spawnGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
    }

    public Map<Vector2D, Animal> getAnimals() {
        return animals;
    }

    public Map<Vector2D, Grass> getGrasses() {
        return grasses;
    }

    public Map<Vector2D, WorldElement> getElements() {
        Map<Vector2D, WorldElement> elements = new HashMap<>();
        elements.putAll(animals);
        elements.putAll(grasses);
        return elements;
    }
}
