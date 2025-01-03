package agh.oop.pdw;

// Opcje symulacji
public class SimulationProps {
    private final int mapWidth;
    private final int mapHeight;
    private final int startEnergy;
    private final boolean isMapPoles; // Czy wariant specjalny z biegunami
    private final int plants;
    private final int energyOnEat;
    private final int plantsPerDay;
    private final int energyPerMove;
    private final int startAnimals;
    private final int energyToBreed;
    private final int energyLossOnBreed;
    private final int minChildrenMutations;
    private final int maxChildrenMutations;
    private final boolean isSpecialMutation; // Starość nie radość
    private final int animalGenomeLength;

    private final int dayLimit;

    public int getDayLimit() {
        return dayLimit;
    }

    public SimulationProps(int mapWidth, int mapHeight, int startEnergy, boolean isMapPoles, int plants, int energyOnEat, int plantsPerDay, int energyPerMove, int startAnimals, int energyToBreed, int energyLossOnBreed, int minChildrenMutations, int maxChildrenMutations, boolean isSpecialMutation, int dayLimit, int animalGenomeLength) {
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
}
