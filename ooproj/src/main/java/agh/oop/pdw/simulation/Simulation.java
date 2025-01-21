package agh.oop.pdw.simulation;

import agh.oop.pdw.model.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static agh.oop.pdw.model.Animal.ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN;
import static agh.oop.pdw.model.util.RandomUtils.RANDOM;

public class Simulation implements Runnable {
    private boolean running = true;
    private boolean paused = false;
    SimulationProps props;
    private final WorldMap map;
    private final AnimalsCreator animalsCreator;
    private final List<SimulationListener> listeners = new ArrayList<>();
    private int deadAnimals = 0;
    private int sumOfDeadAnimalsDays = 0;
    private int day = 0;

    public Simulation(SimulationProps props) {
        this.props = props;
        this.map = new WorldMap(props.getMapHeight(), props.getMapWidth(), props.getPlants());
        this.animalsCreator = new AnimalsCreator(map, props.getEnergyToBreed(), props.getStartEnergy(), props.getAnimalGenomeLength());
    }

    public void run() {
        animalsCreator.createAnimals(props.getStartAnimals());
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
        System.out.println("Grass count: " + map.getGrasses().size());
    }


    public void nextDay() {
        long startTime = System.nanoTime(); // Start pomiaru
        removeDeadAnimals();
        long endTime = System.nanoTime(); // Koniec pomiaru
        long duration = endTime - startTime; // Czas wykonania w nanosekundach
        System.out.println("Czas wykonania metody RemoveDead: " + duration + " ns");
        startTime = System.nanoTime(); // Start pomiaru
        moveAnimals();
        endTime = System.nanoTime(); // Koniec pomiaru
        duration = endTime - startTime; // Czas wykonania w nanosekundach
        System.out.println("Czas wykonania metody MOVE: " + duration + " ns");
        startTime = System.nanoTime(); // Start pomiaru
        animalsEatGrass();
        endTime = System.nanoTime(); // Koniec pomiaru
        duration = endTime - startTime; // Czas wykonania w nanosekundach
        System.out.println("Czas wykonania metody EAT: " + duration + " ns");
        startTime = System.nanoTime(); // Start pomiaru
        animalsBreed();
        endTime = System.nanoTime(); // Koniec pomiaru
        duration = endTime - startTime; // Czas wykonania w nanosekundach
        System.out.println("Czas wykonania metody BREED: " + duration + " ns");
        startTime = System.nanoTime(); // Start pomiaru
        spawnGrass();
        endTime = System.nanoTime(); // Koniec pomiaru
        duration = endTime - startTime; // Czas wykonania w nanosekundach
        System.out.println("Czas wykonania metody GRASS SPAWEN: " + duration + " ns");

//        removeDeadAnimals();
//        moveAnimals();
//        animalsEatGrass();
//        animalsBreed();
//        spawnGrass();
        HashSet<Vector2D> updatedFields = new HashSet<>(map.getUpdatedFields());
        map.getUpdatedFields().clear();
        for (SimulationListener listener : listeners) {
            listener.dayPassed(updatedFields);
        }
    }

    public void subscribe(SimulationListener subscriber) {
        this.listeners.add(subscriber);
    }


    private void removeDeadAnimals() {
        Set<Vector2D> keySet = new HashSet<>(map.getAnimals().keySet());
        for (Vector2D position : keySet) {
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(map.getAnimals().get(position));
            for (Animal animal : animalsOnPosition) {
                if (animal.getCurrentEnergy() <= 0) {
                    map.removeAnimal(animal);
                    sumOfDeadAnimalsDays += animal.getAmountOfDaysAlive();
                    deadAnimals++;
                }
                animal.increaseAmountOfDaysAlive();
            }
        }
    }


    private void moveAnimals() {
        Set<Vector2D> keySet = new HashSet<>(map.getAnimals().keySet());
        System.out.println(keySet);
        for (Vector2D position : keySet) {
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(map.getAnimals().get(position));
            for (Animal animal : animalsOnPosition) {
                map.move(animal);
                animal.setCurrentEnergy(animal.getCurrentEnergy() - props.getEnergyPerMove());
            }
        }
    }

    private void animalsEatGrass() {
        Map<Vector2D, ArrayList<Animal>> animalsMap = map.getAnimals();
        Map<Vector2D, Grass> grasses = map.getGrasses();
        for (Vector2D position : animalsMap.keySet()) {
            if (!grasses.containsKey(position)) continue;
            Animal animal = animalsMap.get(position).stream().max(ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN).orElse(null);
            animal.eat(props.getEnergyOnEat());
            map.removeGrass(position);
        }
    }

    private void animalsBreed() {
        Map<Vector2D, ArrayList<Animal>> animalsMap = map.getAnimals();
        System.out.println(animalsMap.keySet().size());
        for (Vector2D position : animalsMap.keySet()) {
            ArrayList<Animal> animals = animalsMap.get(position);
            List<Animal> animalsOnPostinionList = animals.stream()
                    .filter(a -> a.getCurrentEnergy() >= props.getEnergyToBreed()) // Filtrujemy zwierzęta gotowe do rozmnażania
                    .sorted(ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN)
                    .toList();
            if (animalsOnPostinionList.size() < 2) continue;
            System.out.println(animalsOnPostinionList);
            int i = 0;
            while (i < animalsOnPostinionList.size()-1) {
                Animal animal = animalsOnPostinionList.get(i);
                long start = System.nanoTime();
                Animal child = animal.reproduce(animalsOnPostinionList.get(i + 1));
                long end = System.nanoTime();
                System.out.println("TIME REPOR: " + (end - start) );
                if (child == null) {
                    i = animalsOnPostinionList.size();
                    continue;
                }
                mutateNewAnimalGenotype(child);
                map.placeAnimal(child);
                i += 2;
            }
        }
    }

    private void mutateNewAnimalGenotype(Animal child) {
        Set<Integer> mutatedGenes = new HashSet<>();
        int[] genotype = child.getGenotype();
        int genesToMutate = RANDOM.nextInt(props.getMaxChildrenMutations()) + props.getMinChildrenMutations();
        while (mutatedGenes.size() < genesToMutate) {
            int geneIndex = RANDOM.nextInt(genotype.length);
            mutatedGenes.add(geneIndex);
        }
        for (int geneIndex : mutatedGenes) {
            genotype[geneIndex] = RANDOM.nextInt(8);
        }
        child.setGenotype(genotype);
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

    public SimulationProps getProps() {
        return props;
    }

    public int getDeadAnimals() {
        return deadAnimals;
    }

    public int getSumOfDeadAnimalsDays() {
        return sumOfDeadAnimalsDays;
    }
}
