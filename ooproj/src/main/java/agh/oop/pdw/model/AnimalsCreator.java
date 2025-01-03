package agh.oop.pdw.model;

import agh.oop.pdw.model.util.RandomUtils;

public class AnimalsCreator {
    private WorldMap map;
    private int energyToReproduce;
    private int startEnergy;
    private int genomeLength;

    public AnimalsCreator(WorldMap map, int energyToReproduce, int startEnergy, int genomeLength) {
        this.map = map;
        this.energyToReproduce = energyToReproduce;
        this.startEnergy = startEnergy;
        this.genomeLength = genomeLength;
    }

    public void createAnimals(int numberOfAnimals){
        for(int i = 0; i < numberOfAnimals; i++){
            Vector2D position = RandomUtils.getRandomPosition(map.getBoundary());
            Animal animal = new Animal(position, startEnergy, genomeLength, energyToReproduce);
            map.placeAnimal(animal);
        }
    }
}
