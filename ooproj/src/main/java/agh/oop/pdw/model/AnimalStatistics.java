package agh.oop.pdw.model;

import java.util.Arrays;

public class AnimalStatistics {

    private  final Animal animal;
    private String genotype;
    private String activeGene;
    private String energy;
    private String amountOfEatenPlants;
    private String amountOfChildren;
    private String amountOfDescendants;
    private String amountOfDaysAlive;
    private String isDead;


    public AnimalStatistics(Animal animal) {
        this.animal = animal;
        updateStatistics();
    }

    public void updateStatistics() {
        this.energy = Integer.toString(animal.getEnergy());
        this.activeGene = Integer.toString(animal.getActiveGene());
        this.amountOfEatenPlants = Integer.toString(animal.getAmountOfEatenPlants());
        this.amountOfChildren =  Integer.toString(animal.getAmountOfChildren());
        this.amountOfDaysAlive =  Integer.toString(animal.getAmountOfDaysAlive());
        this.amountOfDescendants =  Integer.toString(getAmountOfDescendants(animal));
        this.isDead = Boolean.toString(animal.getCurrentEnergy() <= 0);
        this.genotype = Arrays.toString(animal.getGenotype());
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


    public String getGenotype() {
        return "Genotype: " + genotype;
    }

    public String getActiveGene() {
        return "Active gene: " + activeGene;
    }

    public String getEnergy() {
        return "Current Energy: " + energy;
    }

    public String getAmountOfEatenPlants() {
        return "Eaten grass: " + amountOfEatenPlants;
    }

    public String getAmountOfChildren() {
        return "Children count: " + amountOfChildren;
    }

    public String getAmountOfDescendants() {
        return "Descendants: " + amountOfDescendants;
    }

    public String getAmountOfDaysAlive() {
        return "Age of animal" + amountOfDaysAlive;
    }

}
