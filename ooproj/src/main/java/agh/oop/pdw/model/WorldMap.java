package agh.oop.pdw.model;

import agh.oop.pdw.model.util.Boundary;
import agh.oop.pdw.model.util.RandomUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WorldMap implements MoveValidator {
    private final Map<Vector2D, ArrayList<Animal>> animals = new HashMap<>();
    private final Map<Vector2D, Grass> grasses = new HashMap<>();
    private final WorldMapInfo informer;
    private final List<Vector2D> emptyFields = new ArrayList<>();
    private final HashSet<Vector2D> updatedFields = new HashSet<>();
    private final Boundary boundary;
    private final Boundary jungleBoundary;
    private final List<WorldMapListener> listeners = new ArrayList<>();


    public WorldMap(int height, int width, int plants) {
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(width - 1, height - 1));
        this.jungleBoundary = createJungle();
        this.informer = new WorldMapInfo(this);
        spawnStartingGrass(plants);
    }

    // Creates a strip of jungle in the middle of the map.
    private Boundary createJungle() {
        int jungleHeight = (int) Math.ceil(0.2 * this.getHeight());
        return new Boundary(
                new Vector2D(0, Math.ceilDiv(getHeight() - jungleHeight, 2)),
                new Vector2D(getWidth() - 1, Math.ceilDiv(getHeight() - jungleHeight, 2) + jungleHeight - 1)
        );
    }

    public void placeAnimal(Animal animal) {
        if (animals.containsKey(animal.getPosition())) {
            ArrayList<Animal> animalsOnPosition = animals.get(animal.getPosition());
            animalsOnPosition.add(animal);
            animals.put(animal.getPosition(), animalsOnPosition);
        } else {
            animals.put(animal.getPosition(), new ArrayList<>(Collections.singletonList(animal)));
        }
        updatedFields.add(animal.getPosition());
    }

    public void spawnGrass() {
        if (emptyFields.isEmpty()) return;
        Vector2D position = RandomUtils.getGrassSpawnPosition(this);
        Grass grass = new Grass(position);
        grasses.put(grass.getPosition(), grass);
        emptyFields.remove(grass.getPosition());
        updatedFields.add(grass.getPosition());
    }

    private void spawnStartingGrass(int plants) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                emptyFields.add(new Vector2D(i, j));
            }
        }
        for (int i = 0; i < plants; i++) {
            spawnGrass();
        }
    }

    public void addListener(WorldMapListener listener) {
        listeners.add(listener);
    }

    public boolean isOccupied(Vector2D position) {
        return objectAt(position) != null;
    }

    public List<WorldElement> objectAt(Vector2D position) {

        List<WorldElement> elementsOnTheSamePole = new ArrayList<>();
//dodaje tylko jednego animala bo i tak jesteśmy w stanie wyświtlić tylko jednego animala na mapie na danym polu
        if (animals.containsKey(position)) {
            elementsOnTheSamePole.addAll(animals.get(position));
        }
        if (grasses.containsKey(position)) {
            elementsOnTheSamePole.add(grasses.get(position));

        }
        if (elementsOnTheSamePole.isEmpty()) {
            return null;
        }
        return elementsOnTheSamePole;
    }

    public void move(Animal animal) {
        removeAnimal(animal);
        updatedFields.add(animal.getPosition());
        animal.move(this);
        placeAnimal(animal);
        updatedFields.add(animal.getPosition());
    }

    public void removeGrass(Vector2D position) {
        grasses.remove(position);
        emptyFields.add(position);
        updatedFields.add(position);
    }


    public int getHeight() {
        return boundary.topRight().getY() + 1;
    }

    public int getWidth() {
        return boundary.topRight().getX() + 1;
    }

    public Map<Vector2D, ArrayList<Animal>> getAnimals() {
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

    public HashSet<Vector2D> getUpdatedFields() {
        return updatedFields;
    }

    public WorldMapInfo getInformer() {
        return informer;
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

    public void removeAnimal(Animal animal) {
        ArrayList<Animal> animalsOnPosition = animals.get(animal.getPosition());
        animalsOnPosition.remove(animal);
        if (animalsOnPosition.isEmpty()) {
            animals.remove(animal.getPosition());
        } else {
            animals.put(animal.getPosition(), animalsOnPosition);
        }
//        System.out.println("Animal died at MAP: " + animal.getPosition());
        updatedFields.add(animal.getPosition());
    }



}
