package agh.oop.pdw.simulation;

import agh.oop.pdw.model.*;

import java.util.*;


import static agh.oop.pdw.model.Animal.ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN;
import static agh.oop.pdw.model.util.RandomUtils.RANDOM;

public class Simulation implements Runnable {
    private boolean paused = false;
    SimulationProps props;
    private final WorldMap map;
    private final AnimalsCreator animalsCreator;
    private final List<SimulationListener> listeners = new ArrayList<>();
    private int deadAnimals = 0;
    private int sumOfDeadAnimalsDays = 0;
    private int day = 0;
    public static int SimulationId = 0;
    private int myId;
    private boolean running = true;

    public Simulation(SimulationProps props) {
        this.props = props;
        this.map = new WorldMap(props.getMapHeight(), props.getMapWidth(), props.getPlants());
        this.animalsCreator = new AnimalsCreator(map, props.getEnergyToBreed(), props.getStartEnergy(), props.getAnimalGenomeLength());
        SimulationId++;
        this.myId = SimulationId;
    }

    public void run() {
        animalsCreator.createAnimals(props.getStartAnimals());
        while (day < props.getDayLimit() && running) {
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
                System.out.println("END OF SIMULATION");
            }
        }
        System.out.println("Day limit reached");
    }


    public void nextDay() {
        removeDeadAnimals();
        moveAnimals();
        animalsEatGrass();
        animalsBreed();
        spawnGrass();
        HashSet<Vector2D> updatedFields = new HashSet<>(map.getUpdatedFields());
        map.getUpdatedFields().clear();
        if (props.isExportData()) CsvExport.saveData(this.day, map.getInformer(), this.myId);
        for (SimulationListener listener : listeners) {
            listener.dayPassed(updatedFields);
        }
    }

    public int animalCold(Animal animal) {
        int distance = 1 / animal.getDistanceFromPole(map.getHeight());
        return distance * map.getHeight() / 2;
    }

    public void subscribe(SimulationListener subscriber) {
        this.listeners.add(subscriber);
    }


    public void removeDeadAnimals() {
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
        if (map.getAnimals().isEmpty()) {
            for (SimulationListener listener : listeners) {
                listener.endOfSimulaltion();
                Thread.currentThread().interrupt();
                this.running = false;
            }
        }
    }


    public void moveAnimals() {
        Set<Vector2D> keySet = new HashSet<>(map.getAnimals().keySet());
        for (Vector2D position : keySet) {
            ArrayList<Animal> animalsOnPosition = new ArrayList<>(map.getAnimals().get(position));
            for (Animal animal : animalsOnPosition) {
                if (!props.isSpecialMutation() || !animal.isMissingMove()) {
                    map.move(animal);
                    if (props.isMapPoles()) animal.setCurrentEnergy(animal.getCurrentEnergy() - animalCold(animal));
                    animal.setCurrentEnergy(animal.getCurrentEnergy() - props.getEnergyPerMove());
                }
            }
        }
    }

    public void animalsEatGrass() {
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
        for (Vector2D position : animalsMap.keySet()) {
            ArrayList<Animal> animals = animalsMap.get(position);
            List<Animal> animalsOnPostinionList = animals.stream()
                    .filter(a -> a.getCurrentEnergy() >= props.getEnergyToBreed()) // Filtrujemy zwierzęta gotowe do rozmnażania
                    .sorted(ENERGY_THEN_AGE_THEN_NUMBER_OF_CHILDREN)
                    .toList();
            if (animalsOnPostinionList.size() < 2) continue;
            int i = 0;
            while (i < animalsOnPostinionList.size() - 1) {
                Animal animal = animalsOnPostinionList.get(i);
                Animal child = animal.reproduce(animalsOnPostinionList.get(i + 1));
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


    public boolean isPaused() {
        return paused;
    }

    public SimulationProps getProps() {
        return props;
    }

    public int getDeadAnimals() {
        return deadAnimals;
    }

    public AnimalsCreator getAnimalsCreator() {
        return animalsCreator;
    }

    public int getSumOfDeadAnimalsDays() {
        return sumOfDeadAnimalsDays;
    }
}
