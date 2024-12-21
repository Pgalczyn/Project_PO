package agh.oop.pdw.model;

import java.util.Random;

import static agh.oop.pdw.model.MapDirection.randomDirection;

public class Animal implements WorldElement{
    private MapDirection direction;
    private Vector2D position;
    private int currentEnergy;
    private int[] genotype;
    private int lengthOfGenotype;
    private int activeGene;
    private int amountOfEatenPlants = 0;
    private int amountOfChildren = 0;
    private int amountOfDescendants = 0;
    private int amountOfDaysAlive = 0;
    private int amountOfDaysUntilDeath = Integer.MAX_VALUE;


    //constructor for aniamals that were born on the map
    public Animal(Vector2D position, int StartEnergy, int[] genotype) {
        this.position = position;
        this.genotype = genotype;
        this.lengthOfGenotype = genotype.length;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;

        Random rand = new Random();
        this.activeGene = rand.nextInt(8);
    }
    //constructor for aniamls which we put on the map
    public Animal(Vector2D position, int StartEnergy,int LengthOfGenotype) {
        this.position = position;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.lengthOfGenotype = LengthOfGenotype;

        Random rand = new Random();
        int[] genotyp = new int [lengthOfGenotype];
        for (int i =0 ; i < lengthOfGenotype; i++) {
            genotyp[i] = rand.nextInt(8);
        }
        this.genotype = genotype;
        this.activeGene = rand.nextInt(8);

    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "Position: " + position.toString() + ", direction: " + direction.toString();
    }
}
