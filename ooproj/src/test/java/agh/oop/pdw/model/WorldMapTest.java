package agh.oop.pdw.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {
    @Test
    public void animalCanMoveValidation() {
        WorldMap map = new WorldMap(10, 10, 10);
        Animal animal = new Animal(new Vector2D(0, 0), 1, 1, 0);
        animal.setGenotype(new int[]{0});
        animal.setDirection(MapDirection.SOUTH);
        map.placeAnimal(animal);
        assertFalse(map.canMoveTo(getAnimalNextPosition(animal)));

        animal.setDirection(MapDirection.SOUTH_WEST);
        assertFalse(map.canMoveTo(getAnimalNextPosition(animal)));

        animal.setDirection(MapDirection.WEST);
        assertTrue(map.canMoveTo(getAnimalNextPosition(animal)));

        Animal animal2 = new Animal(new Vector2D(0, 9), 1, 1, 0);
        animal2.setDirection(MapDirection.WEST);
        assertTrue(map.canMoveTo(getAnimalNextPosition(animal2)));

        animal2.setDirection(MapDirection.NORTH_EAST);
        assertFalse(map.canMoveTo(getAnimalNextPosition(animal2)));
    }

    @Test
    public void testAnimalPlace() {
        WorldMap map = new WorldMap(10, 10, 10);
        Animal animal = new Animal(new Vector2D(0, 0), 1, 1, 0);
        map.placeAnimal(animal);
        assertTrue(map.getAnimals().containsKey(animal.getPosition()));

        Animal animal2 = new Animal(new Vector2D(0, 0), 1, 1, 0);
        map.placeAnimal(animal2);
        assertEquals(2, map.getAnimals().get(animal.getPosition()).size());
    }

    @Test
    public void testGrassSpawn() {
        WorldMap map = new WorldMap(10, 10, 0);
        map.spawnGrass();
        assertEquals(1, map.getGrasses().size());

        WorldMap map2 = new WorldMap(1, 1, 0);
        map2.spawnGrass();
        assertEquals(1, map2.getGrasses().size());
        map2.spawnGrass();
        map2.spawnGrass();
        assertEquals(1, map2.getGrasses().size());
        assertEquals(0, map2.getEmptyFields().size());
    }

    @Test
    public void testAnimalNextPosition() {
        WorldMap map = new WorldMap(3, 3, 0);
        Animal animal = new Animal(new Vector2D(0, 0), 1, 1, 0);
        animal.setDirection(MapDirection.WEST);
        assertEquals(new Vector2D(2, 0), map.getNextPosition(animal));

        Animal animal2 = new Animal(new Vector2D(2, 2), 1, 1, 0);
        animal2.setDirection(MapDirection.NORTH_EAST);
        assertEquals(new Vector2D(2, 2), map.getNextPosition(animal2));

        Animal animal3 = new Animal(new Vector2D(2, 0), 1, 1, 0);
        animal3.setDirection(MapDirection.EAST);
        assertEquals(new Vector2D(0, 0), map.getNextPosition(animal3));
    }

    @Test
    public void testJungleCreation() {
        for(int i = 0; i < 100; i++) {
            int height = (int) (Math.random() * 100) + 100;
            int width = (int) (Math.random() * 100) + 100;
            WorldMap map = new WorldMap(height, width, 0);
            double ratio = map.getJungleBoundary().getArea() / map.getBoundary().getArea();
            assertTrue(Math.abs(ratio - 0.2) < 0.1);
        }
    }

    @Test
    public void testAnimalMovement() {
        WorldMap map = new WorldMap(10, 10, 0);
        Animal animal = new Animal(new Vector2D(5, 5), 1, 1, 0);
        animal.setDirection(MapDirection.NORTH);
        animal.setGenotype(new int[]{1,3,6,7});
        map.placeAnimal(animal);

        animal.move(map,10);
        assertEquals(new Vector2D(6, 6), animal.getPosition());

        animal.move(map,10);
        assertEquals(new Vector2D(6, 5), animal.getPosition());

        animal.move(map,10);
        assertEquals(new Vector2D(7, 5), animal.getPosition());

        animal.move(map,10);
        assertEquals(new Vector2D(8, 6), animal.getPosition());
    }

    private Vector2D getAnimalNextPosition(Animal animal) {
        return animal.getPosition().addVector(animal.getDirection().toVector());
    }
}
