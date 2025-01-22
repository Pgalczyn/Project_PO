package agh.oop.pdw.model;

import java.util.Arrays;
import java.util.Iterator;

public class AnimalStatistics {

    private final Animal animal;
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
        updateStatistics(animal);
    }

    public void updateStatistics(Animal animal) {
        if (animal == null) {return;}
        this.energy = Integer.toString(animal.getEnergy());
        this.activeGene = Integer.toString(animal.getActiveGene());
        this.amountOfEatenPlants = Integer.toString(animal.getAmountOfEatenPlants());
        this.amountOfChildren = Integer.toString(animal.getAmountOfChildren());
        this.amountOfDaysAlive = Integer.toString(animal.getAmountOfDaysAlive());
        this.amountOfDescendants = Integer.toString(getAmountOfDescendants(animal));
        this.isDead = Boolean.toString(animal.getCurrentEnergy() <= 0);
        this.genotype = Arrays.toString(animal.getGenotype());
    }


    public int getAmountOfDescendants(Animal animal) {
        int amountOfDescendants = 0;
        if (animal.getChildren().isEmpty()) return amountOfDescendants;
        Iterator<Animal> iterator = animal.getChildren().iterator();
        while (iterator.hasNext()){
            Animal child = (Animal) iterator.next();
            if (child.getCurrentEnergy() > 0) {
                amountOfDescendants++;
                amountOfDescendants += getAmountOfDescendants(child);
            } else {
                iterator.remove();
            }
        }
        return amountOfDescendants;
    }

    public String getPosition() {
        return "Position: [" + animal.getPosition().x + ", " + animal.getPosition().y + "]";
    }


    public String getGenotype() {
        return (animal == null) ? "" : "genotype: " + genotype;
    }

    public String getActiveGene() {
        return (animal == null) ? "" :"Active gene: " + activeGene;
    }

    public String getEnergy() {
        return (animal == null) ? "" :"Current Energy: " + energy;
    }

    public String getAmountOfEatenPlants() {
        return (animal == null) ? "" :"Eaten grass: " + amountOfEatenPlants;
    }

    public String getAmountOfChildren() {
        return (animal == null) ? "" :"Children count: " + amountOfChildren;
    }

    public String getAmountOfDescendants() {
        return (animal == null) ? "" :"Descendants: " + amountOfDescendants;
    }

    public String getAmountOfDaysAlive() {
        return (animal == null) ? "" :"Age of animal: " + amountOfDaysAlive;
    }

}
