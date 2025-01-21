package agh.oop.pdw.model;

import agh.oop.pdw.model.util.RandomUtils;

import java.util.*;

import static agh.oop.pdw.model.MapDirection.*;
import static agh.oop.pdw.model.util.RandomUtils.RANDOM;

public class Animal implements WorldElement, AnimalObserver {
    private MapDirection direction;
    private final List<MoveValidator> listeners = new ArrayList<>();
    private Vector2D position;
    private int currentEnergy;
    private int[] genotype;
    private int lengthOfGenotype;
    private int activeGene = 0;
    private int amountOfEatenPlants = 0;
    private int amountOfChildren = 0;
    private int amountOfDescendants = 0;
    private int amountOfDaysAlive = 0;
    private int amountOfDaysUntilDeath = Integer.MAX_VALUE;
    private boolean readyToReproduce = false;  //ustalimy początkowe wartości tak żeby zwierze musiało zjeść chociaż jedną rośline żeby móc się rozmnażać
    private int usedEnergyToReproduce;
    private HashSet<AnimalObserver> observers;


    //constructor for animals that were born on the map
    public Animal(Vector2D position, int StartEnergy, int[] genotype, int usedEnergyToReproduce, HashSet<AnimalObserver> observers) {
        this.position = position;
        this.genotype = genotype;
        this.lengthOfGenotype = genotype.length;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.observers = observers;
    }

    //constructor for animals which we put on the map
    public Animal(Vector2D position, int StartEnergy, int LengthOfGenotype, int usedEnergyToReproduce) {
        this.position = position;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.lengthOfGenotype = LengthOfGenotype;
        this.observers = new HashSet<>();
        this.genotype = RandomUtils.genotype(LengthOfGenotype);
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Position: " + position.toString() +
                ", direction: " + direction.toString() +
                ", current energy: " + currentEnergy +
                "days alive: " + amountOfDaysAlive;
    }

    @Override
    public String srcImage() {
        return "/Images/greenDot.png";
    }


    public Animal reproduce(Animal otherAnimal) {

        if (this.readyToReproduce && otherAnimal.readyToReproduce) {
            int[] newGenotype = getGenotypeForAnimal(this, otherAnimal);

            //subtracting energy to reproduce new animal
            this.currentEnergy -= this.usedEnergyToReproduce;
            otherAnimal.currentEnergy -= this.usedEnergyToReproduce;

            //creating set of observers for new animal
            HashSet<AnimalObserver> newObservers = new HashSet<>();
            newObservers.addAll(this.observers);
            newObservers.addAll(otherAnimal.observers);

            //adding parents to observers
            newObservers.add(this);
            newObservers.add(otherAnimal);

            //increasing number of children
            this.amountOfChildren += 1;
            otherAnimal.amountOfChildren += 1;

            Animal child = new Animal(this.position, 2 * this.usedEnergyToReproduce, newGenotype, this.usedEnergyToReproduce, newObservers);


           // child.notifyObservers();
            child.mutateGenotype();

            return child;

        }
        return null;
    }

