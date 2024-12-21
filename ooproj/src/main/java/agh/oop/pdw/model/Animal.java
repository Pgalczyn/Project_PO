package agh.oop.pdw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agh.oop.pdw.model.MapDirection.randomDirection;

public class Animal implements WorldElement,AnimalObserver{
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
    private boolean readyToReproduce = false;  //ustalimy początkowe wartości tak żeby zwierze musiało zjeść chociaż jedną rośline żeby móc się rozmnażać
    private int usedEnergyToReproduce;
    private List<AnimalObserver> observers;


    //constructor for aniamals that were born on the map
    public Animal(Vector2D position, int StartEnergy, int[] genotype, int usedEnergyToReproduce) {
        this.position = position;
        this.genotype = genotype;
        this.lengthOfGenotype = genotype.length;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.observers = new ArrayList<>();
        Random rand = new Random();
        this.activeGene = rand.nextInt(8);
    }
    //constructor for aniamls which we put on the map
    public Animal(Vector2D position, int StartEnergy, int LengthOfGenotype, int usedEnergyToReproduce, List<AnimalObserver> observers) {
        this.position = position;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.lengthOfGenotype = LengthOfGenotype;
        this.observers = observers;
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


    public Animal reproduce(Animal otherAnimal) {

        if (this.readyToReproduce && otherAnimal.readyToReproduce) {
            int[] newGenotype = getGenotype(this, otherAnimal);

            this.currentEnergy -= this.usedEnergyToReproduce;
            otherAnimal.currentEnergy -= this.usedEnergyToReproduce;

            //trzeba stworzyć listę observerów dla nowego zwierzaka ale może pojawic się problem że jakiś observer będzie
            //wspólny i będzei mu się zwiększała liczba potomków kilka razy trzeba bedzie chyba użyć treeSeta

        }



    }

    public int[] getGenotype(Animal animal_1, Animal animal_2) {
        int[] newGenotype = new int[genotype.length];
        int energy_1 = animal_1.currentEnergy;
        int energy_2 = animal_2.currentEnergy;
        int sumEnergy = energy_1 + energy_2;

        double partOfAnimal_1_Genotype = (double) (energy_1 / sumEnergy);

        int Animal_1_Part = (int) (animal_1.lengthOfGenotype * partOfAnimal_1_Genotype);
        int Animal_2_Part = animal_1.lengthOfGenotype - Animal_1_Part;
        //losowanie strony genotypu
        Random rand = new Random();
        int leftOrRight_Animal_1 = rand.nextInt(2);

        if (leftOrRight_Animal_1 == 0) {

            for (int i = 0; i < animal_1.lengthOfGenotype; i++) {

                if(i < Animal_1_Part) newGenotype[i] = animal_1.genotype[i];
                else newGenotype[i] = animal_2.genotype[i];

            }

        }
        else {
            for (int i = 0; i < animal_2.lengthOfGenotype; i++) {
                if(i < Animal_2_Part) newGenotype[i] = animal_2.genotype[i];
                else newGenotype[i] = animal_1.genotype[i];
            }
        }

        return newGenotype;

    }



    public void move(int activeGene,MoveValidator validator){

        for (int i =0 ; i < genotype[activeGene]; i++) {

            this.direction = this.direction.nextDirection();

        }
        if (validator.canMoveTo(position.addVector(this.direction.toVector()))){

            this.position = this.position.addVector(this.direction.toVector());
        }

        this.activeGene = (this.activeGene + 1) % lengthOfGenotype;


    }

    public void addObserver(AnimalObserver observer){
        observers.add(observer);
    }

    public void removeObserver(AnimalObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for (AnimalObserver observer : observers) {
            if (this.amountOfDaysUntilDeath == Integer.MAX_VALUE ) {
                removeObserver(observer);
            }
            else observer.updateDescendants();
        }
    }

    public int getAmountOfEatenPlants() {
        return amountOfEatenPlants;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public int getAmountOfDescendants() {
        return amountOfDescendants;
    }

    public int getAmountOfDaysAlive() {
        return amountOfDaysAlive;
    }

    public int getAmountOfDaysUntilDeath() {
        return amountOfDaysUntilDeath;
    }

    public boolean isReadyToReproduce() {
        return readyToReproduce;
    }

    @Override
    public void updateDescendants() {

            this.amountOfDescendants++;
    }
}
