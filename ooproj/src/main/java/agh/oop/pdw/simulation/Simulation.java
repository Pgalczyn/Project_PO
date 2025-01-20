package agh.oop.pdw.simulation;

import agh.oop.pdw.model.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static agh.oop.pdw.model.Animal.ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN;

public class Simulation implements Runnable {
    private boolean running = true;
    private boolean paused = false;
    SimulationProps props;
    private final WorldMap map;
    private final AnimalsCreator animalsCreator;
    private final List<SimulationListener> listeners = new ArrayList<>();
    private int day = 0;

    public Simulation(SimulationProps props) {
        this.props = props;
        this.map = new WorldMap(props.getMapHeight(), props.getMapWidth(), props.getPlants());
        this.animalsCreator = new AnimalsCreator(map, props.getEnergyToBreed(), props.getStartEnergy(), props.getAnimalGenomeLength());
    }

    public void run() {
        animalsCreator.createAnimals(props.getStartAnimals());
        for (SimulationListener listener : listeners) {
            listener.dayPassed();
        }
        while (day < props.getDayLimit()) {
            synchronized (this) {
                while (paused) {
                    System.out.println("Paused");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            nextDay();
            this.day += 1;
            try {
                Thread.sleep(props.getDayOffset());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Day limit reached");
        for (Grass grass : map.getGrasses().values()) {
            System.out.println(grass);
        }
        System.out.println("Grass count: " + map.getGrasses().size());
    }


    public void nextDay() {
        System.out.println(map.theMostPopularGenotype());
        removeDeadAnimals();
        moveAnimals();
        animalsEatGrass();
        animalsBreed();
        spawnGrass();
        for (SimulationListener listener : listeners) {
            listener.dayPassed();
        }
    }

    public void subscribe(SimulationListener subscriber) {
        this.listeners.add(subscriber);
    }

    private void runWithLimit() {
        for (int i = 0; i < props.getDayLimit(); i++) {
            run();
        }
    }

    private void removeDeadAnimals() {
        Set<Vector2D> keySet = new HashSet<>(map.getAnimals().keySet());
        for (Vector2D position : keySet) {
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(map.getAnimals().get(position));
            for (Animal animal : animalsOnPosition) {
                if (animal.getCurrentEnergy() <= 0) {
                    map.removeAnimal(animal);
                }
            }
        }
    }

    private void moveAnimals() {
        Set<Vector2D> keySet = new HashSet<>(map.getAnimals().keySet());
        for (Vector2D position : keySet) {
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(map.getAnimals().get(position));
            for (Animal animal : animalsOnPosition) {
                map.move(animal);
                animal.setCurrentEnergy(animal.getCurrentEnergy() - props.getEnergyPerMove());
            }
        }
    }

    private void animalsEatGrass() {
        Map<Vector2D, ArrayList<Animal>> animalsMap =  map.getAnimals();
        Map<Vector2D, Grass> grasses = map.getGrasses();
        for (Vector2D position : animalsMap.keySet()) {
            System.out.println(map.getGrasses().keySet());
            if (!grasses.containsKey(position)) return;
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(animalsMap.get(position));
            if (animalsOnPosition.isEmpty()) return;
            animalsOnPosition.sort(ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN);
            Animal animal = animalsOnPosition.getFirst();
            animal.eat(props.getEnergyOnEat());
            map.removeGrass(position);
        }
    }

    private void animalsBreed() {
    }

    private void spawnGrass() {
        for (int i = 0; i < props.getPlantsPerDay(); i++) {
            map.spawnGrass();
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
        synchronized (this) {
            notify();
        }
    }

    public int getDay() {
        return day;
    }

    public WorldMap getMap() {
        return map;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }
}
