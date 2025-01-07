package agh.oop.pdw.model;

import agh.oop.pdw.model.util.Boundary;
import agh.oop.pdw.model.util.RandomUtils;

import java.util.*;

public class WorldMap implements MoveValidator {
    private final Map<Vector2D, Animal[]> animals = new HashMap<>();
    private final Map<Vector2D, Grass> grasses = new HashMap<>();
    private final List<Vector2D> emptyFields = new ArrayList<>(); // Possible positions for grass to spawn on.
    private final Boundary boundary;
    private Boundary jungleBoundary;


    public WorldMap(int height, int width, int plants) {
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(height - 1, width - 1));
        this.jungleBoundary = createJungle();
        spawnStartingGrass(plants);
    }

    // Creates a jungle's boundaries in the middle of the map, which is 20% of the map's area.
    private Boundary createJungle() {
        int jungleWidth = (int) (getWidth() * Math.sqrt(0.2));
        int jungleHeight = (int) (getHeight() * Math.sqrt(0.2));
        return new Boundary(
                new Vector2D((getWidth() - jungleWidth) / 2, (getHeight() - jungleHeight) / 2),
                new Vector2D((getWidth() + jungleWidth) / 2, (getHeight() + jungleHeight) / 2)
        );
    }

    public void placeAnimal(Animal animal) {
        if (animals.containsKey(animal.getPosition())) {
            Animal[] animalsOnPosition = animals.get(animal.getPosition());
            Animal[] newAnimalsOnPosition = Arrays.copyOf(animalsOnPosition, animalsOnPosition.length + 1);
            newAnimalsOnPosition[animalsOnPosition.length] = animal;
            animals.put(animal.getPosition(), newAnimalsOnPosition);
        } else {
            animals.put(animal.getPosition(), new Animal[]{animal});
        }
    }

    public void spawnGrass() {
        Vector2D position = RandomUtils.getGrassSpawnPosition(this);
        Grass grass = new Grass(position);
        if (position == null) return;
        grasses.put(grass.getPosition(), grass);
        emptyFields.remove(grass.getPosition());
    }

    private void spawnStartingGrass(int plants) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                emptyFields.add(new Vector2D(i, j));
            }
        }
        for (int i = 0; i < plants; i++) {
            spawnGrass();
        }
    }

    private int getHeight() {
        return boundary.topRight().getY() + 1;
    }

    private int getWidth() {
        return boundary.topRight().getX() + 1;
    }

    public Map<Vector2D, Animal[]> getAnimals() {
        return animals;
    }

    public Map<Vector2D, Grass> getGrasses() {
        return grasses;
    }


    public List<Vector2D> getEmptyFields() {
        return emptyFields;
    }

    public Boundary getJungleBoundary() {
        return jungleBoundary;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.getY() < this.getHeight() && position.getY() >= 0;
    }

    @Override
    public Vector2D getNextPosition(Animal animal) {
        Vector2D nextPosition = animal.getPosition().addVector(animal.getDirection().toVector());
        if (!canMoveTo(nextPosition)) {
            return animal.getPosition();
        }
        if (nextPosition.getX() >= getWidth()) {
            nextPosition = new Vector2D(0, nextPosition.getY());
        } else if (nextPosition.getX() < 0) {
            nextPosition = new Vector2D(getWidth() - 1, nextPosition.getY());
        }
        return nextPosition;
    }
}
