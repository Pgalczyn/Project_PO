package agh.oop.pdw.presenter;

import agh.oop.pdw.simulation.SimulationEngine;
import agh.oop.pdw.simulation.SimulationProps;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
    private ChoiceBox configBox;
    @FXML
    private CheckBox exportCheck;

    private SimulationEngine engine;

    public void openSimulationWindow() {
        try {
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
            props.setExportData(exportCheck.isSelected());
            if (!props.validate()) {throw new NumberFormatException("Wrong int value");}
            engine.runSimulation(props);
        } catch (NumberFormatException e) {
            System.err.println("Error while parsing simulation properties: " + e.getMessage());
        }
    }

    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
    }
}
