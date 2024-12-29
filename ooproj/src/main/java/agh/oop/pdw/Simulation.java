package agh.oop.pdw;

import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.AnimalsCreator;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;

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
    }

    public void nextDay() {
        removeDeadAnimals();
        moveAnimals();
        animalsEatGrass();
        animalsBreed();
        spawnGrass();
    }

    private void runWithLimit() {
        for (int i = 0; i < props.getDayLimit(); i++) {
            run();
        }
    }

    private void removeDeadAnimals() {
        Map<Vector2D, Animal> mapAnimals = map.getAnimals();
        mapAnimals.forEach((position, animal) -> {
            if (animal.getCurrentEnergy() <= 0) {
                System.out.println("Animal died: " + animal);
                mapAnimals.remove(position);
            }
        });

    }

    private void moveAnimals() {
        Map<Vector2D, Animal> mapAnimals = map.getAnimals();
        mapAnimals.forEach((position, animal) -> {
            animal.move();
            System.out.println("Animal moved: " + animal);
        });
    }

    private void animalsEatGrass() {

    }

    private void animalsBreed() {

    }

    private void spawnGrass() {

    }

}
