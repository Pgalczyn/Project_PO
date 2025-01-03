package agh.oop.pdw;


import agh.oop.pdw.model.*;
import agh.oop.pdw.model.util.RandomUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int MAP_WIDTH = 20;
        final int MAP_HEIGHT = 20;
        final int START_ENERGY = 100;
        final boolean MAP_POLES = false;
        final int PLANTS = 10;
        final int ENERGY_PER_PLANT = 10;
        final int PLANTS_PER_DAY = 1;
        final int ENERGY_LOSS_PER_DAY = 1;
        final int START_ANIMALS = 3;
        final int ENERGY_TO_BREED = 20;
        final int ENERGY_LOSS_ON_BREED = 10;
        final int MAX_CHILDREN_MUTATIONS = 3;
        final int MIN_CHILDREN_MUTATIONS = 1;
        final boolean SPECIAL_MUTATIONS = false;

        final int SIMULATION_LIMIT = 300;

        SimulationProps props = new SimulationProps(
                MAP_WIDTH,
                MAP_HEIGHT,
                START_ENERGY,
                MAP_POLES,
                PLANTS,
                ENERGY_PER_PLANT,
                PLANTS_PER_DAY,
                ENERGY_LOSS_PER_DAY,
                START_ANIMALS,
                ENERGY_TO_BREED,
                ENERGY_LOSS_ON_BREED,
                MAX_CHILDREN_MUTATIONS,
                MIN_CHILDREN_MUTATIONS,
                SPECIAL_MUTATIONS,
                SIMULATION_LIMIT
        );

        Simulation simulation = new Simulation(props);


    }
}