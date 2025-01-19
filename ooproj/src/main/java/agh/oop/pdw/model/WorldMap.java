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

    public boolean isOccupied(Vector2D position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2D position) {
        if (animals.containsKey(position)) {

            return animals.get(position);
        }
        if (grasses.containsKey(position)) {

            return grasses.get(position);
        }
        return null;
    }


    public String amountOfAnimalsOnTheMap(){
        int totalNumberOfAnimals = 0;
        for(Animal[] animalsArray : animals.values()) {
            for(Animal animal : animalsArray) {
                totalNumberOfAnimals++;
            }
        }
        return String.valueOf(totalNumberOfAnimals);
    }
    public  int amountOfGrassOnTheMap(){
        return grasses.size();
    }
    public int amountOfEmptyFields() {
        return emptyFields.size();
    }
    public String theMostPopularGenotype(){

        Map<String,Integer> countGenotype = new HashMap<>();

        for(Animal[] animalsArray : animals.values()) {
            for(Animal animal : animalsArray) {
                String genotypeKey = Arrays.toString(animal.getGenotype());
                countGenotype.put(genotypeKey, countGenotype.getOrDefault(genotypeKey, 0) + 1);

            }
        }

        String mostPopularGenotype = null;
        int maxCount = 0;

        for (Map.Entry<String,Integer> entry : countGenotype.entrySet()){
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularGenotype = entry.getKey();
            }

        }
     return mostPopularGenotype;
    }

    public double averageLevelOfEnergyOfAnimals(){
        int totalEnergy = 0;
        int amountOfAnimals = 0;


        for(Animal[] animalsArray : animals.values()) {
            for(Animal animal : animalsArray) {
                totalEnergy += animal.getCurrentEnergy();
            }
        }
        return (double) totalEnergy/amountOfAnimals;
    }
    public double avgLifeTimeForDeadAnimal(){
        int totalLifeTime = 0;
        int amountOfAnimals = 0;
        for(Animal[] animalsArray : animals.values()) {
            for(Animal animal : animalsArray) {
               if(animal.getAmountOfDaysUntilDeath() != Integer.MAX_VALUE) {
                   totalLifeTime+= animal.getAmountOfDaysUntilDeath();
               }
            }
        }
        return (double) totalLifeTime/amountOfAnimals;
    }

    public double avgAmountOfChildren(){
        int amountOfAnimals = 0;
        int totalChildren = 0;
        for(Animal[] animalsArray : animals.values()) {
            for(Animal animal : animalsArray) {
                totalChildren += animal.getAmountOfChildren();
                amountOfAnimals++;
            }
        }
        return (double) totalChildren/amountOfAnimals;
    }


    public int getHeight() {
        return boundary.topRight().getY() + 1;
    }

    public int getWidth() {
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
