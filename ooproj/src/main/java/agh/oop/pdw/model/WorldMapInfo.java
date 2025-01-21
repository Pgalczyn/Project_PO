package agh.oop.pdw.model;

import agh.oop.pdw.simulation.Simulation;

import java.util.*;

public class WorldMapInfo {

    private final WorldMap map;
    private int amountOfAnimalsOnTheMap = 0;
    private String theMostPopularGenotype = "0";
    private double averageLevelOfEnergyOfAnimals = 0;
    private double avgAmountOfChildren = 0;
    private double avgLifeTimeForDeadAnimal = 0;
    private int amountOfGrassOnTheMap = 0;
    private int amountOfEmptyFieldsOnTheMap = 0;


    public WorldMapInfo(WorldMap map) {
        this.map = map;
    }

    public void getInfoWorldMap(Simulation simulation) {
        try {
            Map<Vector2D, ArrayList<Animal>> animalsMap = map.getAnimals();
            ArrayList<Animal> animals = new ArrayList<>();
            Map<String, Integer> countGenotype = new HashMap<>();

            int totalEnergy = 0;
            int amountOfAnimals = 0;
            int totalChildren = 0;

            HashSet<ArrayList<Animal>> set = new HashSet<>(animalsMap.values());
            for (ArrayList<Animal> animalList : set) {
                for (Animal animal : animalList) {
                    amountOfAnimals++;
                    totalEnergy += animal.getCurrentEnergy();
                    totalChildren += animal.getAmountOfChildren();

                    //counting genotypes
                    String genotypeKey = Arrays.toString(animal.getGenotype());
                    countGenotype.put(genotypeKey, countGenotype.getOrDefault(genotypeKey, 0) + 1);

                }
            }
            theMostPopularGenotype(countGenotype);

            if (amountOfAnimals > 0) {
                this.averageLevelOfEnergyOfAnimals = Math.round(((double) totalEnergy / amountOfAnimals) * 100.0) / 100.0;
                this.avgAmountOfChildren = Math.round(((double) totalChildren / amountOfAnimals) * 100.0) / 100.0;
                this.amountOfAnimalsOnTheMap = amountOfAnimals;
            }
            if (simulation.getDeadAnimals() > 0) {
                avgLifeTimeForDeadAnimal(simulation.getDeadAnimals(), simulation.getSumOfDeadAnimalsDays());
            }

            this.amountOfGrassOnTheMap = map.getGrasses().size();
            this.amountOfEmptyFieldsOnTheMap = map.getEmptyFields().size();
        } catch (ConcurrentModificationException e) {
            System.out.println("Aborting calculation of statistics");
        }

    }

    public void theMostPopularGenotype(Map<String, Integer> countGenotype) {
        String mostPopularGenotype = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : countGenotype.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularGenotype = entry.getKey();
            }
        }
        this.theMostPopularGenotype = mostPopularGenotype;
    }

    public void avgLifeTimeForDeadAnimal(int amountOfDeadAnimals, int sumOfDaysAliveForDeadAnimals) {
        this.avgLifeTimeForDeadAnimal = Math.round((double) sumOfDaysAliveForDeadAnimals / amountOfDeadAnimals * 100) / 100.0;
    }


    public double getAvgLifeTimeForDeadAnimal() {
        return avgLifeTimeForDeadAnimal;
    }

    public int getAmountOfEmptyFieldsOnTheMap() {
        return amountOfEmptyFieldsOnTheMap;
    }

    public int getAmountOfGrassOnTheMap() {
        return amountOfGrassOnTheMap;
    }

    public double getAvgAmountOfChildren() {
        return avgAmountOfChildren;
    }

    public double getAverageLevelOfEnergyOfAnimals() {
        return averageLevelOfEnergyOfAnimals;
    }

    public String getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    public int getAmountOfAnimalsOnTheMap() {
        return amountOfAnimalsOnTheMap;
    }
}





