package agh.oop.pdw.model;

public class AnimalStatistics {

    private  final Animal animal;
    private int[] genotype;
    private int activeGene;
    private int energy;
    private int amountOfEatenPlants;
    private int amountOfChildren;
    private int amountOfDescendants;
    private int amountOfDaysAlive;
    private boolean isDead;


    public AnimalStatistics(Animal animal) {
        this.animal = animal;
        updateStatisticts();
    }

    public void updateStatisticts() {
        this.energy = animal.getEnergy();
        this.activeGene = animal.getActiveGene();
        this.amountOfEatenPlants = animal.getAmountOfEatenPlants();
        this.amountOfChildren = animal.getAmountOfChildren();
        this.amountOfDaysAlive = animal.getAmountOfDaysAlive();
        this.amountOfDescendants = getAmountOfDescendants(animal);
        this.isDead = animal.getCurrentEnergy() <= 0;
        this.genotype = animal.getGenotype();
    }


    public int getAmountOfDescendants(Animal animal) {
        int amountOfDescendants = 0;


        if(animal.getChildren().isEmpty()) return amountOfDescendants;

        for(Animal child : animal.getChildren()) {
            amountOfDescendants++;
            amountOfDescendants += getAmountOfDescendants(child);
        }
        return amountOfDescendants;
    }




}
