package agh.oop.pdw.model;

import agh.oop.pdw.model.util.Boundary;
import agh.oop.pdw.model.util.RandomUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WorldMap implements MoveValidator {
    private final Map<Vector2D, ArrayList<Animal>> animals = new HashMap<>();
    private final Map<Vector2D, Grass> grasses = new HashMap<>();
    private final List<Vector2D> emptyFields = new ArrayList<>(); // Possible positions for grass to spawn on.
    private final Boundary boundary;
    private final Boundary jungleBoundary;
    private final List<WorldMapListener> listeners = new ArrayList<>();


    public WorldMap(int height, int width, int plants) {
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(width - 1, height - 1));
        this.jungleBoundary = createJungle();
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
        notifySubscribers(animal.getPosition());
    }

    public void spawnGrass() {
        if (emptyFields.isEmpty()) return;
        Vector2D position = RandomUtils.getGrassSpawnPosition(this);
        notifySubscribers(position);
        Grass grass = new Grass(position);
        grasses.put(grass.getPosition(), grass);
        emptyFields.remove(grass.getPosition());
        notifySubscribers(position);
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

    public void notifySubscribers(Vector2D position) {
        for (WorldMapListener listener : listeners) {
            listener.fieldUpdated(position);
        }
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


    public String amountOfAnimalsOnTheMap() {
        int totalNumberOfAnimals = 0;
        for (ArrayList<Animal> animalsArray : animals.values()) {
            for (Animal animal : animalsArray) {
                totalNumberOfAnimals++;
            }
        }
        return String.valueOf(totalNumberOfAnimals);
    }

    public int amountOfGrassOnTheMap() {
        return grasses.size();
    }

    public int amountOfEmptyFields() {
        return emptyFields.size();
    }

    public String theMostPopularGenotype() {

        Map<String, Integer> countGenotype = new HashMap<>();

        for (ArrayList<Animal> animalsArray : animals.values()) {
            for (Animal animal : animalsArray) {
                String genotypeKey = Arrays.toString(animal.getGenotype());
                countGenotype.put(genotypeKey, countGenotype.getOrDefault(genotypeKey, 0) + 1);

            }
        }

        String mostPopularGenotype = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : countGenotype.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularGenotype = entry.getKey();
            }

        }
        return mostPopularGenotype;
    }

    public double averageLevelOfEnergyOfAnimals() {
        int totalEnergy = 0;
        int amountOfAnimals = 0;


        for (ArrayList<Animal> animalsArray : animals.values()) {
            for (Animal animal : animalsArray) {
                totalEnergy += animal.getCurrentEnergy();
                amountOfAnimals++;
            }
        }
        if (amountOfAnimals == 0) return 0;
        double result = (double) totalEnergy/amountOfAnimals;
        return Math.round(result * 100) / 100.0;
    }

//    public double avgLifeTimeForDeadAnimal(int amountOfDeadAnimals,int sumOfDaysAliveForDeadAnimals) {
//
//    }

    public double avgAmountOfChildren() {
        int amountOfAnimals = 0;
        int totalChildren = 0;

        for (ArrayList<Animal> animalsArray : animals.values()) {
            for (Animal animal : animalsArray) {
                totalChildren += animal.getAmountOfChildren();
                amountOfAnimals++;
            }
        }

        if (amountOfAnimals == 0) {
            return 0.0;
        }

        double result = (double) totalChildren / amountOfAnimals;
        return Math.round(result * 100.0) / 100.0;
    }


    public void move(Animal animal) {
        Vector2D oldPosition = animal.getPosition();
        animal.move(this);
        ArrayList<Animal> animalsAtPosition = animals.get(oldPosition);
        animalsAtPosition.remove(animal);
        if (animalsAtPosition.isEmpty()) {
            animals.remove(oldPosition);
        } else {
            animals.put(oldPosition, animalsAtPosition);
        }
        placeAnimal(animal);
        notifySubscribers(oldPosition);
    }

    public void removeGrass(Vector2D position) {
        grasses.remove(position);
        emptyFields.add(position);
        notifySubscribers(position);
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
        System.out.println("Animal died at MAP: " + animal.getPosition());
         notifySubscribers(animal.getPosition());
    }
}
