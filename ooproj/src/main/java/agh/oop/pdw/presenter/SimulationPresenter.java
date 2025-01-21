package agh.oop.pdw.presenter;

import agh.oop.pdw.config.ConfigLoader;
import agh.oop.pdw.config.ConfigSaver;
import agh.oop.pdw.config.SimulationConfig;
import agh.oop.pdw.simulation.SimulationEngine;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;


public class SimulationPresenter {
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private TextField plantsOnStart;
    @FXML
    private TextField dailyPlants;
    @FXML
    private TextField startEnergy;
    @FXML
    private TextField energyOnEat;
    @FXML
    private TextField energyPerMove;
    @FXML
    private TextField genomeLength;
    @FXML
    private TextField energyToBreed;
    @FXML
    private TextField breedCost;
    @FXML
    private TextField startAnimals;
    @FXML
    private TextField minMutations;
    @FXML
    private TextField maxMutations;
    @FXML
    private TextField dayLimit;
    @FXML
    private CheckBox specialMutation;
    @FXML
    private CheckBox mapPoles;
    @FXML
    private TextField simulationTimeout;
    @FXML
    private TextField configName;
    @FXML
    private Button saveButton;
    @FXML
    private AnchorPane configPane;
    @FXML
    private ChoiceBox configBox;

    private SimulationEngine engine;

    public void openSimulationWindow() {
        SimulationProps props = new SimulationProps();
        props.setMapWidth(Integer.parseInt(mapWidth.getText()));
        props.setMapHeight(Integer.parseInt(mapHeight.getText()));
        props.setPlants(Integer.parseInt(plantsOnStart.getText()));
        props.setPlantsPerDay(Integer.parseInt(dailyPlants.getText()));
        props.setStartEnergy(Integer.parseInt(startEnergy.getText()));
        props.setEnergyOnEat(Integer.parseInt(energyOnEat.getText()));
        props.setEnergyPerMove(Integer.parseInt(energyPerMove.getText()));
        props.setAnimalGenomeLength(Integer.parseInt(genomeLength.getText()));
        props.setEnergyToBreed(Integer.parseInt(energyToBreed.getText()));
        props.setEnergyLossOnBreed(Integer.parseInt(breedCost.getText()));
        props.setStartAnimals(Integer.parseInt(startAnimals.getText()));
        props.setMinChildrenMutations(Integer.parseInt(minMutations.getText()));
        props.setMaxChildrenMutations(Integer.parseInt(maxMutations.getText()));
        props.setDayLimit(Integer.parseInt(dayLimit.getText()));
        props.setSpecialMutation(specialMutation.isSelected());
        props.setMapPoles(mapPoles.isSelected());
        props.setDayOffset(Integer.parseInt(simulationTimeout.getText()));
        engine.runSimulation(props);
        System.out.println("Simulation started");
    }

    public void saveConfig() {
        SimulationConfig config = new SimulationConfig();
        config.setMapWidth(Integer.parseInt(mapWidth.getText()));
        config.setMapHeight(Integer.parseInt(mapHeight.getText()));
        config.setPlantsOnStart(Integer.parseInt(plantsOnStart.getText()));
        config.setDailyPlants(Integer.parseInt(dailyPlants.getText()));
        config.setStartEnergy(Integer.parseInt(startEnergy.getText()));
        config.setEnergyOnEat(Integer.parseInt(energyOnEat.getText()));
        config.setEnergyPerMove(Integer.parseInt(energyPerMove.getText()));
        config.setGenomeLength(Integer.parseInt(genomeLength.getText()));
        config.setEnergyToBreed(Integer.parseInt(energyToBreed.getText()));
        config.setBreedCost(Integer.parseInt(breedCost.getText()));
        config.setStartAnimals(Integer.parseInt(startAnimals.getText()));
        config.setMinMutations(Integer.parseInt(minMutations.getText()));
        config.setMaxMutations(Integer.parseInt(maxMutations.getText()));
        config.setDayLimit(Integer.parseInt(dayLimit.getText()));
        config.setSpecialMutation(specialMutation.isSelected());
        config.setMapPoles(mapPoles.isSelected());
        config.setSimulationTimeout(Integer.parseInt(simulationTimeout.getText()));
        config.setConfigName(configName.getText());
        ConfigSaver.saveConfig(config);
        ConfigLoader.list(configBox);
    }

    private void setConfig(SimulationConfig config) {
        mapWidth.setText(String.valueOf(config.getMapWidth()));
        mapHeight.setText(String.valueOf(config.getMapHeight()));
        plantsOnStart.setText(String.valueOf(config.getPlantsOnStart()));
        dailyPlants.setText(String.valueOf(config.getDailyPlants()));
        startEnergy.setText(String.valueOf(config.getStartEnergy()));
        energyOnEat.setText(String.valueOf(config.getEnergyOnEat()));
        energyPerMove.setText(String.valueOf(config.getEnergyPerMove()));
        genomeLength.setText(String.valueOf(config.getGenomeLength()));
        energyToBreed.setText(String.valueOf(config.getEnergyToBreed()));
        breedCost.setText(String.valueOf(config.getBreedCost()));
        startAnimals.setText(String.valueOf(config.getStartAnimals()));
        minMutations.setText(String.valueOf(config.getMinMutations()));
        maxMutations.setText(String.valueOf(config.getMaxMutations()));
        dayLimit.setText(String.valueOf(config.getDayLimit()));
        specialMutation.setSelected(config.isSpecialMutation());
        mapPoles.setSelected(config.isMapPoles());
        simulationTimeout.setText(String.valueOf(config.getSimulationTimeout()));
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
        ConfigLoader.list(configBox);
        configBox.setOnAction(e -> {
            SimulationConfig config = ConfigLoader.load((String) configBox.getValue());
            System.out.println(config);
            if (config != null) setConfig(config);
        });
    }
}
