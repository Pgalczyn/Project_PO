package agh.oop.pdw;

import agh.oop.pdw.model.*;

import java.util.*;

public class Simulation {
    SimulationProps props;
    private final WorldMap map;
    private final AnimalsCreator animalsCreator;

    public Simulation(SimulationProps props) {
        this.props = props;
        this.map = new WorldMap(props.getMapHeight(), props.getMapWidth(), props.getPlants());
        this.animalsCreator = new AnimalsCreator(map, props.getEnergyToBreed(), props.getStartEnergy(), props.getAnimalGenomeLength());
    }

    public void run() {
        animalsCreator.createAnimals(props.getStartAnimals());
        for (int i = 0; i < props.getDayLimit(); i++) {
            nextDay();
        }
        System.out.println("Day limit reached");
        for (Grass grass : map.getGrasses().values()) {
            System.out.println(grass);
        }
        System.out.println("Grass count: " + map.getGrasses().size());
    }

    public void nextDay() {
        removeDeadAnimals();
        moveAnimals();
        animalsEatGrass();
        animalsBreed();
        spawnGrass();
    }

    private void runWithLimit(){
        for (int i = 0; i < props.getDayLimit(); i++) {
            run();
        }
    }

    private void removeDeadAnimals() {
        for (Animal[] animalsOnPosition : map.getAnimals().values()) {
            for (Animal animal : animalsOnPosition) {
                if (animal.getCurrentEnergy() <= 0) {
                    System.out.println("Animal died: " + animal);
                    animalsOnPosition =
                            Arrays.stream(animalsOnPosition)
                                    .filter(a -> a != animal)
                                    .toArray(Animal[]::new);
                }
            }
        }
    }

    private void moveAnimals() {
        for (Animal[] animalsOnPosition : map.getAnimals().values()) {
            for (Animal animal : animalsOnPosition) {
                animal.move();
                System.out.println("Animal moved: " + animal);
            }
        }
    }

    private void animalsEatGrass() {

    }

    private void animalsBreed() {

    }

    private void spawnGrass() {
        for(int i = 0; i < props.getPlantsPerDay(); i++){
            map.spawnGrass();
        }
    }

}
