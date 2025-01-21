package agh.oop.pdw.model;

import agh.oop.pdw.model.util.RandomUtils;

import java.util.*;

import static agh.oop.pdw.model.MapDirection.*;
import static agh.oop.pdw.model.util.RandomUtils.RANDOM;

public class Animal implements WorldElement, AnimalAncestor {
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
    private int usedEnergyToReproduce;
    private HashSet<Animal> ancestors = new HashSet<>();
    private HashSet<Animal> descendants = new HashSet<>();


    //constructor for animals that were born on the map
    public Animal(Vector2D position, int StartEnergy, int[] genotype, int usedEnergyToReproduce, HashSet<Animal> ancestors) {
        this.position = position;
        this.genotype = genotype;
        this.lengthOfGenotype = genotype.length;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.ancestors = ancestors;
    }

    //constructor for animals which we put on the map
    public Animal(Vector2D position, int StartEnergy, int LengthOfGenotype, int usedEnergyToReproduce) {
        this.position = position;
        this.usedEnergyToReproduce = usedEnergyToReproduce;
        this.direction = randomDirection();
        this.currentEnergy = StartEnergy;
        this.lengthOfGenotype = LengthOfGenotype;
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
        int[] newGenotype = getGenotypeForAnimal(this, otherAnimal);

        //subtracting energy to reproduce new animal
        this.currentEnergy -= this.usedEnergyToReproduce;
        otherAnimal.currentEnergy -= this.usedEnergyToReproduce;

        //creating set of observers for new animal
        HashSet<Animal> newAncestors = new HashSet<>();
//        newAncestors.addAll(this.ancestors);
//        newAncestors.addAll(otherAnimal.ancestors);
//
//        //adding parents to observers
//        newAncestors.add(this);
//        newAncestors.add(otherAnimal);

        //increasing number of children
        this.amountOfChildren += 1;
        otherAnimal.amountOfChildren += 1;


        Animal child = new Animal(this.position, 2 * this.usedEnergyToReproduce, newGenotype, this.usedEnergyToReproduce, newAncestors);
        child.notifyAncestors(true);
        this.descendants.add(child);
        otherAnimal.getDescendants().add(child);

        return child;
    }

    private void notifyAncestors(boolean born) {
        for (Animal animal : ancestors) {
            if (born) DescendantSpawned(animal);
            else DescendantDied(animal);
        }
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


    //w czasie wolnym poprawić optymalizacja
    public void move(MoveValidator validator) {
        if (!this.isMissingMove()) {
            this.rotate();
            if (validator.canMoveTo(position.addVector(this.direction.toVector()))) {
                this.position = validator.getNextPosition(this);
            }
        }
        this.activeGene = (this.activeGene + 1) % lengthOfGenotype;
        if (currentEnergy <= 0) {
            for (Animal animal : descendants) {
                animal.ancestors.remove(this);
            }
        }
        this.amountOfDaysAlive += 1;
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

    public static final Comparator<Animal> ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN = Comparator.comparingInt(Animal::getCurrentEnergy).thenComparing(Animal::getAmountOfDaysAlive).thenComparing(Animal::getAmountOfChildren);

    public void eat(int EnergyAfterEatingAdded) {
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

    public int getEnergy() {
        return currentEnergy;
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


    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }


    public HashSet<Animal> getDescendants() {
        return descendants;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return activeGene == animal.activeGene && amountOfEatenPlants == animal.amountOfEatenPlants && amountOfChildren == animal.amountOfChildren && amountOfDescendants == animal.amountOfDescendants && amountOfDaysAlive == animal.amountOfDaysAlive && direction == animal.direction && Objects.equals(position, animal.position) && Objects.deepEquals(genotype, animal.genotype) && Objects.equals(ancestors, animal.ancestors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, position, Arrays.hashCode(genotype), activeGene, amountOfEatenPlants, amountOfChildren, amountOfDescendants, amountOfDaysAlive, ancestors);
    }

    @Override
    public void DescendantDied(Animal animal) {
        animal.descendants.remove(this);
    }

    @Override
    public void DescendantSpawned(Animal animal) {
        animal.descendants.add(this);
    }
}
