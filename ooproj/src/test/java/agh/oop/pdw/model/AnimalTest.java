package agh.oop.pdw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    private Animal animal1;
    private Animal animal2;

    @BeforeEach
    public void setUp() {
        // Przygotowanie zwierząt do testów
        animal1 = new Animal(new Vector2D(10, 10), 100, 10, 10);
        animal2 = new Animal(new Vector2D(10, 10), 100, 10, 10);
    }

    @Test
    public void testReproduce() {
        Animal child = animal1.reproduce(animal2);

        // Testujemy, czy dziecko zostało poprawnie stworzony
        assertNotNull(child);
        // Testujemy, czy dzieci zostały dodane do obu zwierząt
        assertTrue(animal1.getChildren().contains(child));
        assertTrue(animal2.getChildren().contains(child));
        // Sprawdzamy, czy energia obu zwierząt została odpowiednio zmniejszona
        assertEquals(90, animal1.getCurrentEnergy());
        assertEquals(90, animal2.getCurrentEnergy());
    }

    @Test
    public void testGenotypeForAnimal() {
        int[] genotype1 = animal1.getGenotype();
        int[] genotype2 = animal2.getGenotype();
        int[] newGenotype = animal1.getGenotypeForAnimal(animal1, animal2);

        // Testujemy, czy genotyp jest poprawnie wygenerowany
        assertNotNull(newGenotype);
        assertEquals(genotype1.length, newGenotype.length);
        // Testujemy, czy genotypy są odpowiednio wymieszane
        assertNotEquals(genotype1, newGenotype);
        assertNotEquals(genotype2, newGenotype);
    }

    @Test
    public void testEat() {
        int initialEnergy = animal1.getEnergy();
        animal1.eat(20);

        // Sprawdzamy, czy energia po zjedzeniu wzrosła
        assertEquals(initialEnergy + 20, animal1.getEnergy());
    }

    @Test
    public void testMove() {
        WorldMap worldMap = new WorldMap(100,100,10);
        Vector2D initialPosition = animal1.getPosition();

        // Zakładając, że mamy odpowiednie dane do testów ruchu
        animal1.move(worldMap);

        // Testujemy, czy zwierzę zmieniło pozycję
        assertNotEquals(initialPosition, animal1.getPosition());
    }


    @Test
    public void testAmountOfChildren() {
        assertEquals(0, animal1.getAmountOfChildren());

        // Testujemy liczbę dzieci po rozmnożeniu
        animal1.reproduce(animal2);
        assertEquals(1, animal1.getAmountOfChildren());
        assertEquals(1, animal2.getAmountOfChildren());
    }
}
