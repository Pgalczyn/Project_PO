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
            Map<String, Integer> countGenotype = new HashMap<>();
            Map<Vector2D, ArrayList<Animal>> animals = new HashMap<>(map.getAnimals());
            int totalEnergy = 0;
            int amountOfAnimals = 0;
            int totalChildren = 0;

            for (ArrayList<Animal> animalList : animals.values()) {
                for (Animal animal : animalList) {
                    amountOfAnimals++;
                    totalEnergy += animal.getCurrentEnergy();
                    totalChildren += animal.getAmountOfChildren();

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
            System.err.println("Skipping information update");
//            e.printStackTrace();
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


    public String getAvgLifeTimeForDeadAnimal() {
        return Double.toString(avgLifeTimeForDeadAnimal);
    }

    public String getAmountOfEmptyFieldsOnTheMap() {
        return Double.toString(amountOfEmptyFieldsOnTheMap);
    }

    public String getAmountOfGrassOnTheMap() {
        return Double.toString(amountOfGrassOnTheMap);
    }

    public String getAvgAmountOfChildren() {
        return Double.toString(avgAmountOfChildren);
    }

    public String getAverageLevelOfEnergyOfAnimals() {
        return Double.toString(averageLevelOfEnergyOfAnimals);
    }

    public String getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    public String getAmountOfAnimalsOnTheMap() {
        return Double.toString(amountOfAnimalsOnTheMap);
    }
}





