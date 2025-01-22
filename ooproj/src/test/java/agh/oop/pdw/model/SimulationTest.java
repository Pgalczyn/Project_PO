package agh.oop.pdw.model;

import agh.oop.pdw.simulation.Simulation;
import agh.oop.pdw.simulation.SimulationProps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    SimulationProps simulationProps = new SimulationProps(
            10,10,10,false,100,
            12,0,2,10,10,
            2,0,5,false,
            100,10,10,false
    );
    Simulation simulation = new Simulation(simulationProps);

    @BeforeEach void setUp() {
        simulation.getAnimalsCreator().createAnimals(simulation.getProps().getStartAnimals());
    }



    @Test
    void removeDeadAnimals() {
        simulation.removeDeadAnimals();
        simulation.moveAnimals();
        assertFalse(simulation.getMap().getAnimals().isEmpty(), "Lista zwierząt nie powinna być pusta po kilku ruchach");

        simulation.moveAnimals();
        simulation.moveAnimals();
        simulation.removeDeadAnimals();
        assertFalse(simulation.getMap().getAnimals().isEmpty(), "Lista zwierząt nie powinna być pusta po kilku ruchach");


        simulation.moveAnimals();

        assertFalse(simulation.getMap().getAnimals().isEmpty(), "Lista zwierząt nie powinna być pusta po kilku ruchach");

        simulation.moveAnimals();
        simulation.removeDeadAnimals();

        assertTrue(simulation.getMap().getAnimals().isEmpty(), "Lista zwierząt powinna być pusta po wywołaniu moveAnimals");
    }



    @Test
    void animalEat() {
        Animal animal = new Animal(new Vector2D(5, 5), 10, 1, 0);
        animal.setDirection(MapDirection.NORTH);
        animal.setGenotype(new int[]{1,3,6,7});
        simulation.getMap().placeAnimal(animal);

        simulation.moveAnimals();
        simulation.animalsEatGrass();
        // energia uzyskiwana przez zjedzenie trawy = 12
        assertEquals(20,animal.getCurrentEnergy());

        simulation.moveAnimals();
        simulation.animalsEatGrass();
        assertEquals(30,animal.getCurrentEnergy());

        simulation.moveAnimals();
        simulation.animalsEatGrass();
        assertEquals(40,animal.getCurrentEnergy());

    }

    @Test
    void animalCold() {
        Animal animal = new Animal(new Vector2D(5, 5), 10, 1, 0);
        animal.setDirection(MapDirection.NORTH);
        animal.setGenotype(new int[]{1,3,6,7});
        simulation.getMap().placeAnimal(animal);

        assertEquals(5,animal.getPosition().getY());
        assertNotEquals(0,animal.getPosition().getX());

        Animal animal1 = new Animal(new Vector2D(0, 0), 10, 1, 0);
        animal.setDirection(MapDirection.NORTH);
        animal.setGenotype(new int[]{1,3,6,7});
        simulation.getMap().placeAnimal(animal);

        assertEquals(0,animal1.getPosition().getY());
        assertNotEquals(1,animal1.getPosition().getX());

    }


    @Test
    void moveAnimals() {
        Animal animal = new Animal(new Vector2D(5, 5), 10, 1, 0);
        animal.setDirection(MapDirection.NORTH);
        animal.setGenotype(new int[]{1,3,6,7});
        simulation.getMap().placeAnimal(animal);

        //w symulacji jest ustawione że odjemuje 2 energii za ruch
        simulation.moveAnimals();
        assertEquals(8,animal.getCurrentEnergy());
        assertNotEquals(9,animal.getCurrentEnergy());
        simulation.moveAnimals();
        assertEquals(6,animal.getCurrentEnergy());
        assertNotEquals(1,animal.getCurrentEnergy());
        simulation.moveAnimals();
        assertEquals(4,animal.getCurrentEnergy());
        assertNotEquals(5,animal.getCurrentEnergy());

    }
}