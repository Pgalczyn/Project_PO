package agh.oop.pdw.config;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SimulationConfig {
    private int mapWidth;
    private int mapHeight;
    private int plantsOnStart;
    private int dailyPlants;
    private int startEnergy;
    private int energyOnEat;
    private int energyPerMove;
    private int genomeLength;
    private int energyToBreed;
    private int breedCost;
    private int startAnimals;
    private int minMutations;
    private int maxMutations;
    private int dayLimit;
    private boolean specialMutation;
    private boolean mapPoles;
    private int simulationTimeout;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    private String configName;

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getPlantsOnStart() {
        return plantsOnStart;
    }

    public void setPlantsOnStart(int plantsOnStart) {
        this.plantsOnStart = plantsOnStart;
    }

    public int getDailyPlants() {
        return dailyPlants;
    }

    public void setDailyPlants(int dailyPlants) {
        this.dailyPlants = dailyPlants;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public void setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
    }

    public int getEnergyOnEat() {
        return energyOnEat;
    }

    public void setEnergyOnEat(int energyOnEat) {
        this.energyOnEat = energyOnEat;
    }

    public int getEnergyPerMove() {
        return energyPerMove;
    }

    public void setEnergyPerMove(int energyPerMove) {
        this.energyPerMove = energyPerMove;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public int getEnergyToBreed() {
        return energyToBreed;
    }

    public void setEnergyToBreed(int energyToBreed) {
        this.energyToBreed = energyToBreed;
    }

    public int getBreedCost() {
        return breedCost;
    }

    public void setBreedCost(int breedCost) {
        this.breedCost = breedCost;
    }

    public int getStartAnimals() {
        return startAnimals;
    }

    public void setStartAnimals(int startAnimals) {
        this.startAnimals = startAnimals;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public boolean isSpecialMutation() {
        return specialMutation;
    }

    public void setSpecialMutation(boolean specialMutation) {
        this.specialMutation = specialMutation;
    }

    public boolean isMapPoles() {
        return mapPoles;
    }

    public void setMapPoles(boolean mapPoles) {
        this.mapPoles = mapPoles;
    }

    public int getSimulationTimeout() {
        return simulationTimeout;
    }

    public void setSimulationTimeout(int simulationTimeout) {
        this.simulationTimeout = simulationTimeout;
    }

    @Override
    public String toString() {
        return "" + mapWidth + ";" + mapHeight + ";" + plantsOnStart + ";" + dailyPlants + ";" + startEnergy + ";" + energyOnEat + ";" + energyPerMove + ";" + genomeLength + ";" + energyToBreed + ";" + breedCost + ";" + startAnimals + ";" + minMutations + ";" + maxMutations + ";" + dayLimit + ";" + specialMutation + ";" + mapPoles + ";" + simulationTimeout;
    }
}
