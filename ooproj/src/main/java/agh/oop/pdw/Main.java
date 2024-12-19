package agh.oop.pdw;


import agh.oop.pdw.model.Animal;
import agh.oop.pdw.model.Vector2D;
import agh.oop.pdw.model.WorldMap;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        Animal animal1 = new Animal(new Vector2D(0, 0));
        Animal animal2 = new Animal(new Vector2D(100, 100));

        map.place(animal1);
        map.place(animal2);

        for (Animal animal : map.getAnimals().values()) {
            System.out.println(animal);
        }
    }
}