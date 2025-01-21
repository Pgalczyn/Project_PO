package agh.oop.pdw.simulation;

// Opcje symulacji
public class SimulationProps {
    private int mapWidth; //
    private int mapHeight;//
    private int startEnergy;//
    private boolean isMapPoles; // Czy wariant specjalny z biegunami
    private int plants;//
    private int energyOnEat;//
    private int plantsPerDay;//
    private int energyPerMove;//
    private int startAnimals;
    private int energyToBreed;//
    private int energyLossOnBreed;//
    private int minChildrenMutations;
    private int maxChildrenMutations;
    private boolean isSpecialMutation; // Starość nie radość
    private int animalGenomeLength;//
    private int dayOffset;
    private int dayLimit;
//    private int amountOfDeadAnimals;
//    private int sumOfDaysAliveForDeadAnimals;
    public SimulationProps() {

    }

    public int getDayLimit() {
        return dayLimit;
    }

    public SimulationProps(int mapWidth, int mapHeight, int startEnergy, boolean isMapPoles, int plants, int energyOnEat, int plantsPerDay, int energyPerMove, int startAnimals, int energyToBreed, int energyLossOnBreed, int minChildrenMutations, int maxChildrenMutations, boolean isSpecialMutation, int dayLimit, int animalGenomeLength, int dayOffset) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.startEnergy = startEnergy;
        this.isMapPoles = isMapPoles;
        this.plants = plants;
        this.energyOnEat = energyOnEat;
        this.plantsPerDay = plantsPerDay;
        this.energyPerMove = energyPerMove;
        this.startAnimals = startAnimals;
        this.energyToBreed = energyToBreed;
        this.energyLossOnBreed = energyLossOnBreed;
        this.minChildrenMutations = minChildrenMutations;
        this.maxChildrenMutations = maxChildrenMutations;
        this.isSpecialMutation = isSpecialMutation;
        this.dayLimit = dayLimit;
        this.animalGenomeLength = animalGenomeLength;
        this.dayOffset = dayOffset;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public boolean isMapPoles() {
        return isMapPoles;
    }

    public int getPlants() {
        return plants;
    }

    public int getEnergyOnEat() {
        return energyOnEat;
    }

    public int getPlantsPerDay() {
        return plantsPerDay;
    }

    public int getEnergyPerMove() {
        return energyPerMove;
    }

    public int getStartAnimals() {
        return startAnimals;
    }

    public int getEnergyToBreed() {
        return energyToBreed;
    }

    public int getEnergyLossOnBreed() {
        return energyLossOnBreed;
    }

    public int getMinChildrenMutations() {
        return minChildrenMutations;
    }

    public int getMaxChildrenMutations() {
        return maxChildrenMutations;
    }

    public boolean isSpecialMutation() {
        return isSpecialMutation;
    }

    public int getAnimalGenomeLength() {
        return animalGenomeLength;
    }

    public int getDayOffset() {
        return dayOffset;
    }

    public void setDayOffset(int dayOffset) {
        this.dayOffset = dayOffset;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setStartEnergy(int startEnergy) {
        this.startEnergy = startEnergy;
    }

    public void setMapPoles(boolean mapPoles) {
        isMapPoles = mapPoles;
    }

    public void setPlants(int plants) {
        this.plants = plants;
    }

    public void setEnergyOnEat(int energyOnEat) {
        this.energyOnEat = energyOnEat;
    }

    public void setPlantsPerDay(int plantsPerDay) {
        this.plantsPerDay = plantsPerDay;
    }

    public void setEnergyPerMove(int energyPerMove) {
        this.energyPerMove = energyPerMove;
    }

    public void setStartAnimals(int startAnimals) {
        this.startAnimals = startAnimals;
    }

    public void setEnergyToBreed(int energyToBreed) {
        this.energyToBreed = energyToBreed;
    }

    public void setEnergyLossOnBreed(int energyLossOnBreed) {
        this.energyLossOnBreed = energyLossOnBreed;
    }

    public void setMinChildrenMutations(int minChildrenMutations) {
        this.minChildrenMutations = minChildrenMutations;
    }

    public void setMaxChildrenMutations(int maxChildrenMutations) {
        this.maxChildrenMutations = maxChildrenMutations;
    }

    public void setSpecialMutation(boolean specialMutation) {
        isSpecialMutation = specialMutation;
    }

    public void setAnimalGenomeLength(int animalGenomeLength) {
        this.animalGenomeLength = animalGenomeLength;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }


}
