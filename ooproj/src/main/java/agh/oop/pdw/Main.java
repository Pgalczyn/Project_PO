package agh.oop.pdw;


import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.Grass;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;
import agh.oop.pdw.model.util.RandomUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        WorldMap map = new WorldMap();
//
//        map.spawnGrass(new Grass(new Vector2D(3, 3)));
//
//
//        for (Animal animal : map.getAnimals().values()) {
//            System.out.println(animal);
//        }
//
//        RandomUtils randomUtils = new RandomUtils();
//        for(int i = 0; i < 30; i++){
//             System.out.println(randomUtils.getGrassSpawnPosition(map));
//        }

        Animal animal = new Animal(new Vector2D(1,1),56,5,3);
        animal.mutateGenotype();
        System.out.println(Arrays.toString(animal.getGenotype()));

    }
}