    public int[] getGenotypeForAnimal(Animal animal_1, Animal animal_2) {
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

                if (i < Animal_1_Part) newGenotype[i] = animal_1.genotype[i];
                else newGenotype[i] = animal_2.genotype[i];

            }

        } else {
            for (int i = 0; i < animal_2.lengthOfGenotype; i++) {
                if (i < Animal_2_Part) newGenotype[i] = animal_2.genotype[i];
                else newGenotype[i] = animal_1.genotype[i];
            }
        }

        return newGenotype;

    }

    public void mutateGenotype() {

        // drawing how many numbers will be changed
        int amountOfChangedGenes = RANDOM.nextInt(this.genotype.length + 1);


        HashSet<Integer> randomIndexes = new HashSet<>();
        while (randomIndexes.size() < amountOfChangedGenes) {
            randomIndexes.add(RANDOM.nextInt(this.genotype.length));
        }

        for (int index : randomIndexes) {

            this.genotype[index] = RANDOM.nextInt(this.genotype.length);

        }
    }

    // changing direction of the animal when exiting the map
    public void moveBy4(WorldMap map) {

        if (this.direction == WEST) {
            this.direction = EAST;
            this.position = new Vector2D(map.getWidth(), this.position.getY());
        } else {
            this.direction = WEST;
            this.position = new Vector2D(0, this.position.getY());
        }
        ;
    }

    //w czasie wolnym poprawić optymalizacja
    public void move(MoveValidator validator) {
        if (!this.isMissingMove()) {
            this.rotate();
            if (validator.canMoveTo(position.addVector(this.direction.toVector()))) {
                this.position = validator.getNextPosition(this);
            }
        }
        this.activeGene = (this.activeGene + 1) % lengthOfGenotype;
        int heightOfMap = validator.getBoundary().topRight().y;
        this.currentEnergy = currentEnergy - substractingEnergyAlgo(heightOfMap);
        this.amountOfDaysAlive += 1;
        System.out.println("Animal energy: " + this.currentEnergy);
        //A4 - dodatkowy feature
        // poprzez pomnożenie substractingEnergyAlgo(heightOfMap) mozemy ustawic jaką minimalnie energi zwierze bedzie tracić za ruch
    }


    private void rotate() {
        int newDirection = (this.direction.ordinal() + this.genotype[this.activeGene]) % 8;
        this.direction = MapDirection.values()[newDirection];
    }

    //przyjąłem na sztywno 800 i 1000 dni jeśli będziemy chcieli można będzie to podać w dodatkowych atrybutach
    public Boolean isMissingMove() {

        int chanceBoundary = Math.min(this.amountOfDaysAlive, 800);

        return RANDOM.nextInt(1000) < chanceBoundary;
    }

    //[A] bieguny – bieguny zdefiniowane są na dolnej i górnej krawędzi mapy.
    // Im bliżej bieguna znajduje się zwierzę, tym większą energię traci podczas pojedynczego ruchu (na biegunach jest zimno);

    public int getDistanceFromPole(int heightOfMap) {
        int currentAnimalPositionY = this.position.getY();
        return Math.min(Math.abs(currentAnimalPositionY - heightOfMap), currentAnimalPositionY) + 1;

    }

    public int substractingEnergyAlgo(int heightOfMap) {
        int distance = 1 / getDistanceFromPole(heightOfMap);
        System.out.println((int) distance * heightOfMap / 2);
        return (int) distance * heightOfMap / 2;
    }


    public void addObserver(AnimalObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AnimalObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for (AnimalObserver observer : new ArrayList<>(observers)) {
            if (observer.getCurrentEnergyObserver() <= 0 ) {
                removeObserver(observer);
            } else {
                observer.updateDescendants();
            }
        }
    }

    public static final Comparator<Animal> ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN = Comparator.comparingInt(Animal::getCurrentEnergy).thenComparing(Animal::getAmountOfDaysAlive).thenComparing(Animal::getAmountOfChildren);

    public void eat(int EnergyAfterEatingAdded){
        this.currentEnergy += EnergyAfterEatingAdded;
    }

    public int[] getGenotype() {
        return genotype;
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
    public void increaseAmountOfDaysAlive() {
        amountOfDaysAlive++;
    }

    public int getAmountOfDaysUntilDeath() {
        return amountOfDaysUntilDeath;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setGenotype(int[] genotype) {
        this.genotype = genotype;
        this.lengthOfGenotype = genotype.length;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public boolean getisReadyToReproduce() {
        return readyToReproduce;
    }
    public void setIsReadyToReproduce(boolean readyToReproduce) {
        this.readyToReproduce = readyToReproduce;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    @Override
    public void updateDescendants() {

        this.amountOfDescendants++;
    }
    @Override
    public int getCurrentEnergyObserver() {

        return this.currentEnergy;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return activeGene == animal.activeGene && amountOfEatenPlants == animal.amountOfEatenPlants && amountOfChildren == animal.amountOfChildren && amountOfDescendants == animal.amountOfDescendants && amountOfDaysAlive == animal.amountOfDaysAlive && amountOfDaysUntilDeath == animal.amountOfDaysUntilDeath && readyToReproduce == animal.readyToReproduce && direction == animal.direction && Objects.equals(position, animal.position) && Objects.deepEquals(genotype, animal.genotype) && Objects.equals(observers, animal.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, position, Arrays.hashCode(genotype), activeGene, amountOfEatenPlants, amountOfChildren, amountOfDescendants, amountOfDaysAlive, amountOfDaysUntilDeath, readyToReproduce, observers);
    }

}
