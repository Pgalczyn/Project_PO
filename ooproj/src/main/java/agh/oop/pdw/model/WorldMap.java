package agh.oop.pdw.model;

import agh.oop.pdw.model.util.Boundary;
import agh.oop.pdw.model.util.RandomUtils;

import java.util.*;

public class WorldMap {
    private final Map<Vector2D, Animal> animals = new HashMap<>();
    private final Map<Vector2D, Grass> grasses = new HashMap<>();
    private final List<Vector2D> emptyFields = new ArrayList<>(); // Possible positions for grass to spawn on.
    private final Boundary boundary;
    private Boundary jungleBoundary;


    public WorldMap(int height, int width, int plants) {
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(height- 1, width - 1));
        createJungle();
        spawnStartingGrass(plants);
    }

    // Creates a jungle's boundaries in the middle of the map, which is 20% of the map's area.
    private void createJungle() {
        int jungleWidth = (int) (getWidth() * Math.sqrt(0.2));
        int jungleHeight = (int) (getHeight() * Math.sqrt(0.2));
        this.jungleBoundary = new Boundary(
                new Vector2D((getWidth() - jungleWidth) / 2, (getHeight() - jungleHeight) / 2),
                new Vector2D((getWidth() + jungleWidth) / 2, (getHeight() + jungleHeight) / 2)
        );
    }

    public void placeAnimal(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    public void spawnGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
        emptyFields.remove(grass.getPosition());
    }

    private void spawnStartingGrass(int plants) {
        for(int i = 0; i < getHeight(); i ++) {
            for (int j = 0; j < getWidth(); j++) {
                emptyFields.add(new Vector2D(i, j));
            }
        }
        for(int i = 0; i < plants; i++) {
            spawnGrass(new Grass(RandomUtils.getGrassSpawnPosition(this)));
        }
    }

    private int getHeight() {
        return boundary.topRight().getY() + 1;
    }

    private int getWidth() {
        return boundary.topRight().getX() + 1;
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

    public List<Vector2D> getEmptyFields() {
        return emptyFields;
    }
}
